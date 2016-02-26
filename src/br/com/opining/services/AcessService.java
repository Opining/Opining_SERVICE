package br.com.opining.services;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.UserCredentials;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.rest.security.Authorizator;
import br.com.opining.util.ErrorFactory;

@Path("acess")
public class AcessService {
	
	private static final Logger logger = LogManager.getLogger(AcessService.class.getName());
	
	/**
	 * This method check if that user exists in the database and authorize
	 * his to use others services using the AcessKey
	 * 
	 * @param user
	 * @return a response that can contains a AcessKey or a Message
	 * 
	 * @author José Renan
	 */
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(@QueryParam("login") String login, String password){
		
		logger.info(login + " is logging in now");
		
		ResponseBuilder builder;
		User bdUser = checkIfUserExists(login);
		
		if(bdUser == null){
			OpiningError error = ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error);
			
			logger.warn(login + " isn't succefully logged in with error code: " + error.getCode());
			
		} else if (password.equals(bdUser.getPassword())){
			
			Authorizator auth = new Authorizator();
			UserCredentials userCredentials = new UserCredentials();
			
			userCredentials.setKey(auth.insertKey(bdUser).getKey());
			userCredentials.setUser(bdUser);
			
			builder = Response.status(Status.OK).entity(userCredentials);
			
			setLoginInformations(login);
			
			logger.info(login + " is succefully logged in");
			
		} else {
			OpiningError error = ErrorFactory.getErrorFromIndex(ErrorFactory.INCORRECT_PASSWORD);
			builder = Response.status(Status.EXPECTATION_FAILED).entity(error);
			
			logger.warn(login + " isn't succefully logged in with error code: " + error.getCode());
		}
		
		return builder.build();
	}
	
	/**
	 * This method check if that user have an AcessKey
	 * and remove that on database
	 * 
	 * @param user
	 * @return a response that can contains a Message
	 * 
	 * @author José Renan
	 */
	@RolesAllowed("user")
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(User user){
		
		logger.info(user.getLogin() + " is logging out now");
		
		ResponseBuilder builder;
		
		Authorizator auth = new Authorizator();
		auth.deleteKey(user);
		
		builder = Response.status(Response.Status.OK);
		logger.info(user.getLogin() + " is succefully logged out");
		
		setLogoutInformations(user.getLogin());
		
		return builder.build();
	}
	
	private void setLoginInformations(String login){
		//TODO
	}
	
	private void setLogoutInformations(String login){
		//TODO
	}
	
	private static User checkIfUserExists(String login){
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getByLogin(login);
		
		return bdUser;
		
	}
}
