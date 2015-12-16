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

public class MatriculaVM {
	
	
	private Matricula matriculaActual = null;
	private boolean edit = false;
	private TipoTarifa[] tipoTarifa = TipoTarifa.values();
	private int idUsuario;
	
	@Init
	public void init(@QueryParam("id") String id) {
		this.idUsuario =Integer.parseInt(id);
		System.out.println("id usuario" +this.idUsuario);
	}
	
	public List<Matricula> getMatriculas() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select m from Matricula m where usuario=" + this.idUsuario, Matricula.class).getResultList();
	}
	
	public List<Centro> getCentros() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select c from Centro c ", Centro.class).getResultList();
	}
	
	public Usuario getUsuario() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select u from Usuario u where id=" + this.idUsuario, Usuario.class).getSingleResult();
	}
	
	@Command
	@NotifyChange("matriculaActual")
	public void nuevaMatricula(){
		Usuario usuario = this.getUsuario();
		this.matriculaActual = new Matricula();
		this.matriculaActual.setUsuario(usuario);
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

	public TipoTarifa[] getTipoTarifa() {
		return tipoTarifa;
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
	
	/*private void getVariable(){
        Execution exec = Executions.getCurrent();
        String variableRecuperada = exec.getParameter("id");
        this.idUsuario =Integer.parseInt(variableRecuperada);
    }*/

	/*public int getIdUsuario() {
		return idUsuario;
	}*/
	
	

}
