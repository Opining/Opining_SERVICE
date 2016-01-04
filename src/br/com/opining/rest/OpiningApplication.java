package br.com.opining.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.opining.services.AcessService;
import br.com.opining.services.PolarizedDebateService;
import br.com.opining.services.UserService;

public class OpiningApplication extends Application{
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public OpiningApplication() {
		this.singletons.add(new UserService());
		this.singletons.add(new AcessService());
		this.singletons.add(new PolarizedDebateService());
	}

	public Set<Class<?>> setSingletons() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}	
}
