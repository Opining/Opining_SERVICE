package br.com.opining.test.database.user;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserInsertTest {

	// Insert the first user
	@Test
	public void test1() {

		User user = new User();
		user.setLogin("Teste");
		user.setName("Teste da Silva Junior");
		user.setPassword("testando123");

		UserDAO userDao = new UserDAO();
		userDao.insert(user);

		assertNotNull(user.getIdUser());

	}

	// Insert a repeated user
	@Test
	public void test2() {

		User user = new User();
		user.setLogin("Teste");
		user.setName("Teste da Silva Junior");
		user.setPassword("testando123");

		UserDAO userDao = new UserDAO();
		userDao.insert(user);

		assertNull(user.getIdUser());

	}

	// Insert a user with a login too big
	@Test
	public void test3() {

		User user = new User();
		user.setLogin("123456789123456789123456789");
		user.setName("Teste da Silva Junior");
		user.setPassword("testando123");

		UserDAO userDao = new UserDAO();
		userDao.insert(user);

		assertNull(user.getIdUser());

	}

	// Insert a user with a password too big
	@Test
	public void test4() {

		User user = new User();
		user.setLogin("1234567");
		user.setName("Teste da Silva Junior");
		user.setPassword("testando123123123123123");

		UserDAO userDao = new UserDAO();
		userDao.insert(user);

		assertNull(user.getIdUser());

	}
}
