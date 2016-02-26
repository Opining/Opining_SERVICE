package br.com.opining.services;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.UserDAO;
import br.com.opining.database.UserInformationDAO;
import br.com.opining.library.model.NewUser;
import br.com.opining.library.model.User;
import br.com.opining.library.model.UserInformations;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.rest.security.Authorizator;
import br.com.opining.util.DataValidator;

@Path("user")
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class.getName());
	
	/**
	 * This method create a new User (After check if that can be inserted) 
	 * its UserInformations and insert it in the database.
	 * If the received user contains a existent login name this method return a 
	 * response that contains a OpiningError, otherwise returns the created user with all informations.
	 * 
	 * @param newUser
	 * @return a response that can contains a User or a OpiningError
	 * 
	 * @author José Renan
	 */
	@PermitAll
	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(NewUser newUser){
		
		logger.info(newUser.getNewLogin() + " is requesting be an Opining user");
		logger.info("Starting a new user creation " + newUser.getNewPassword());
		
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;
		
		OpiningError error = DataValidator.validateInsertion(newUser);
		
		if (error == null) {
			UserInformations userInformations = new UserInformations();
			UserInformationDAO userInformationDAO = new UserInformationDAO();
			User user = newUser.toUser();
			
			userDAO.insert(user);
			
			userInformations.setUser(user);
			userInformationDAO.insert(userInformations);
			
			builder = Response.status(Response.Status.OK).entity(user);
			logger.info("New user registered.");
		
		} else {
			builder = Response.status(Response.Status.CONFLICT).entity(error);
			logger.warn(error.getMessage() + ": " + newUser.getNewLogin());
		}
		
		return builder.build();
	}
	
	/**
	 * This method invalidates a user changing login to null 
	 * and deletes his AcessKey from BD.
	 * 
	 * @param user
	 * @return Response
	 * 
	 * @author Diego Takei and José Renan
	 */
	@RolesAllowed("user")
	@POST
	@Path("/invalidate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response invalidateUser(User user){
		logger.info(user.getLogin() + " is requesting be an invalidation");
		logger.info("Starting to invalidate an user");
		
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;
		
		user = userDAO.getByLogin(user.getLogin());
		user.setLogin(null);
		
		Authorizator auth = new Authorizator();
		auth.deleteKey(user);
		
		logger.info("Updating the database to invalidate user");
		userDAO.update(user);
		logger.info("User invalidated");
		
		builder = Response.status(Response.Status.OK);
		
		return builder.build();
	}
	
	/**
	 * This method updates a user after check if that can be updated
	 * 
	 * @param newUser
	 * @return a Response that can contains a User or a OpiningError
	 * 
	 * @author Diego Takei
	 */
	@RolesAllowed("user")
	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateUser(@QueryParam(value = "login") String login, NewUser newUser){
		
		logger.info(login + "is requesting an update");
		logger.info("Starting to update an user");
		
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getByLogin(login);
		ResponseBuilder builder;
		
		OpiningError error = DataValidator.validateUpdate(user, newUser);
		
		if(error == null){
			logger.info("Updating the user");
			
			user = newUser.toUser(user.getIdUser());
			
			userDAO.update(user);
			logger.info("The user has been updated");
			
			builder = Response.status(Response.Status.OK).entity(user);
			
		}else{
			builder = Response.status(Response.Status.CONFLICT).entity(error);
			logger.warn("Could not update the user");
		}
		return builder.build();
	}
	
	/**
	 * This method list all valid users
	 * 
	 * @param user
	 * @return List<User>
	 * 
	 * @see {@link br.com.opining.services.UserService#listUsers() listUsers()}
	 * 
	 * @author José Renan
	 */
	@RolesAllowed("user")
	@GET
	@Path("/list/valid")
	@Consumes("application/json")
	@Produces("application/json")
	public List<User> listValidUsers(){
		UserDAO userDAO = new UserDAO();
		return userDAO.getValidUsers();
	}
	
	/**
	 * This method list all users
	 * 
	 * @param user
	 * @return List<User>
	 * 
	 * @see {@link br.com.opining.services.UserService#listValidUsers() listValidUsers()}
	 * 
	 * @author José Renan
	 */
	@RolesAllowed("user")
	@GET
	@Path("/list/all")
	@Consumes("application/json")
	@Produces("application/json")
	public List<User> listUsers(){
		UserDAO userDAO = new UserDAO();
		List<User> list = userDAO.getAll();
		
		return list;
	}
}
