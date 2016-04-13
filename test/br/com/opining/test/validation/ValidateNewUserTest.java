package br.com.opining.test.validation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.opining.library.model.NewUser;
import br.com.opining.library.model.error.Errors;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.library.model.error.OpiningValidateException;

public class ValidateNewUserTest {
	
	private NewUser newUser = new NewUser();
	
	@Test
	public void validateNewUser(){
		
		newUser.setNewLogin("joserenan");
		newUser.setNewName("José Renan Silva Luciano");
		newUser.setNewPassword("123456");
		
	}
		
	@Test
	public void loginTooShort(){
		
		OpiningError error = null;
		
		try{
			newUser.setNewLogin("abc");
			newUser.setNewName("José Renan Silva Luciano");
			newUser.setNewPassword("123456");
			newUser.toUser();
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
			newUser.setNewLogin("joserenan7687649");
			newUser.setNewName("José Renan Silva Luciano");
			newUser.setNewPassword("123456");
			newUser.toUser();
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
			newUser.setNewLogin("José# Renan");
			newUser.setNewName("José Renan Silva Luciano");
			newUser.setNewPassword("123456");
			newUser.toUser();
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
			newUser.setNewLogin("joserenan");
			newUser.setNewName("José Renan Silva Luciano");
			newUser.setNewPassword("12");
			newUser.toUser();
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
			newUser.setNewLogin("joserenan");
			newUser.setNewName("José Renan Silva Luciano");
			newUser.setNewPassword("12131111111111111111111");
			newUser.toUser();
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
			newUser.setNewLogin("joserenan");
			newUser.setNewName("Jo");
			newUser.setNewPassword("123456789");
			newUser.toUser();
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
			newUser.setNewLogin("joserenan");
			newUser.setNewName("José Renan Silva Luciano XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
					+ "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			newUser.setNewPassword("123456789");
			newUser.toUser();
		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.NAME_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
}
