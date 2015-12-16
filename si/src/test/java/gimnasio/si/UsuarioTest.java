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
/**
 *Creacion del Test de Usuario, para comprobar su funcionamiento y crear datos
 *  @author Nancy Ocampo, Jenifer Vázquez, Nuria Canle
 */
public class UsuarioTest {

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
	 * Metodo para crear las pruebas sobre los Usuarios en la base de datos. 
	 * En el creamos 2 usuarios, con sus atributos correspondientes y comprobamos
	 * que los datos sean guardados correctamente en la BD
	 * @return void
	 */
	@Test
	public void testCreateUsuario() {

		EntityManager em = emf.createEntityManager();
		
		//Creación del primer usuario
		final Usuario usuario = new Usuario();
		usuario.setDni("55555555A");
		usuario.setNombre("Fito");
		usuario.setDomicilio("Rua Nova");
		usuario.setFechaNacimiento(new Date(1992, 5, 1));
		usuario.setOcupacion(Ocupacion.UNIVERSITARIO);
		usuario.setSexo(Sexo.Masculino);
		usuario.setTelefono("666366662");
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(usuario);
			}
		}, em);
		
		//Creación del segundo usuario
		final Usuario usuario2 = new Usuario();
		usuario2.setDni("44444444A");
		usuario2.setNombre("Laura");
		usuario2.setDomicilio("Lagunas");
		usuario2.setFechaNacimiento(new Date(1993, 2, 4));
		usuario2.setOcupacion(Ocupacion.UNIVERSITARIO);
		usuario2.setSexo(Sexo.Femenino);
		usuario2.setTelefono("666555222");
		TransactionUtil.doTransaction(new Transaction() {
			public void run(EntityManager em) {
				em.persist(usuario2);
			}
		}, em);
		
		Usuario usuarioRec = em.createQuery("SELECT u FROM Usuario u where u.dni=:dni", Usuario.class)
				.setParameter("dni", usuario.getDni()).getSingleResult();
		assertEquals(usuarioRec.getDni(), usuario.getDni());
		
		Usuario usuarioRec2 = em.createQuery("SELECT u FROM Usuario u where u.dni=:dni", Usuario.class)
				.setParameter("dni", usuario2.getDni()).getSingleResult();
		assertEquals(usuarioRec2.getDni(), usuario2.getDni());
	}
}
