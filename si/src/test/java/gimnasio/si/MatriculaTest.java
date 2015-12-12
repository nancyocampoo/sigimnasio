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

		EntityManager em = emf.createEntityManager();
		final Usuario usuario = new Usuario();
		usuario.setDni("55555554A");
		usuario.setNombre("Fito");
		usuario.setDomicilio("Rua Nova");
		usuario.setFechaNacimiento(new Date(1992, 5, 1));
		usuario.setOcupacion(Ocupacion.UNIVERSITARIO);
		usuario.setSexo(Sexo.Masculino);
		usuario.setTelefono(666366662);

		final Matricula matricula = new Matricula();
		matricula.setFechaAlta(new Date());
		matricula.setTarifa(TipoTarifa.COMPLETA);
		matricula.setUsuario(usuario);
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(usuario);
				em.persist(matricula);
			}
		}, em);
		List<Matricula> matriculaRec = em.createQuery("SELECT m FROM Matricula m ", Matricula.class).getResultList();
		assertEquals(matriculaRec.size(), 1);
		List<Matricula> usuarioMatriculas = em.createQuery("SELECT u.matriculas From Usuario u WHERE dni=:dni")
				.setParameter("dni", usuario.getDni()).getResultList();
		assertEquals(usuarioMatriculas.size(), 1);
	}
}
