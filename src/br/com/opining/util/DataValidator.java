package br.com.opining.util;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.NewUser;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;

public class DataValidator {
	
	/**
	 * This method check if that user can be inserted on BD.
	 * 
	 * @param newUser
	 * @return OpiningError
	 */
	public static OpiningError validateInsertion(NewUser newUser){
		
		OpiningError error = checkUserAttributes(newUser);
		
		if (error != null)
			return error;
		
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getByLogin(newUser.getNewLogin());
		
		if(bdUser != null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_LOGIN);
		
		return null;
	}
	
	/**
	 * This method check if that user can be updated on BD.
	 * 
	 * @param newUser
	 * @return OpiningError
	 */
	public static OpiningError validateUpdate(User oldUser, NewUser newUser){
		
		OpiningError error = null;
		
		error = DataValidator.checkUserAttributes(newUser);
		
		if(error != null)
			return error;
		
		if (!oldUser.getLogin().equalsIgnoreCase(newUser.getNewLogin())){
			UserDAO userDAO = new UserDAO();
			User bdUser = userDAO.getByLogin(newUser.getNewLogin());
			
			if (bdUser != null)
				return ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_LOGIN);
		}		
		
		return null;
	}
	
	/**
	 * This method checks if the user parameters are according to predefined standards
	 * 
	 * @param newUser
	 * @return OpiningError
	 */
	private static OpiningError checkUserAttributes(NewUser newUser){
		
		if (newUser.getNewLogin() == null || newUser.getNewLogin().length() < User.LOGIN_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.LOGIN_IS_TOO_SHORT);
		
		if (newUser.getNewPassword() == null || newUser.getNewPassword().length() < User.PASSWORD_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.PASSWORD_IS_TOO_SHORT);
		
		if (newUser.getNewName() == null || newUser.getNewName().length() < User.NAME_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.NAME_IS_TOO_SHORT);
		
		if (newUser.getNewPassword().length() > User.PASSWORD_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.PASSWORD_IS_TOO_LONG);
		
		if (newUser.getNewName().length() > User.NAME_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.NAME_IS_TOO_LONG);
		
		if (newUser.getNewLogin().length() > User.LOGIN_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.LOGIN_IS_TOO_LONG);
		
		return null;
	}
	
	
}
