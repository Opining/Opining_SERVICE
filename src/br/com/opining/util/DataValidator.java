package br.com.opining.util;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;

public class DataValidator {
	
	/**
	 * This method check if that user can be inserted on BD.
	 * 
	 * @param user
	 * @return OpiningError
	 */
	public static OpiningError validateInsertion(User user){
		
		OpiningError error = checkUserAttributes(user);
		
		if (error != null)
			return error;
		
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getByLogin(user.getLogin());
		
		if(bdUser != null && bdUser.getIdUser() != user.getIdUser())
			return ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_LOGIN);
		
		bdUser = userDAO.getByToken(user.getToken());
		
		if(bdUser != null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_TOKEN);
		
		return null;
	}
	
	/**
	 * This method check if that user can be updated on BD.
	 * 
	 * @param user
	 * @return OpiningError
	 */
	public static OpiningError validateUpdate(User user){
		
		OpiningError error = validateInsertion(user);
		
		if(error != null)
			return error;
		
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getValidUser(user.getIdUser());
		
		if (bdUser == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.USER_IS_INVALID);
		
		return null;
	}
	
	/**
	 * This method checks if the user parameters are according to predefined standards
	 * 
	 * @param user
	 * @return OpiningError
	 */
	private static OpiningError checkUserAttributes(User user){
		
		if (user.getLogin() == null || user.getLogin().length() < User.LOGIN_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.LOGIN_IS_TOO_SHORT);
		
		if (user.getPassword() == null || user.getPassword().length() < User.PASSWORD_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.PASSWORD_IS_TOO_SHORT);
		
		if (user.getName() == null || user.getName().length() < User.NAME_MIN_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.NAME_IS_TOO_SHORT);
		
		if (user.getPassword().length() > User.PASSWORD_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.PASSWORD_IS_TOO_LONG);
		
		if (user.getName().length() > User.NAME_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.NAME_IS_TOO_LONG);
		
		if (user.getLogin().length() > User.LOGIN_MAX_LENGHT)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.LOGIN_IS_TOO_LONG);
		
		return null;
	}
	
	
}
