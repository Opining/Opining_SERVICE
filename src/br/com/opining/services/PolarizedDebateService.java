package br.com.opining.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.opining.database.PolarizedDebaterDAO;
import br.com.opining.database.PolarizedRoomDAO;
import br.com.opining.database.UserDAO;
import br.com.opining.library.model.User;
import br.com.opining.library.model.room.polarized.PolarizedRoom;
import br.com.opining.library.model.room.polarized.participant.PolarizedDebater;

@Path("debate/polarized")
public class PolarizedDebateService {
	
	@POST
	@Path("/createDebate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createDebate(PolarizedRoom debate){
		
		PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
		polarizedRoomDAO.insert(debate);
		
		ResponseBuilder builder;
		
		builder = Response.status(Response.Status.OK).entity(debate);
		
		return builder.build();
	}
	
	@POST
	@Path("/enterDebate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response enterDebate(String password, @QueryParam("idRoom") Integer idRoom, @QueryParam("idUser") Integer idUser){
		
		PolarizedDebater debater = new PolarizedDebater();
		User user = new User();
		PolarizedRoom room = new PolarizedRoom();
		
		PolarizedDebaterDAO debaterDAO = new PolarizedDebaterDAO();
		PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
		UserDAO userDAO = new UserDAO();
		
		user = userDAO.getById(idUser);
		room = polarizedRoomDAO.getById(idRoom);
		
		debater.setRoom(room);
		debater.setUser(user);
		
		debaterDAO.insert(debater);
		
		ResponseBuilder builder;
		
		debater.setSide(null);
		
		builder = Response.status(Response.Status.OK).entity(debater);
		
		return builder.build();
	}
	
	@POST
	@Path("/leaveDebate")
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
	
	@POST
	@Path("/listDebates")
	@Consumes("application/json")
	@Produces("application/json")
	public Response listDebates(){
	
		PolarizedRoomDAO polarizedRoomDAO = new PolarizedRoomDAO();
		
		List<PolarizedRoom> polarizedRoom = polarizedRoomDAO.getAll();
		
		ResponseBuilder builder;
		
		builder = Response.status(Response.Status.OK).entity(polarizedRoom);
		
		return builder.build();
	}
}
