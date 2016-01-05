package br.com.opining.services;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
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
	public Response login(User user){
		
		logger.info(user.getLogin() + " is logging now");
		
		ResponseBuilder builder;
		User bdUser = checkIfUserExists(user);
		
		if(bdUser == null){
			OpiningError error = ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
			builder = Response.status(Status.OK).entity(error);
			
			logger.warn(user.getLogin() + " isn't succefully logged in with error code: " + error.getCode());
			
		} else if (user.getPassword().equals(bdUser.getPassword())){
			Authorizator auth = new Authorizator();
			builder = Response.status(Status.OK).entity(auth.insertKey(bdUser));
			
			setLoginInformations(user.getLogin());
			
			logger.info(user.getLogin() + " is succefully logged in");
			
		} else {
			OpiningError error = ErrorFactory.getErrorFromIndex(ErrorFactory.INCORRECT_PASSWORD);
			builder = Response.status(Status.OK).entity(error);
			
			logger.warn(user.getLogin() + " isn't succefully logged in with error code: " + error.getCode());
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
	 * @author [Author Name]
	 */
	@RolesAllowed("user")
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(User user){
		//TODO
		return null;
	}
	
	private void setLoginInformations(String login){
		//TODO
	}
	
	private void setLogoutInformations(String login){
		//TODO
	}
	
	private static User checkIfUserExists(User user){
		UserDAO userDAO = new UserDAO();
		User bdUser = userDAO.getByLogin(user.getLogin());
		
		return bdUser;
		
	}
}
