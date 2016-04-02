package br.com.opining.test.validation;

import static org.junit.Assert.*;
import org.junit.Test;

import br.com.opining.library.model.User;
import br.com.opining.library.model.error.Errors;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.library.model.error.OpiningValidateException;

public class ValidateUserTest {
	
	private User user = new User();
	
	@Test
	public void validUser() {
		
		user.setLogin("joserenan");
		user.setName("José Renan Silva Luciano");
		user.setPassword("123456");
		
	}
	
	@Test
	public void loginTooShort(){
		
		OpiningError error = null;
		
		try{
			user.setLogin("abc");
			user.setName("José Renan Silva Luciano");
			user.setPassword("123456");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.LOGIN_IS_TOO_SHORT.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void loginTooLong() {
		
		OpiningError error = null;
		
		try{
			user.setLogin("joserenan7687649");
			user.setName("José Renan Silva Luciano");
			user.setPassword("123456");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.LOGIN_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void loginFormatNotAccepted(){

		OpiningError error = null;
		
		try{
			user.setLogin("José# Renan");
			user.setName("José Renan Silva Luciano");
			user.setPassword("123456");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.LOGIN_FORMAT_NOT_ACCEPTED.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void passwordTooShort(){

		OpiningError error = null;
		
		try{
			user.setLogin("joserenan");
			user.setName("José Renan Silva Luciano");
			user.setPassword("12");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.PASSWORD_IS_TOO_SHORT.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void passwordTooLong(){

		OpiningError error = null;
		
		try{
			user.setLogin("joserenan");
			user.setName("José Renan Silva Luciano");
			user.setPassword("12131111111111111111111");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.PASSWORD_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void nameTooShort(){

		OpiningError error = null;
		
		try{
			user.setLogin("joserenan");
			user.setName("Jo");
			user.setPassword("123456789");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.NAME_IS_TOO_SHORT.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void nameTooLong(){

		OpiningError error = null;
		
		try{
			user.setLogin("joserenan");
			user.setName("José Renan Silva Luciano XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
					+ "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			user.setPassword("123456789");
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.NAME_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
}
