package org.pk.endpoint;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pk.model.Project;
import org.pk.model.ProjectType;

import com.github.javafaker.Faker;

import io.quarkus.runtime.StartupEvent;

@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectEndpoint {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String version() {
		return "project API v1";
	}

	@GET
	@Path("byname/{color}")
	public List<Project> findByName(@PathParam(value = "color") ProjectType color) {
		System.out.println(color);
		// return Project.findByName(color);
		return Project.findByType(color);
	}

	@GET
	@Path("listall")

	public List<Project> findAll(@PathParam(value = "name") String name) {
		return Project.listAll();
	}

	@Transactional
	void onStart(@Observes StartupEvent ev) {
		for (int i = 0; i < 30; i++) {
			Faker faker = new Faker();
			
			LocalDate startDate = LocalDate.now().plusWeeks(Math.round(Math.floor(Math.random() * 20 * 52 * -1)));
			Project p = new Project();
			p.projectName=faker.name().fullName()+i;
			p.projectDesc=faker.book().author()+i+faker.book().title();
			p.projectType=ProjectType.JAVA;
			p.endDate=startDate;
			p.startDate=startDate;
			Project.persist(p);
		}
	}
	
	public String RandomString() {
	    byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	 
	   return generatedString;
	}
	
}
