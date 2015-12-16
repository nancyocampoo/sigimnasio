package gimnasio.si;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *Creación de la entidad Usuario que hace referencia a los usuarios matriculados en el gimnasio.
 * @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 */
@Entity
public class Usuario {
	//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull @Column(length=100)
	@Pattern(regexp = "[a-zA-Z ñáéíóúÑÁÉÍÓÚ]*")
	private String nombre;
	@Column(unique = true, length=9)
	@Pattern(regexp = "[0-9]{8}[a-zA-Z]")
	private String dni;
	private Date fechaNacimiento;
	@NotNull @Column(length=100)
	private String domicilio;
	@NotNull @Column(length=9)
	@Pattern(regexp = "[0-9]{9}")
	private String telefono;
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	@Enumerated(EnumType.STRING)
	private Ocupacion ocupacion;
	
	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL)
	private List<Matricula> matriculas= new ArrayList<Matricula>();
	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL)
	private List<Acceso> accesos = new ArrayList<Acceso>();
	

	/**
	 * Metodo par obtener el nombre del usuario.
	 * @return String. Nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo para modificar el nombre del usuario
	 * @param Nombre nombre. Nombre del usuario
	 * @return void
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo para obtener el dni del usuario
	 * @return String. Dni del usuario
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Metodo para modificar el dni del usuario
	 * @param String dni. Dni del usuario 
	 * @return void
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Metodo para obtener la fecha de nacimiento del usuario
	 * @return Date. Fecha de nacimiento del usuario
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Metodo para modificar la fecha de nacimiento del usuario
	 * @param Date fechaNacimiento. fecha de nacimiento del usuario
	 * @return void
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Metodo para obtener el domicilio del usuario
	 * @return String. Domicilio del usuario
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * Metodo para modificar el domicilio del usuario
	 * @param String domicilio. Domicilio del usuario
	 * @return void
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * Metodo para obtener el telefono del usuario
	 * @return String. Telefono del usuario
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Metodo para modificar el telefono del usuario
	 * @param String telefono. Telefono del usuario
	 * @return void
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Metodo para obtener el sexo del usuario
	 * @return Sexo. Enum del sexo del usuario
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * Metodo para modificar el sexo del usuario
	 * @param Sexo sexo. enum del sexo del usuario
	 * @return void
	 * 
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	/**
	 * Metodo para obtener la ocupacion del usuario
	 * @return Ocupacion. ocupacion del usuario
	 */
	public Ocupacion getOcupacion() {
		return ocupacion;
	}

	/**
	 * Metodo para modificar la ocupacion del usuario
	 * @param Ocupacion ocupacion. Ocupacion del usuario
	 * @return void
	 */
	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}

	/**
	 * Metodo para obtener la identificador del usuario
	 * @return int. identificador del usuario
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Metodo para añadir una matricula a la lista de matriculas
	 * @param Matricula matricula. Objeto matricula
	 */
	void internalAddMatricula(Matricula matricula) {
		matriculas.add(matricula);
	}
	
	/**
	 * Metodo para borrar una matricula de la lista de matriculas
	 * @param Matricula matricula. Objeto matricula
	 */
	void internalRemoveMatricula(Matricula matricula) {
		matriculas.remove(matricula);
	}
	
	/**
	 * Metodo para añadir una acceso a la lista de accesos
	 * @param Acceso acceso. Objeto acceso
	 */
	void internalAddAcceso(Acceso acceso) {
		this.accesos.add(acceso);
	}
	
	/**
	 * Metodo para borrar una acceso de la lista de accesos
	 * @param Acceso acceso. Objeto acceso
	 */
	void internalRemoveAcceso(Acceso acceso) {
		this.accesos.remove(acceso);
	}
	
}
