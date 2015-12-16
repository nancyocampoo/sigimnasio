package gimnasio.si;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;

public class MatriculaTest {
	private static EntityManagerFactory emf;
//esto se ejectura antes de todo y crea un entity manager factory emf utilizar la base de datos
	@BeforeClass
	public static void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("gimnasio-database");
	}
//se ejectuta cuando se terminan todos los test y cierra la conexion con la base de datos
	@AfterClass
	public static void closeEntityManagerFactory() {
		emf.close();
	}

	@Test
	public void testCreateMatricula() {

		//Creación del usuario que no será propietario de ninguna matrícula
		EntityManager em = emf.createEntityManager();
		final Usuario usuario2 = new Usuario();
		usuario2.setDni("11111111A");
		usuario2.setNombre("Marco");
		usuario2.setDomicilio("Padre feijo");
		usuario2.setFechaNacimiento(new Date(1991, 5, 1));
		usuario2.setOcupacion(Ocupacion.UNIVERSITARIO);
		usuario2.setSexo(Sexo.Masculino);
		usuario2.setTelefono("999555888");
		
		//Creación del usuario propietario de las matrículas
		final Usuario usuario = new Usuario();
		usuario.setDni("88888888A");
		usuario.setNombre("Rober");
		usuario.setDomicilio("San lazaro");
		usuario.setFechaNacimiento(new Date(1991, 5, 1));
		usuario.setOcupacion(Ocupacion.UNIVERSITARIO);
		usuario.setSexo(Sexo.Masculino);
		usuario.setTelefono("666999888");
		
		final Centro centro1 = new Centro();
		centro1.setNombre("AqaGymOu");
		centro1.setCiudad(Ciudad.Ourense);
		centro1.setHorario("10:00-10:00");
		centro1.setLocalizacion("Lagoas");
		
		final Centro centro2 = new Centro();
		centro2.setNombre("AqaGymVigo");
		centro2.setCiudad(Ciudad.Vigo);
		centro2.setHorario("10:00-10:00");
		centro2.setLocalizacion("Lazaro");
	
		//Se guarda en la base de datos las dos matriculas y el usuario al que pertenecen
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(usuario2);
				em.persist(usuario);
				em.persist(centro1);
				em.persist(centro2);
			}
		}, em);
		
		Usuario usuarioRecuperado = em.createQuery("SELECT u FROM Usuario u WHERE dni='" + usuario.getDni() +"'", Usuario.class).getSingleResult();
		Centro centroRecuperado = em.createQuery("SELECT c FROM Centro c WHERE nombre='" + centro1.getNombre() + "'", Centro.class).getSingleResult();
		
		//Creación de la primera matrícula perteneciente al usuario
		final Matricula matricula = new Matricula();
		matricula.setFechaAlta(new Date());
		matricula.setTarifa(TipoTarifa.COMPLETA);
		matricula.setUsuario(usuarioRecuperado);
		matricula.setCentro(centroRecuperado);
		
		//Creación de la segunda matrícula perteneciente al usuario
		final Matricula matricula2 = new Matricula();
		matricula2.setFechaAlta(new Date());
		matricula2.setTarifa(TipoTarifa.PARCIAL);
		matricula2.setUsuario(usuarioRecuperado);
		matricula2.setCentro(centroRecuperado);
				
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(matricula);
				em.persist(matricula2);
			}
		}, em);
		
		//Se comprueba si las creaciones se han realizado de forma correcta
		//Comprobación de que el número de matrículas existentes en la tabla de matrículas es 2.
		List<Matricula> matriculaRec = em.createQuery("SELECT m FROM Matricula m ", Matricula.class).getResultList();
		assertEquals(matriculaRec.size(), 2);
		//Comprobación de que el usuario creado tiene un total de dos matrículas asociadas
		List<Matricula> usuarioMatriculas = em.createQuery("SELECT u.matriculas From Usuario u WHERE dni=:dni")
				.setParameter("dni", usuario.getDni()).getResultList();
		assertEquals(usuarioMatriculas.size(), 2);
	}
}
