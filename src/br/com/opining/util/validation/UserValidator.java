package br.com.opining.util.validation;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.NewUser;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.util.validation.DataValidator;
import br.com.opining.util.ErrorFactory;

public class UserValidator implements DataValidator<NewUser, String> {
	
	private UserDAO userDAO;
	
	public UserValidator(){
		userDAO = new UserDAO();
	}
	
	@Override
	public OpiningError validateInsertion(NewUser newUser) {
		
		OpiningError error = checkUserAttributes(newUser);
		
		if (error != null)
			return error;
		
		User bdUser = userDAO.getByLogin(newUser.getNewLogin());
		
		if(bdUser != null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_LOGIN);
		
		return null;
	}
	
	@Override
	public OpiningError validateUpdate(String oldLogin, NewUser newUser) {
		
		OpiningError error = null;
		
		error = checkUserAttributes(newUser);
		
		if(error != null)
			return error;
		
		User bdUser = userDAO.getByLogin(oldLogin);
		
		if (bdUser == null)
			return ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
		
		if (!oldLogin.equalsIgnoreCase(newUser.getNewLogin())){

			bdUser = userDAO.getByLogin(newUser.getNewLogin());
			
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
