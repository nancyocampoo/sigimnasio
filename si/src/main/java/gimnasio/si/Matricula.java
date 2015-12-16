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

/**
 *Creacion de la entidad Matricula, que hace referencia a las matriculas 
 *de cada uno de los usuarios del gimnasio
 * @author Nancy Ocampo, Jenifer Vazquez, Nuria Canle
 */
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
	private float precioTarifa;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Centro centro = null;
	
	/**
	 * Metodo para obtener la Fecha de Alta del gimnasio.
	 * @return Date. Fecha Alta del gimnasio
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * Metodo para modificar la Fecha de Alta del gimnasio.
	 * @param Date fechaAlta. Fecha alta del gimnasio
	 * @return void
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * Metodo para obtener la fecha baja del gimnasio.
	 * @return Date. fecha baja del gimnasio
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}

	/**
	 * Metodo para modificar la fecha baja del gimnasio
	 * @param Date fechaBaja. fecha baja del gimnasio
	 * @return void
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	 * Metodo para obtener el tipo de tarifa de la matricula.
	 * @return TipoTarifa. Enum de los tipos de tarifa del gimnasio
	 */
	public TipoTarifa getTarifa() {
		return tarifa;
	}

	/**
	 * Metodo para modificar el tipo de tarifa de la matricula
	 * @param TipoTarifa tarifa. Enum de los tipos de tarifa del gimnasio 
	 * @return void
	 */
	public void setTarifa(TipoTarifa tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Metodo para obtener el precio de tarifa de la matricula
	 * @return float. precioTarifa del gimnasio
	 */
	public float getPrecioTarifa() {
		return precioTarifa;
	}

	/**
	 * Metodo para modificar el precio de tarifa de la matricula
	 * @param float precioTarifa. Precio tarifa de la matricula
	 * @return void
	 */
	public void setPrecioTarifa(float precioTarifa) {
		this.precioTarifa = precioTarifa;
	}

	/**
	 * Metodo para obtener el id de la matricula
	 * @return int id. Identificador de la matricula
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo para obtener el usuario al que pertenece la matricula
	 * @return Usuario. Objeto usuario
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}
	/**
	 * Metodo para modificar la lista de matriculas de un usuario
	 * @param Usuario usuario. objeto usuario 
	 * @return void
	 */
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
	
	/**
	 * Metodo para obtener el centro en el que se realiza la Matricula
	 * @return Centro. objeto centro
	 */
	public Centro getCentro() {
		return this.centro;
	}
	
	/**
	 * Metodo para modificar la lista de matriculas de un centro
	 * @param Centro centro.objeto centro
	 * @return void
	 */
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
}
