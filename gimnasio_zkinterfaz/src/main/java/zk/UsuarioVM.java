package zk;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import gimnasio.si.Ocupacion;
import gimnasio.si.Sexo;
import gimnasio.si.TipoTarifa;
import gimnasio.si.Usuario;
import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;
import zk.jpa.DesktopEntityManagerManager;
/**
 *Clase para gestionar los usuarios del gimnasio 
 *(altas, bajas, modificaciones, consultas y distintas gestiones sobre las paginas
 * @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 */
public class UsuarioVM {
	
	private Usuario usuarioActual = null;
	private boolean edit = false;
	private Sexo[] sexo = Sexo.values();
	private Ocupacion [] ocupacion = Ocupacion.values();
	/**
	 * Metodo que obtiene la lista de todos los usuarios del gimnasio.
	 * @return List<Usuario>. Lista de usuarios del gimnasio.
	 */
	public List<Usuario> getUsuarios() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}
	/**
	 * Metodo que crea un nuevo usuario vacio.
	 * Con este metodo inicializamos la variable "usuarioActual"
	 * La variable edit se pone a false porque estamos creando.
	 * Notificamos los cambios sobre la variable usuarioActual.
	 * @return void
	 */
	@Command
	@NotifyChange("usuarioActual")
	public void nuevoUsuario(){
		this.usuarioActual = new Usuario();
		this.edit = false;
	}
	/**
	 * Metodo que borra un usuario de la tabla de usuarios.
	 * Notificamos los cambios sobre la variable usuarios.
	 * @param Usuario u. Objeto usuario que se desea borrar.
	 * @return void
	 */
	@Command
	@NotifyChange("usuarios")
	public void borrar(@BindingParam("usuario") Usuario u){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				em.remove(u);
			}
		}, em);
		
	}
	
	/**
	 * Metodo para contar el numero de usuarios existentes.
	 * Depende de la variable usuarios.
	 * @return int. Numero de usuarios.
	 */
	@DependsOn("usuarios")
	public int getCount(){
		return this.getUsuarios().size();
	}
	/**
	 * Metodo para cerrar la ventana Modal que solo esta abierta 
	 * cuando el usuarioActual tiene algun valor
	 * Notifica los cambios sobre la variable usuarioActual
	 * @return void
	 */
	@Command
	@NotifyChange("usuarioActual")
	public void cancel(){
		this.usuarioActual = null;
	}
	/**
	 * Metodo que obtiene el usuarioActual 
	 * @return Usuario. usuario actual
	 */
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	/**
	 * Metodo para modificar el usuarioActual
	 * @param Usuario usuarioActual. Objeto usuario
	 */
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
	/**
	 * Metodo que obtiene el sexo 
	 * @return Sexo[]. sexo del usuario
	 */
	public Sexo[] getSexo() {
		return sexo;
	}
	/**
	 * Metodo para modificar el sexo del usuario
	 * @param Sexo[] sexo. Enum sexo
	 */
	public void setSexo(Sexo[] sexo) {
		this.sexo = sexo;
	}
	/**
	 * Metodo que obtiene el Ocupacion del usuario 
	 * @return Ocupacion[]. Enum ocupacion del usuario
	 */
	public Ocupacion[] getOcupacion() {
		return ocupacion;
	}
	/**
	 * Metodo para modificar el Ocupacion del usuario
	 * @param Ocupacion[] ocupacion. Enum ocupacion
	 */
	public void setOcupacion(Ocupacion[] ocupacion) {
		this.ocupacion = ocupacion;
	}
	/**
	 * Metodo para guardar el usuarioActual en la base de datos
	 * para ello se utiliza una transaction donde se guardan los datos en la BD.
	 * Al final pone la variable usuarioActual a null.
	 * Notifica los cambios sobre la variable usuarioActual y usuarios
	 * @return void
	 */
	@Command
	@NotifyChange({"usuarioActual","usuarios"})
	public void save(){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				if(!edit)
				{
					em.persist(usuarioActual);
				}
			}
		}, em);
		this.usuarioActual = null;
	}
	/**
	 * Metodo que modifica los datos del usuario
	 * Notifica los cambios sobre la variable usuarioActual
	 * @param Usuario u. Usuario modificado 
	 */
	@Command
	@NotifyChange("usuarioActual")
	public void modificar (@BindingParam("usuario") Usuario u){
		this.usuarioActual = u;
		this.edit = true;
	}

	/**
	 * Metodo para redireccionar a la pantalla de gestionar matriculas de un usuario concreto.
	 * Aqui es donde se pasa por get el Id del usuario
	 * @param Usuario u. Objeto usuario
	 */
	@Command
	public void editarMatricula(@BindingParam("usuario") Usuario u){
		Executions.sendRedirect("edit_matricula.zul?id="+u.getId());
	}
	

}
