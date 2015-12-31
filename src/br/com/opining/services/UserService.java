package br.com.opining.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.opining.library.model.User;

@Path("user")
public class UserService {
	
	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(User user){
		return null;
	}
	
	@POST
	@Path("/invalidate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response invalidateUser(User user){
		return null;
	}
	
	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateUser(User user){
		return null;
	}
	
	@GET
	@Path("/list/all")
	@Consumes("application/json")
	@Produces("application/json")
	public List<User> listUsers(){
		return null;
	}
}
