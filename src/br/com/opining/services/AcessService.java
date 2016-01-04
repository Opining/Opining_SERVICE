package br.com.opining.services;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.opining.library.model.User;

@Path("acess")
public class AcessService {
	
	/**
	 * This method check if that user exists in the database and authorize
	 * his to use others services using the AcessKey
	 * 
	 * @param user
	 * @return a response that can contains a AcessKey or a Message
	 * 
	 * @author [Author Name]
	 */
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(User user){
		//TODO
		return null;
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
	
	private void setLoginInformations(Integer idUser){
		//TODO
	}
	
	private void setLogoutInformations(Integer idUser){
		//TODO
	}
}
