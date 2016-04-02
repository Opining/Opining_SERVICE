package br.com.opining.test.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningValidateException;

public class ValidateUserTest {
	
	private User user = new User();
	
	@Test
	public void validUser() {
		
		user.setLogin("joserenan");
		user.setName("José Renan Silva Luciano");
		user.setPassword("123456");
		
	}
	
	@Test(expected = OpiningValidateException.class)
	public void loginTooLong() {
		user.setLogin("joserenan7687649");
		user.setName("José Renan Silva Luciano");
		user.setPassword("123456");
	}

}
