package org.pk;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pk.service.WBSEntity;
import org.pk.service.WBSService;
import org.pk.service.WbsConfiguration;

@Path("/hello")
public class ExampleResource {

	@Inject
	WBSService service;
	@Inject
	WbsConfiguration configuration; 
	
	@ConfigProperty(name = "wbs.name")
	String myname;
	
	@ConfigProperty(name = "wbs.version")
	Optional<String> version;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "hello";
	}
	
	@GET
	@Path("/getData")
	@Produces(MediaType.TEXT_PLAIN)
	public WBSEntity GetEntity() {
		return service.getEntity();
	}

	@GET
	@Path("/service")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVersion() {
		return service.getServiceName()+configuration.url;
	}

	@GET
	@Path("/service/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVersion1(@PathParam("data") String data) {
		String uuid = UUID.randomUUID().toString();
		return service.getServiceName() + uuid + data;
	}

	@GET
	@Path("/async")
	@Produces(MediaType.APPLICATION_JSON)
	public CompletionStage<String> getAsync() {
		return CompletableFuture.supplyAsync(() -> "Hello World!" + myname+version.orElse("No Version"));
	}

}