package br.com.opining.services;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.opining.library.model.room.polarized.PolarizedRoom;
import br.com.opining.library.model.room.polarized.participant.PolarizedDebater;

@Path("debate/polarized")
public class PolarizedDebateService {
	
	public Response createDebate(PolarizedRoom debate){
		return null;
	}
	
	public Response enterDebate(PolarizedDebater debater){
		return null;
	}
	
	public Response leaveDebate(PolarizedDebater debater){
		return null;
	}
}
