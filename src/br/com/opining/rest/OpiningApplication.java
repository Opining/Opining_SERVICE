package br.com.opining.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import br.com.opining.services.AccessService;
import br.com.opining.services.PolarizedRoomService;
import br.com.opining.services.UserService;

public class OpiningApplication extends Application{
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public OpiningApplication() {
		
		CorsFilter filter = new CorsFilter();
		filter.getAllowedOrigins().add("*");
		
		this.singletons.add(filter);
		this.singletons.add(new UserService());
		this.singletons.add(new AccessService());
		this.singletons.add(new PolarizedRoomService());
	}

	public Set<Class<?>> setSingletons() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}	
}
