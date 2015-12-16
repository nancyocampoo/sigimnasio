package gimnasio.si;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
/**
 * Creacion de la entidad Centro que hace referencia a los centros del gimnasio
 * @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 *
 */
@Entity
public class Centro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	@Pattern(regexp = "[a-zA-Z ñáéíóúÑÁÉÍÓÚ]*")
	private String nombre;
	@Enumerated(EnumType.STRING)
	private Ciudad ciudad;
	@NotNull @Column(length=100)
	private String localizacion;
	@NotNull @Column(length=50)
	private String horario;
	@OneToMany(mappedBy="centro")
	private List<Matricula> matriculas = new ArrayList<Matricula>();
	@OneToMany(mappedBy="centro",cascade=CascadeType.ALL)
	private List<Acceso> accesos = new ArrayList<Acceso>();
	
	/**
	 * Metodo para modificar el valor del id del centro
	 * @param int id. Identificador del centro
	 * @return void
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Metodo para obtener el Nombre del centro
	 * @return String. Nombre del centro
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo para modificar el valor el nombre del centro
	 * @param String nombre. Nombre del centro
	 * @return void
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo para obtener la Ciudad del centro
	 * @return Ciudad. Enum de las ciudades
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * Metodo para modificar la Ciudad del centro
	 * @param Ciudad ciudad. Es la ciudad del centro
	 * @return void
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Metodo para obtener la localizacion del centro
	 * @return String Localizacion. Localizacion del centro
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * Metodo para modificar la localizacion del centro
	 * @param String localizacion. Localizacion del centro
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * Metodo para obtener el horario del centro
	 * @return String Horario. Horario del centro
	 */
	public String getHorario() {
		return horario;
	}

	/**Metodo para modificar el horario del centro
	 * @param String horario. Horario del centro
	 * @return void
	 */
	public void setHorario(String horario) {
		this.horario = horario;
	}

	/**
	 * Metodo para obtener la lista de matrículas asociadas al centro
	 * @return List<Matricula>. Lista de matriculas asociadas al centro
	 */
	public List<Matricula> getMatriculas() {
		return this.matriculas;
	}

	/**
	 * Metodo para modificar la lista de matriculas asociadas al centro
	 * @param List<Matricula> matriculas. Lista de matriculas.
	 * @return void
	 */
	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	/**
	 * Metodo para obtener el identificador del centro
	 * @return int. Identificador del centro
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Metodo para añadir una matricula a la lista de matriculas
	 * @param Matricula matricula. Objeto matricula
	 * @return void
	 */
	void internalAddMatricula(Matricula matricula) {
		this.matriculas.add(matricula);
	}
	
	/**
	 * Metodo para borrar una matricula de la lista de matriculas
	 * @param Matricula matricula. Objeto matricula
	 * @return void
	 */
	void internalRemoveMatricula(Matricula matricula) {
		this.matriculas.remove(matricula);
	}
	
	/**
	 * Metodo para añadir un acceso a la lista de accesos
	 * @param Acceso acceso. Objeto Acceso
	 * @return void
	 */
	void internalAddAcceso(Acceso acceso) {
		this.accesos.add(acceso);
	}
	
	/**
	 * Metodo para borrar un acceso de la lista de accesos
	 * @param Acceso acceso. Objeto acceso.
	 * @return void
	 */
	void internalRemoveAcceso(Acceso acceso) {
		this.accesos.remove(acceso);
	}
	
}
