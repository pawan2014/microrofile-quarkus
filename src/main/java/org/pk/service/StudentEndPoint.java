package org.pk.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class StudentEndPoint {
	@Inject
	StudentDao dao;

	@GET
	@Path("student")
	
	public List<Student> all() {
		return dao.findAll().list();
	}
}
