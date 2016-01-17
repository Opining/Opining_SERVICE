package br.com.opining.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.opining.database.PolarizedDebaterDAO;
import br.com.opining.database.PolarizedRoomDAO;
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
	public Response enterDebate(User user, @QueryParam("idRoom") Integer idRoom){
		
		PolarizedDebater debater = new PolarizedDebater();
		PolarizedDebaterDAO debaterDAO = new PolarizedDebaterDAO();
		
		debater = debaterDAO.getById(idRoom);
		debater.setUser(user);
		
		ResponseBuilder builder;
		
		builder = Response.status(Response.Status.OK).entity(debater);
		
		return builder.build();
	}
	
	@POST
	@Path("/leaveDebate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response leaveDebate(PolarizedDebater debater){
		return null;
	}
}
