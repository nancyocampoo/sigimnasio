package zk;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.NotifyChange;

import gimnasio.si.Centro;
import gimnasio.si.Ciudad;
import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;
import zk.jpa.DesktopEntityManagerManager;



public class CentroVM {
	
	private Centro centroActual = null;
	private boolean edit = false;
	private Ciudad[] ciudad = Ciudad.values();
	
	public List<Centro> getCentros() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select c from Centro c", Centro.class).getResultList();
	}
	
	@Command
	@NotifyChange("centroActual")
	public void nuevoCentro(){
		this.centroActual = new Centro();
		this.edit = false;
	}
	
	@Command
	@NotifyChange("centros")
	public void borrar(@BindingParam("centro") Centro c){
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			@Override
			public void run(EntityManager em) {
				em.remove(c);
			}
		}, em);
		
	}
	
	@DependsOn("centros")
	public int getCount(){
		return this.getCentros().size();
	}
	
	@Command
	@NotifyChange("centroActual")
	public void cancel(){
		this.centroActual = null;
	}


	public Centro getCentroActual() {
		return centroActual;
	}

	public void setCentroActual(Centro centroActual) {
		this.centroActual = centroActual;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Ciudad[] getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad[] ciudad) {
		this.ciudad = ciudad;
	}

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
	
	@Command
	@NotifyChange("centroActual")
	public void modificar (@BindingParam("centro") Centro c){
		this.centroActual = c;
		this.edit = true;
	}

}

