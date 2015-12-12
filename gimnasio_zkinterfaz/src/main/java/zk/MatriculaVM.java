package zk;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.NotifyChange;

import gimnasio.si.Matricula;
import gimnasio.si.TipoTarifa;
import gimnasio.si.Usuario;
import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;
import zk.jpa.DesktopEntityManagerManager;

public class MatriculaVM {
	
	private Matricula matriculaActual = null;
	private boolean edit = false;
	private TipoTarifa[] tipoTarifa = TipoTarifa.values();
	
	public List<Matricula> getMatriculas() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select m from Matricula m", Matricula.class).getResultList();
	}
	
	@Command
	@NotifyChange("matriculaActual")
	public void nuevaMatricula(){
		this.matriculaActual = new Matricula();
		this.edit = false;
	}
	
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
	
	@DependsOn("matriculas")
	public int getCount(){
		return this.getMatriculas().size();
	}
	
	@Command
	@NotifyChange("matriculaActual")
	public void cancel(){
		this.matriculaActual = null;
	}

	public Matricula getMatriculaActual() {
		return matriculaActual;
	}

	public void setMatriculaActual(Matricula matriculaActual) {
		this.matriculaActual = matriculaActual;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public TipoTarifa[] getTipoTarifa() {
		return tipoTarifa;
	}

	public void setTipoTarifa(TipoTarifa[] tipoTarifa) {
		this.tipoTarifa = tipoTarifa;
	}
	
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
	
	@Command
	@NotifyChange("matriculaActual")
	public void modificar (@BindingParam("matricula") Matricula m){
		this.matriculaActual = m;
		this.edit = true;
	}
	

}
