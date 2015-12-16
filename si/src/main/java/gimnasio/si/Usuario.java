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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Ocupacion getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}

	public int getId() {
		return id;
	}
	
	void internalAddMatricula(Matricula matricula) {
		matriculas.add(matricula);
	}
	
	void internalRemoveMatricula(Matricula matricula) {
		matriculas.remove(matricula);
	}
	
	void internalAddAcceso(Acceso acceso) {
		this.accesos.add(acceso);
	}
	
	void internalRemoveAcceso(Acceso acceso) {
		this.accesos.remove(acceso);
	}
	
}
