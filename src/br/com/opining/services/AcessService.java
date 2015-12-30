package br.com.opining.services;

import javax.ws.rs.core.Response;

import br.com.opining.library.model.AcessKey;
import br.com.opining.library.model.User;

public class AcessService {
	
	public Response login(User user){
		return null;
	}
	
	public Response logout(User user){
		return null;
	}
	
	private String generateKey(){
		return null;
	}
	
	private AcessKey checkIfHasKey(Integer idUser){
		return null;
	}
	
	private AcessKey createKey(User user){
		return null;
	}
	
	private void setLoginInformations(Integer idUser){
		
	}
	
	private void setLogoutInformations(Integer idUser){
		
	}
}
