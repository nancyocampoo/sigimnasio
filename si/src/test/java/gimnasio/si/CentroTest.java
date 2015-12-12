package gimnasio.si;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import gimnasio.si.util.Transaction;
import gimnasio.si.util.TransactionUtil;

public class CentroTest {

	private static EntityManagerFactory emf;

	// esto se ejectura antes de todo y crea un entity manager factory emf
	// utilizar la base de datos
	@BeforeClass
	public static void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("gimnasio-database");
	}

	// se ejectuta cuando se terminan todos los test y cierra la conexion con la
	// base de datos
	@AfterClass
	public static void closeEntityManagerFactory() {
		emf.close();
	}
//NOMBRE DEL ATRIBUTO
	@Test
	public void testCreateCentro() {
		final Centro centro1 = new Centro();
		centro1.setNombre("AqaGymOu");
		centro1.setCiudad(Ciudad.Ourense);
		centro1.setHorario("10:00-10:00");
		centro1.setLocalizacion("Lagoas");
		EntityManager em = emf.createEntityManager();
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(centro1);
				// TODO Auto-generated method stub

			}
		}, em);
		List<Centro> centrosRec = em.createQuery("Select c From Centro c").getResultList();
		Assert.assertEquals(centrosRec.size(), 1);
	}
}
