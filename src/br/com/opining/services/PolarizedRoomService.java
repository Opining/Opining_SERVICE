package br.com.opining.services;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.PolarizedDebaterDAO;
import br.com.opining.database.PolarizedRoomDAO;
import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.error.OpiningError;
import br.com.opining.library.model.room.polarized.NewPolarizedRoom;
import br.com.opining.library.model.room.polarized.PolarizedRoom;
import br.com.opining.util.validation.PolarizedRoomValidation;
import br.com.opining.library.model.room.participant.PolarizedDebater;

@Path("debate/polarized")
public class PolarizedRoomService {
	
	private static final Logger logger = LogManager.getLogger(PolarizedRoomService.class.getName());
	
	/**
	 * This method create a new Debate (After check if that can be created) 
	 * It's PolarizedRoom and insert it in the database.
	 * If the received PolarizedRoom contains a existent login name this method return a 
	 * response that contains a OpiningError, otherwise returns the created room with all informations.
	 * 
	 * @param newDebate
	 * @return a response that can contains a PolarizedRoom or a OpiningError
	 * 
	 * @author Diego Takei and José Renan
	 */	
	@RolesAllowed("user")
	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createDebate(NewPolarizedRoom newDebate){
		
		logger.info("Starting new room creating");
		
		OpiningError error = new PolarizedRoomValidation().validateInsertion(newDebate);
		ResponseBuilder builder;
		
		if (error == null) {
			
			UserDAO userDAO = new UserDAO();
			User user = userDAO.getByLogin(newDebate.getCreator().getLogin());
			newDebate.setCreator(user);
			
			PolarizedRoom polarizedRoom = newDebate.toPolarizedRoom();
			
			PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
			polarizedRoomDAO.insert(polarizedRoom);
			
			builder = Response.status(Response.Status.OK).entity(polarizedRoom);
			
			logger.info("Room succefully created");
		
		} else {
			builder = Response.status(Response.Status.CONFLICT).entity(error);
			logger.warn("Room creation is failed with error code: " + error.getCode());
		}
		
		return builder.build();
	}
	
	/**
	 * This method allows a user entering a room (After check if that exists)
	 * After validatiion, the method create a Debater that will be inserted into a room
	 * 
	 * @param password
	 * @param idRoom
	 * @param login
	 * 
	 * @return a response that can contains a debater or a OpiningError
	 * 
	 * @author Diego Takei and José Renan
	 */	
	@RolesAllowed("user")
	@POST
	@Path("/enter")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public Response enterDebate(@FormParam("password") String password, 
								@FormParam("idRoom") Integer idRoom, 
								@FormParam("login") String login){
		
		logger.info("Starting new user entrance");
		
		OpiningError error = new PolarizedRoomValidation().validateEntrance(password, idRoom, login);
		ResponseBuilder builder;
		
		if(error == null){
			
			PolarizedDebater debater = new PolarizedDebater();
			PolarizedRoom room = new PolarizedRoom();
			User user = new User();
			
			PolarizedDebaterDAO debaterDAO = new PolarizedDebaterDAO();
			PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
			UserDAO userDAO = new UserDAO();
			
			user = userDAO.getByLogin(login);
			room = polarizedRoomDAO.getById(idRoom);
			
			debater.setRoom(room);
			debater.setUser(user);
			
			debaterDAO.insert(debater);
			debater.setSide(null);
			
			builder = Response.status(Response.Status.OK).entity(debater);
			
		}else{
			
			builder = Response.status(Response.Status.CONFLICT).entity(error);
			logger.warn("User entrance is failed with error code: " + error.getCode());
		}
		
		return builder.build();
	}
	
	/**
	 * This method allows a user leaving a room
	 * Invalidating the debater
	 * 
	 * @param debater
	 * 
	 * @return a response that can contains a debater or null
	 * 
	 * @author Diego Takei and José Renan
	 */	
	@POST
	@Path("/leave")
	@Consumes("application/json")
	@Produces("application/json")
	public Response leaveDebate(PolarizedDebater debater){
		
		PolarizedDebaterDAO debaterDAO = new PolarizedDebaterDAO();
		ResponseBuilder builder;
		
		debater.setIsValid(false);
		debaterDAO.update(debater);
		
		
		builder = Response.status(Response.Status.OK);
		
		return builder.build();
	}
	
	/**
	 * This method list the existing debates
	 * 
	 * @return a response that can contains a polarizedRoom list or null
	 * 
	 * @author José Renan
	 */	
	@RolesAllowed("user")
	@GET
	@Path("/list")
	@Produces("application/json")
	public Response listDebates(){
	
		PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
		
		List<PolarizedRoom> polarizedRoom = polarizedRoomDAO.getAll();
		
		ResponseBuilder builder;
		
		builder = Response.status(Response.Status.OK).entity(polarizedRoom);
		
		return builder.build();
	}
	
	/**
	 * This method list the existing debates by Subject
	 * 
	 * @return a response that can contains a polarizedRoom list or null
	 * 
	 * @author José Renan
	 */	
	@RolesAllowed("user")
	@POST
	@Path("/list/subject")
	@Produces("application/json")
	public Response listDebatesBySubject(@QueryParam("q") String subject){
	
		PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
		
		List<PolarizedRoom> polarizedRoom = polarizedRoomDAO.getBySubject(subject);
		
		ResponseBuilder builder;
		
		builder = Response.status(Response.Status.OK).entity(polarizedRoom);
		
		return builder.build();
	}
}
