package gimnasio.si;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Acceso {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idAcceso;
	private Date fecha;
	private Time horaEntrada;
	private Time horaSalida;
	
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Centro centro;
	
	public int getIdAcceso() {
		return idAcceso;
	}
	
	public void setFechaAcceso(Date fecha) {
		this.fecha = fecha;
	}
	
	public Time getHoraEntrada() {
		return horaEntrada;
	}
	
	public void setHoraEntrada(Time horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	
	public Time getHoraSalida() {
		return horaSalida;
	}
	
	public void setHoraSalida(Time horaSalida) {
		this.horaSalida = horaSalida;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	// lo devuelve
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	// lo guarda
	public void setUsuario(Usuario usuario)
	{
		if(this.usuario!=null){
			this.usuario.internalRemoveAcceso(this);
		}
		this.usuario = usuario;
		if(usuario!=null){
			usuario.internalAddAcceso(this);
		}
	}
	
	// lo devuelve
	public Centro getCentro() {
		return this.centro;
	}
		
	// lo guarda
	public void setCentro(Centro centro) {
		if (this.centro != null) {
			this.centro.internalRemoveAcceso(this);
		}
		this.centro = centro;
		if (centro != null) {
			centro.internalAddAcceso(this);
		}
	}
}