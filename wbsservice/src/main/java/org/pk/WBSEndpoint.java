package org.pk;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.javafaker.Faker;

import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;

@Path("/wbs")
@Produces(MediaType.APPLICATION_JSON)
public class WBSEndpoint {
	private static final Logger LOGGER = Logger.getLogger(WBSEndpoint.class);
	private final AtomicLong counter = new AtomicLong(0);

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String version() {
		return "WBS API :v1";
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WBSTask> findAll() {
		LOGGER.infof("Get all the WBS Items");
		return WBSTask.listAll();
	}

	@GET
	@Path("byid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WBSTask findById(@PathParam(value = "id") final Long id) {
		LOGGER.infof("Find a WBS with a specific ID.");
		WBSTask t1= WBSTask.find("id=?1 and parent_id is null",id).firstResult();
		//LOGGER.info(t1.getChildren());
		return t1;
		//findByparentID(id); //WBSTask.findById(id);
	}

	@POST
	@Path("add")
	@Transactional
	public Response create(final WBSTask prj) {
		LOGGER.info(prj);
		final WBSTask entity = WBSTask.findByName(prj.taskName);
		if (entity != null) {
			throw new WebApplicationException("Project with this name" + prj.taskName + " already exist.", 404);
		}
		prj.id = null;
		prj.persist();
		return Response.ok(prj).status(201).build();
	}

	@POST
	@Path("addsubtask/{id}")
	@Transactional
	public Response create(@PathParam(value = "id") final Long id ,final List<WBSTask> prj) {
		LOGGER.info(prj);
		final WBSTask entity = WBSTask.findById(id);
		if (entity == null) {
			throw new WebApplicationException("Task with this ID " + id + "not found.", 404);
		}
	
		for (WBSTask wbsTask : prj) {
			wbsTask.parent=entity;
		}
		entity.getChildren().addAll(prj);
		entity.persist();
		return Response.ok("{ok}").status(201).build();
	}


	@PUT
	@Path("changestatus/{id}/{status}")
	@Transactional
	public WBSTask update(@PathParam(value = "id") final Long id, @PathParam(value = "status") String status) {
		LOGGER.infof("Calling  change status");
		WBSTask entity = WBSTask.findById(id);
		if (entity == null) {
			throw new WebApplicationException("Project with id of " + id + " does not exist.", 404);
		}

		entity.taskStatus = status;

		return entity;
	}

	//@Transactional
	 void onStart(final StartupEvent ev) {
	//void onStart(@Observes StartupEvent ev) {
		for (int i = 0; i < 3; i++) {
			final Faker faker = new Faker();

			final LocalDate startDate = LocalDate.now().plusWeeks(Math.round(Math.floor(Math.random() * 20 * 52 * -1)));
			final WBSTask p = new WBSTask();
			p.taskName = faker.name().fullName() + i;
			p.taskDesc = faker.book().author() + i + faker.book().title();
			p.developers = faker.artist().name();
			p.taskStatus = "NEW";
			p.endDate = startDate;
			p.startDate = startDate;

			HashSet<WBSTask> set = new HashSet<WBSTask>();
			for (int i2 = 0;i2 < 5; i2++) {
				WBSTask p1 = new WBSTask();
				p1.taskName =faker.name().fullName() + i2;
				p1.taskDesc=faker.job().keySkills();
				p1.taskStatus="NEW";
				p1.parent = p;
				
				set.add(p1);
			}

			p.children.addAll(set);
			WBSTask.persist(p);
		}
	}
}
