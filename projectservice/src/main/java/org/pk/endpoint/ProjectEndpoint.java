package org.pk.endpoint;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.jboss.logging.Logger;
import org.pk.model.Project;
import org.pk.model.ProjectType;

import com.github.javafaker.Faker;

import io.quarkus.runtime.StartupEvent;

@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectEndpoint {
	private static final Logger LOGGER = Logger.getLogger(ProjectEndpoint.class);
	private AtomicLong counter = new AtomicLong(0);
	@Inject
	FrancophoneService srvc;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String version() {
		return "project API v1";
	}

	@Counted(unit = MetricUnits.NONE, name = "itemsCheckedOut", absolute = true, displayName = "Checkout items", description = "Metrics to show how many times checkoutItems method was called.", tags = {
			"checkout=items" })
	@GET
	@Path("byname/{color}")
	@Timeout(320)
	@Fallback(fallbackMethod = "findByNameFallback")
	public List<Project> findByName(@PathParam(value = "color") ProjectType color) {
		LOGGER.infof("Called findByName");
		System.out.println(color);
		final Long invocationNumber = counter.getAndIncrement();
		maybeFail(String.format("Project#findAll() invocation #%d failed", invocationNumber));
		// return Project.findByName(color);
		return Project.findByType(color);
		//return  Collections.singletonList(new Project());
	}

	
	public List<Project> findByNameFallback(@PathParam(value = "color") ProjectType color) {
		LOGGER.infof("Calling Fallback in case of the failure of findByName");
				return  Collections.singletonList(new Project());
	}
	
	@GET
	@Path("listall")
	@Retry(maxRetries = 2)
	
	public List<Project> findAll(@PathParam(value = "name") String name) {
		srvc.bonjour();
		//
		final Long invocationNumber = counter.getAndIncrement();
		maybeFail(String.format("Project#findAll() invocation #%d failed", invocationNumber));
		//
		LOGGER.infof("Project#findAll() invocation #%d returning successfully", invocationNumber);
		return Project.listAll();
	}

	private void maybeFail(String failureLogMessage) {
		if (new Random().nextBoolean()) {
			LOGGER.error(failureLogMessage);
			throw new RuntimeException("Resource failure.");
		}
	}

	// @Transactional
	void onStart(StartupEvent ev) {
		// void onStart(@Observes StartupEvent ev) {
		for (int i = 0; i < 30; i++) {
			Faker faker = new Faker();

			LocalDate startDate = LocalDate.now().plusWeeks(Math.round(Math.floor(Math.random() * 20 * 52 * -1)));
			Project p = new Project();
			p.projectName = faker.name().fullName() + i;
			p.projectDesc = faker.book().author() + i + faker.book().title();
			p.projectType = ProjectType.JAVA;
			p.endDate = startDate;
			p.startDate = startDate;
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
