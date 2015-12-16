package zk;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import gimnasio.si.Acceso;
import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;
import zk.jpa.DesktopEntityManagerManager;


/**
 * @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 *
 */
public class AccesosVM {

	private Acceso accesoActual = null;
	
	/**
	 * @return
	 */
	public Acceso getAccesoActual(){ 
		return accesoActual;
	}
	
	/**
	 * @return
	 */
	public List<Acceso> getAccesos() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select d from Acceso d",Acceso.class).getResultList();
	}
	
	/**
	 * 
	 */
	@Command
	@NotifyChange("accesoActual")
	public void nuevoAcceso(){
		this.accesoActual = new Acceso();
	}
	
	/**
	 * 
	 */
	@Command
	@NotifyChange("accesoActual")
	public void cancelar(){
		this.accesoActual = null;
	}
	
	/**
	 * 
	 */
	@Command
	@NotifyChange({"accesoActual","accesos"})
	public void guardar(){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				em.persist(accesoActual);
			}
		}, em);
		this.accesoActual = null;
	}
	
	
}
	

