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

/**
 *Creacion del Test de Centro, para comprobar su funcionamiento y crear datos
 *  @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 */
public class CentroTest {

	private static EntityManagerFactory emf;

	/**
	 * Metodo que cree la EntityManagerFactory
	 * @return void
	 */
	@BeforeClass
	public static void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("gimnasio-database");
	}

	/**
	 * Metodo para cerrar la conexion de la base de datos. Se ejecuta cuando se terminan todos los test
	 * @return void
	 */
	@AfterClass
	public static void closeEntityManagerFactory() {
		emf.close();
	}
	/**
	 * Metodo para crear las pruebas sobre los centros en la base de datos. 
	 * En el creamos un centro con sus atributos correspondientes y comprobamos
	 * que los datos sean guardados correctamente en la BD
	 * @return void
	 */
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
			}
		}, em);
		List<Centro> centrosRec = em.createQuery("Select c From Centro c").getResultList();
		Assert.assertEquals(centrosRec.size(), 1);
	}
}
