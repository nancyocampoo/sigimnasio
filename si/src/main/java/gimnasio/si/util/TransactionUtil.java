package gimnasio.si.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionUtil {

	public static void doTransaction(Transaction t, EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			t.run(em);
			tx.commit();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		}
	}

}
