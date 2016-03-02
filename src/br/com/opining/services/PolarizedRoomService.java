package br.com.opining.services;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
		
		if(error==null){
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
	
	@PermitAll
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
}
