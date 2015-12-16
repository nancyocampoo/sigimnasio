package zk;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.NotifyChange;

import gimnasio.si.Centro;
import gimnasio.si.Ciudad;
import gimnasio.si.Matricula;
import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;
import zk.jpa.DesktopEntityManagerManager;

/**
 *Clase para gestionar los centros del gimnasio 
 *(altas, bajas, modificaciones, consultas y distintas gestiones sobre las paginas
 * @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 */
public class CentroVM {
	
	private Centro centroActual = null;
	private boolean edit = false;
	private Ciudad[] ciudad = Ciudad.values();
	
	/**
	 * Metodo que obtiene la lista de todos los centros del gimnasio.
	 * @return List<centro>. Lista de centros del gimnasio.
	 */
	public List<Centro> getCentros() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select c from Centro c", Centro.class).getResultList();
	}
	
	/**
	 * Metodo que crea un nuevo centro vacio.
	 * Con este metodo inicializamos la variable "centroActual"
	 * La variable edit se pone a false porque estamos creando.
	 * Notificamos los cambios sobre la variable centroActual.
	 * @return void
	 */
	@Command
	@NotifyChange("centroActual")
	public void nuevoCentro(){
		this.centroActual = new Centro();
		this.edit = false;
	}
	
	/**
	 * Metodo que borra un centro de la tabla de centros.
	 * Como en la tabla de matriculas hay una clave foranea que hace referencia al centro,
	 * es necesario poner a null este campo en la tabla de matriculas, para
	 * despues borrar el centro
	 * Notificamos los cambios sobre la variable centros.
	 * @param Centro c. Objeto centro que se desea borrar.
	 * @return void
	 */
	@Command
	@NotifyChange("centros")
	public void borrar(@BindingParam("centro") Centro c){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				List<Matricula> matriculas = c.getMatriculas();
				for (int i = matriculas.size() -1; i>=0; i--) {
					matriculas.get(i).setCentro(null);
				}
				em.remove(c);
			}
		}, em);
		
	}
	
	/**
	 * Metodo para contar el numero de centros existentes.
	 * Depende de la variable centros.
	 * @return int. Numero de centros.
	 */
	@DependsOn("centros")
	public int getCount(){
		return this.getCentros().size();
	}
	
	/**
	 * Metodo para cerrar la ventana Modal que solo esta abierta 
	 * cuando el centroActual tiene algun valor
	 * Notifica los cambios sobre la variable centroActual
	 * @return void
	 */
	@Command
	@NotifyChange("centroActual")
	public void cancel(){
		this.centroActual = null;
	}

	/**
	 * Metodo que obtiene el centroActual 
	 * @return Centro. centro actual
	 */
	public Centro getCentroActual() {
		return centroActual;
	}

	/**
	 * Metodo para modificar el centroActual
	 * @param Centro centroActual. Objeto centro
	 */
	public void setCentroActual(Centro centroActual) {
		this.centroActual = centroActual;
	}

	/**
	 * Metodo para obtener la ciudad del centro
	 * @return Ciudad[]. Enum de ciudades
	 */
	public Ciudad[] getCiudad() {
		return ciudad;
	}

	/**
	 * Metodo que modifica la ciudad del centro
	 * @param Ciudad[] ciudad. Enum de ciudad del centro
	 */
	public void setCiudad(Ciudad[] ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Metodo para guardar el centroActual en la base de datos
	 * para ello se utiliza una transaction donde se guardan los datos en la BD.
	 * Al final pone la variable centroActual a null.
	 * Notifica los cambios sobre la variable centroActual y centros
	 * @return void
	 */
	@Command
	@NotifyChange({"centroActual","centros"})
	public void save(){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				if(!edit)
				{
					em.persist(centroActual);
				}
			}
		}, em);
		this.centroActual = null;
	}
	
	/**
	 * Metodo que modifica los datos del centro
	 * Notifica los cambios sobre la variable centroActual
	 * @param Centro c. Centro modificado 
	 */
	@Command
	@NotifyChange("centroActual")
	public void modificar (@BindingParam("centro") Centro c){
		this.centroActual = c;
		this.edit = true;
	}

}

