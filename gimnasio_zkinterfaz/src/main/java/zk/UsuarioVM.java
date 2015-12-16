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

public class UsuarioVM {
	
	private Usuario usuarioActual = null;
	private boolean edit = false;
	private Sexo[] sexo = Sexo.values();
	private Ocupacion [] ocupacion = Ocupacion.values();
	private TipoTarifa [] tipotarifa = TipoTarifa.values() ;
	
	public List<Usuario> getUsuarios() {
		EntityManager em = DesktopEntityManagerManager.getDesktopEntityManager();
		return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}
	
	@Command
	@NotifyChange("usuarioActual")
	public void nuevoUsuario(){
		this.usuarioActual = new Usuario();
		this.edit = false;
	}
	
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
	
	@DependsOn("usuarios")
	public int getCount(){
		return this.getUsuarios().size();
	}
	
	@Command
	@NotifyChange("usuarioActual")
	public void cancel(){
		this.usuarioActual = null;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public Sexo[] getSexo() {
		return sexo;
	}

	public void setSexo(Sexo[] sexo) {
		this.sexo = sexo;
	}

	public Ocupacion[] getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Ocupacion[] ocupacion) {
		this.ocupacion = ocupacion;
	}

	public TipoTarifa[] getTipotarifa() {
		return tipotarifa;
	}

	public void setTipotarifa(TipoTarifa[] tipotarifa) {
		this.tipotarifa = tipotarifa;
	}

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
	
	@Command
	@NotifyChange("usuarioActual")
	public void modificar (@BindingParam("usuario") Usuario u){
		this.usuarioActual = u;
		this.edit = true;
	}

	@Command
	public void editarMatricula(@BindingParam("usuario") Usuario u){
		Executions.sendRedirect("edit_matricula.zul?id="+u.getId());
	}
	

}
