package br.com.opining.util.validation;

import br.com.opining.database.PolarizedRoomDAO;
import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.library.model.room.polarized.NewPolarizedRoom;
import br.com.opining.library.model.room.polarized.PolarizedRoom;
import br.com.opining.util.ErrorFactory;

public class PolarizedRoomValidation implements DataValidator<NewPolarizedRoom, Integer> {
	
	PolarizedRoomDAO polarizedRoomDAO;
	
	public PolarizedRoomValidation() {
		polarizedRoomDAO = new PolarizedRoomDAO();
	}
	
	@Override
	public OpiningError validateInsertion(NewPolarizedRoom newEntity) {
		
		OpiningError error = checkDebateAttributes(newEntity);
		
		if (error != null)
			return error;
		
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getByLogin(newEntity.getCreator().getLogin());
		
		if (user == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
		
		return null;
	}

	@Override
	public OpiningError validateUpdate(Integer id, NewPolarizedRoom newEntity) {
		
		PolarizedRoom polarizedRoom = polarizedRoomDAO.getById(id);
		
		if (polarizedRoom == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.ROOM_NOT_FOUND);
		
		OpiningError error = checkDebateAttributes(newEntity);
		
		if (error != null)
			return error;
		
		return null;
	}
	
	public OpiningError validateEntrance(String password, int idRoom, String login){
		
		PolarizedRoom room = new PolarizedRoom();
		User user = new User();
		
		PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
		UserDAO userDAO = new UserDAO();
		
		user = userDAO.getByLogin(login);
		room = polarizedRoomDAO.getById(idRoom);
		
		if(user == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
		
		if(room == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.ROOM_NOT_FOUND);
		
		if(room.getIsPrivate() && !room.getPassword().equals(password))
			return ErrorFactory.getErrorFromIndex(ErrorFactory.INCORRECT_PASSWORD);
			
		return null;
		
	}
	
	private static OpiningError checkDebateAttributes(NewPolarizedRoom newPolarizedRoom){
		
		if (newPolarizedRoom.getIsPrivate()) {
			
			if (newPolarizedRoom.getNewPassword() == null || newPolarizedRoom.getNewPassword().length() < PolarizedRoom.PASSWORD_MIN_LENGHT)
				return ErrorFactory.getErrorFromIndex(ErrorFactory.PASSWORD_IS_TOO_SHORT);
			
			if (newPolarizedRoom.getNewPassword() == null || newPolarizedRoom.getNewPassword().length() > PolarizedRoom.PASSWORD_MAX_LENGHT)
				return ErrorFactory.getErrorFromIndex(ErrorFactory.PASSWORD_IS_TOO_LONG);
		}
		
		if (newPolarizedRoom.getNewSubject() == null || newPolarizedRoom.getNewSubject().length() < PolarizedRoom.SUBJECT_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.SUBJECT_IS_TOO_SHORT);
		
		if (newPolarizedRoom.getNewSubject() == null || newPolarizedRoom.getNewSubject().length() > PolarizedRoom.SUBJECT_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.SUBJECT_IS_TOO_LONG);
		
		if (newPolarizedRoom.getNewArgumentTimeInMinutes() == null || newPolarizedRoom.getNewArgumentTimeInMinutes() < PolarizedRoom.MIN_ARGUMENT_TIME_IN_MINUTES)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.ARGUMENT_TIME_TOO_SHORT);
		
		if (newPolarizedRoom.getNewArgumentTimeInMinutes() == null || newPolarizedRoom.getNewArgumentTimeInMinutes() > PolarizedRoom.MAX_ARGUMENT_TIME_IN_MINUTES)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.ARGUMENT_TIME_TOO_LONG);
		
		return null;
	}

	
}
