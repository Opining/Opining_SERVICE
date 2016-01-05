package br.com.opining.services;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.UserDAO;
import br.com.opining.database.UserInformationDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.UserInformations;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.util.DataValidator;

@Path("user")
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class.getName());
	
	/**
	 * This method create a new User its UserInformations and insert it in the database.
	 * If the received user contains a existent login name this method return a 
	 * response that contains a OpiningError, otherwise returns the created user with all informations.
	 * 
	 * @param user
	 * @return a response that can contains a User or a Message
	 * 
	 * @author José Renan
	 */
	@PermitAll
	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(User user){
		logger.info(user.getLogin() + " is requesting be an Opining user");
		logger.info("Starting a new user creation");
		
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;
		
		OpiningError error = DataValidator.validateInsertion(user);
		
		if (error == null) {
			UserInformations userInformations = new UserInformations();
			UserInformationDAO userInformationDAO = new UserInformationDAO();
			
			userDAO.insert(user);
			
			userInformations.setUser(user);
			userInformationDAO.insert(userInformations);
			
			builder = Response.status(Response.Status.OK).entity(user);
			logger.info("New user registered.");
		
		} else {
			builder = Response.status(Response.Status.CONFLICT).entity(error);
			logger.warn("This login name is in use: " + user.getLogin());
		}
		
		return builder.build();
	}
	
	@RolesAllowed("user")
	@POST
	@Path("/invalidate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response invalidateUser(User user){
		return null;
	}
	
	@RolesAllowed("user")
	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateUser(User user){
		return null;
	}
	
	@RolesAllowed("user")
	@GET
	@Path("/list/all")
	@Consumes("application/json")
	@Produces("application/json")
	public List<User> listUsers(){
		
		UserDAO userDAO = new UserDAO();
		return userDAO.getAll();
	}
}
