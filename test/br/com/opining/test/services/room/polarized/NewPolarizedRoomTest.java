package br.com.opining.test.services.room.polarized;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.opining.library.model.error.Errors;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.library.model.error.OpiningValidateException;
import br.com.opining.library.model.room.polarized.NewPolarizedRoom;

public class NewPolarizedRoomTest {
	
private NewPolarizedRoom newPolarizedRoom = new NewPolarizedRoom();
	
	@Test
	public void validPolarizedRoom() {
		
		newPolarizedRoom.setNewArgumentTimeInMinutes(1);
		newPolarizedRoom.setNewSubject("Teste");
		newPolarizedRoom.setNewPassword("123456");
		
		newPolarizedRoom.toPolarizedRoom();
		
	}
	
	@Test
	public void passwordTooShort(){
		OpiningError error = null;
		
		try{
			newPolarizedRoom.setNewArgumentTimeInMinutes(1);
			newPolarizedRoom.setNewSubject("Teste");
			newPolarizedRoom.setNewPassword("12");
			newPolarizedRoom.toPolarizedRoom();

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
			newPolarizedRoom.setNewArgumentTimeInMinutes(1);
			newPolarizedRoom.setNewSubject("Teste");
			newPolarizedRoom.setNewPassword("1212312312312312312312321");
			newPolarizedRoom.toPolarizedRoom();

		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.PASSWORD_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void argumentTimeTooShort(){
		
		OpiningError error = null;
		
		try{
			newPolarizedRoom.setNewArgumentTimeInMinutes(0);
			newPolarizedRoom.setNewSubject("Teste");
			newPolarizedRoom.setNewPassword("123456");
			newPolarizedRoom.toPolarizedRoom();

		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.ARGUMENT_TIME_TOO_SHORT.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void argumentTimeTooLong(){
		
		OpiningError error = null;
		
		try{
			newPolarizedRoom.setNewArgumentTimeInMinutes(3);
			newPolarizedRoom.setNewSubject("Teste");
			newPolarizedRoom.setNewPassword("123456");
			newPolarizedRoom.toPolarizedRoom();

		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.ARGUMENT_TIME_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void subjectTooShort(){
		
		OpiningError error = null;
		
		try{
			newPolarizedRoom.setNewArgumentTimeInMinutes(1);
			newPolarizedRoom.setNewSubject("");
			newPolarizedRoom.setNewPassword("123456");
			newPolarizedRoom.toPolarizedRoom();

		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.SUBJECT_IS_TOO_SHORT.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}
	
	@Test
	public void subjectTooLong(){
		
		OpiningError error = null;
		
		try{
			newPolarizedRoom.setNewArgumentTimeInMinutes(1);
			newPolarizedRoom.setNewSubject("Teste123456789231231231323123121313213113165468464484866844684868466486486848646");
			newPolarizedRoom.setNewPassword("123456");
			newPolarizedRoom.toPolarizedRoom();

		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.SUBJECT_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}


}
