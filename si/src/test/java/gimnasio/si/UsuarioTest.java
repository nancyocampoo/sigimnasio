package gimnasio.si;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;

public class UsuarioTest {

	private static EntityManagerFactory emf;

	@BeforeClass
	public static void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("gimnasio-database");
	}

	@AfterClass
	public static void closeEntityManagerFactory() {
		emf.close();
	}

	@Test
	public void testCreateUsuario() {

		EntityManager em = emf.createEntityManager();

		final Usuario usuario = new Usuario();
		usuario.setDni("55555555A");
		usuario.setNombre("Fito");
		usuario.setDomicilio("Rua Nova");
		usuario.setFechaNacimiento(new Date(1992, 5, 1));
		usuario.setOcupacion(Ocupacion.UNIVERSITARIO);
		usuario.setSexo(Sexo.Masculino);
		usuario.setTelefono(666366662);
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(usuario);
			}
		}, em);
		Usuario usuarioRec = em.createQuery("SELECT u FROM Usuario u where u.dni=:dni", Usuario.class)
				.setParameter("dni", usuario.getDni()).getSingleResult();
		assertEquals(usuarioRec.getDni(), usuario.getDni());
	}
}
