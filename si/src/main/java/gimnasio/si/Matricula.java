package gimnasio.si;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Matricula {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private Date fechaAlta;
	private Date fechaBaja;
	@Enumerated(EnumType.STRING)
	private TipoTarifa tarifa;
	//@Pattern(regexp = "[0-9.-,]*")
	private float precioTarifa;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Centro centro;
	
	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public TipoTarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(TipoTarifa tarifa) {
		this.tarifa = tarifa;
	}

	public float getPrecioTarifa() {
		return precioTarifa;
	}

	public void setPrecioTarifa(float precioTarifa) {
		this.precioTarifa = precioTarifa;
	}

	public int getId() {
		return id;
	}

	// lo devuelve
	public Usuario getUsuario() {
		return this.usuario;
	}

	// lo guarda
	public void setUsuario(Usuario usuario)
	{
		if(this.usuario!=null){
			this.usuario.internalRemoveMatricula(this);
		}
		this.usuario = usuario;
		if(usuario!=null){
			usuario.internalAddMatricula(this);
		}
	}
	
	// lo devuelve
	public Centro getCentro() {
		return this.centro;
	}
	
	// lo guarda
	public void setCentro(Centro centro)
	{
		if(this.centro!=null){
			this.centro.internalRemoveMatricula(this);
		}
		this.centro = centro;
		if(centro!=null){
			centro.internalAddMatricula(this);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + Float.floatToIntBits(precioTarifa);
		result = prime * result + ((tarifa == null) ? 0 : tarifa.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((centro == null) ? 0 : centro.hashCode());
		return result;
	}

	// para comparar objetos matricula
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matricula other = (Matricula) obj;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (Float.floatToIntBits(precioTarifa) != Float.floatToIntBits(other.precioTarifa))
			return false;
		if (tarifa != other.tarifa)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (centro == null) {
			if (other.centro != null)
				return false;
		} else if (!centro.equals(other.centro))
			return false;
		return true;
	}
}
