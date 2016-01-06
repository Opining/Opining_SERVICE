package br.com.opining.util;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;

public class DataValidator {
	
	public static OpiningError validateInsertion(User user){
		
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getByLogin(user.getLogin());
		
		if(bdUser != null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_LOGIN);
		
		return null;
	}
	
	public static OpiningError validateUpdate(User user){
		
		OpiningError error = validateInsertion(user);
		
		if(error != null)
			return error;
		
		return null;
	}
	
	public static OpiningError checkUserAttributes(User user){
		
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getByLogin(user.getLogin());
		
		if(bdUser == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
		
		if (!bdUser.getPassword().equals(user.getPassword()))
			return ErrorFactory.getErrorFromIndex(ErrorFactory.INCORRECT_PASSWORD);
		
		return null;
	}
	
	
}
