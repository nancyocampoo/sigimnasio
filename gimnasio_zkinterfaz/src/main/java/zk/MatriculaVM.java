package zk;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;

import gimnasio.si.Centro;
import gimnasio.si.Matricula;
import gimnasio.si.TipoTarifa;
import gimnasio.si.Usuario;
import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;
import zk.jpa.DesktopEntityManagerManager;

/**
 * Clase para gestionar las Matriculas del gimnasio 
 * (altas, bajas, modificaciones, consultas y distintas gestiones sobre las paginas)
 * 
 *  @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 * 
 */
public class MatriculaVM {
	
	
	private Matricula matriculaActual = null;
	private boolean edit = false;
	private TipoTarifa[] tipoTarifa = TipoTarifa.values();
	private int idUsuario;
	
	/**
	 * Metodo para recuperar el id pasado por get y guardarlo en la variable idUsuario.
	 * Se ejecuta al principio
	 * @param String id. identificacion del usuario pasado por get
	 */
	@Init
	public void init(@QueryParam("id") String id) {
		this.idUsuario =Integer.parseInt(id);
		System.out.println("id usuario" +this.idUsuario);
	}
	
	/**
	 * Metodo que obtiene la lista de Matriculas pertenecientes al usuario con id=idUsuario 
	 * @return List<Matricula>. Lista de Matriculas del gimnasio.
	 */
	public List<Matricula> getMatriculas() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select m from Matricula m where usuario=" + this.idUsuario, Matricula.class).getResultList();
	}
	
	/**
	 * Metodo que obtiene la lista de todos los centros del gimnasio.
	 * @return List<centro>. Lista de centros del gimnasio.
	 */
	public List<Centro> getCentros() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select c from Centro c ", Centro.class).getResultList();
	}
	
	/**
	 * Metodo que obtiene los datos del usuario cuyo id=idUsuario
	 * @return Usuario. Objeto usuario
	 */
	public Usuario getUsuario() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select u from Usuario u where id=" + this.idUsuario, Usuario.class).getSingleResult();
	}
	
	/**
	 * Metodo que crea una nueva matricula vacia.
	 * Con este metodo inicializamos la variable "matriculaActual"
	 * La variable edit se pone a false porque estamos creando, a la matricula actual se le modifica
	 * el usuario con los datos del usuario recuperado con setUsuario
	 * Notificamos los cambios sobre la variable matriculaActual.
	 * @return void
	 */
	@Command
	@NotifyChange("matriculaActual")
	public void nuevaMatricula(){
		Usuario usuario = this.getUsuario();
		this.matriculaActual = new Matricula();
		this.matriculaActual.setUsuario(usuario);
		this.edit = false;
	}
	
	/**
	 * Metodo que borra un matricula de la tabla de matriculas.
	 * Notificamos los cambios sobre la variable matriculas.
	 * @param Matricula m. Objeto matricula que se desea borrar.
	 * @return void
	 */
	@Command
	@NotifyChange("matriculas")
	public void borrar(@BindingParam("matricula") Matricula m){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				em.remove(m);
			}
		}, em);
		
	}
	
	/**
	 * Metodo para contar el numero de matriculas existentes.
	 * Depende de la variable matriculas.
	 * @return int. Numero de matriculas.
	 */
	@DependsOn("matriculas")
	public int getCount(){
		return this.getMatriculas().size();
	}
	
	/**
	 * Metodo para cerrar la ventana Modal que solo esta abierta 
	 * cuando el matriculaActual tiene algun valor
	 * Notifica los cambios sobre la variable matriculaActual
	 * @return void
	 */
	@Command
	@NotifyChange("matriculaActual")
	public void cancel(){
		this.matriculaActual = null;
	}

	/**
	 * Metodo que obtiene el matriculaActual 
	 * @return Matricula. matricula actual
	 */
	public Matricula getMatriculaActual() {
		return matriculaActual;
	}
	
	/**
	 * Metodo que obtiene el TipoTarifa 
	 * @return TipoTarifa[]. TipoTarifa de la matricula
	 */
	public TipoTarifa[] getTipoTarifa() {
		return tipoTarifa;
	}
	
	/**
	 * Metodo para guardar el matriculaActual en la base de datos
	 * para ello se utiliza una transaction donde se guardan los datos en la BD.
	 * Al final pone la variable matriculaActual a null.
	 * Notifica los cambios sobre la variable matriculaActual y matriculas
	 * @return void
	 */
	@Command
	@NotifyChange({"matriculaActual","matriculas"})
	public void save(){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				if(!edit)
				{
					em.persist(matriculaActual);
				}
			}
		}, em);
		this.matriculaActual = null;
	}

	/**
	 * Metodo que modifica los datos del matricula
	 * Notifica los cambios sobre la variable matriculaaActual
	 * @param Matricula m. Matricula modificado 
	 */
	@Command
	@NotifyChange("matriculaActual")
	public void modificar (@BindingParam("matricula") Matricula m){
		this.matriculaActual = m;
		this.edit = true;
	}
}
