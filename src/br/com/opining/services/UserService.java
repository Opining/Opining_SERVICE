package br.com.opining.services;

import java.sql.SQLException;
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
import org.hibernate.HibernateException;
import org.hibernate.exception.GenericJDBCException;

import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.util.ErrorFactory;

@Path("user")
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class.getName());
	
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
		
		try {
			userDAO.insert(user);
			builder = Response.status(Response.Status.OK).entity(user);
			logger.info("New user registered.");
		} catch (GenericJDBCException ex) {
			OpiningError error = ErrorFactory.getErrorFromIndex(ErrorFactory.DUPLICATE_LOGIN);
			builder = Response.status(Response.Status.CONFLICT).entity(error);
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
		return null;
	}
}
