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
 //Creacion de la entidad Centro con sus atributos 
/**
 * 
 * @author nancy
 *
 */
@Entity
public class Centro {
	//Creacion de los atributos del Centro y las comprobaciones de formato 
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
	//Establecer relaciones con Matriculas y Accesos, creando una lista de las mismas
	@OneToMany(mappedBy="centro")
	private List<Matricula> matriculas = new ArrayList<Matricula>();
	@OneToMany(mappedBy="centro",cascade=CascadeType.ALL)
	private List<Acceso> accesos = new ArrayList<Acceso>();
	
	//Generamos los setters y getters de cada uno de los atributos, que eran necesarios de modificar
	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public List<Matricula> getMatriculas() {
		return this.matriculas;
	}

	/**
	 * hgghfghfhgdfdgdgfg
	 * 
	 * @param matriculas
	 */
	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public int getId() {
		return id;
	}
	
	//Añadimos una nueva matricula, a la lista de matriculas
	void internalAddMatricula(Matricula matricula) {
		this.matriculas.add(matricula);
	}
	//Borramos una matricula, de la lista de matriculas
	void internalRemoveMatricula(Matricula matricula) {
		this.matriculas.remove(matricula);
	}
	
	//Añadimos un nuevo acceso, a la lista de accesos
	void internalAddAcceso(Acceso acceso) {
		this.accesos.add(acceso);
	}
	
	//Borramos un acceso, de la lista de accesos
	void internalRemoveAcceso(Acceso acceso) {
		this.accesos.remove(acceso);
	}
	
}
