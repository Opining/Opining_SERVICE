package br.com.opining.test.services.room.polarized;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.opining.library.model.error.Errors;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.library.model.error.OpiningValidateException;
import br.com.opining.library.model.room.polarized.PolarizedRoom;

public class PolarizedRoomServiceTest {

	private PolarizedRoom polarizedRoom = new PolarizedRoom();
	
	@Test
	public void validPolarizedRoom() {
		
		polarizedRoom.setArgumentTimeInMinutes(1);
		polarizedRoom.setSubject("Teste");
		
	}
	
	@Test
	public void argumentTimeTooShort(){
		
		OpiningError error = null;
		
		try{
			polarizedRoom.setArgumentTimeInMinutes(0);
			polarizedRoom.setSubject("Teste");

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
			polarizedRoom.setArgumentTimeInMinutes(3);
			polarizedRoom.setSubject("Teste");

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
			polarizedRoom.setArgumentTimeInMinutes(1);
			polarizedRoom.setSubject("");

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
			polarizedRoom.setArgumentTimeInMinutes(1);
			polarizedRoom.setSubject("Teste123456789231231231323123121313213113165468464484866844684868466486486848646");

		}catch(OpiningValidateException ex){
			error = ex.getOpiningError();
		}finally{
			int expected = Errors.SUBJECT_IS_TOO_LONG.getOpiningError().getCode();
			assertEquals(expected, error.getCode());
		}
	}

}
