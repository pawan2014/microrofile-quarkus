package org.pk.service;

import java.net.URI;
import java.sql.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

@Path("/v1")

public class PersonEndpointV2 {
    @Inject
    PersonDao personDao;

    @GET
    @Path("people")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> all() {
        return personDao.findAll();
    }

    @GET
    @Path("people/by-name")
    public List<Person> findByName(@PathParam(value ="name") String name) {
        return personDao.findByName(name);
    }

    @GET
    @Path("people/born-after")
    public List<Person> findBornAfter(@PathParam(value = "date") Date date) {
        return personDao.findBornAfter(date);
    }

    @GET
    @Path("person/{id}")
    public Person findById(@PathParam(value = "id") Long id) {
        Person p = personDao.findById(id);
        if(p == null)
            throw new WebApplicationException(Status.NOT_FOUND);
        return p;
    }

    @PUT
    @Path("person/{id}")
    public void updatePerson(@PathParam(value = "id") Long id, Person newPerson) {
        Person p = personDao.findById(id);
        if(p == null)
            throw new WebApplicationException(Status.NOT_FOUND);
        p.setBirth(newPerson.getBirth());
        p.setFirstName(newPerson.getFirstName());
        p.setLastName(newPerson.getLastName());
    }

    @DELETE
    @Path("person/{id}")
    public void deletePerson(@PathParam(value = "id") Long id) {
        Person p = personDao.findById(id);
        if(p == null)
            throw new WebApplicationException(Status.NOT_FOUND);
        personDao.delete(p);
    }

    @POST
    @Path("people")
    public Response newPerson(@Context UriInfo uriInfo, Person newPerson) {
        Person p = new Person();
        p.setBirth(newPerson.getBirth());
        p.setFirstName(newPerson.getFirstName());
        p.setLastName(newPerson.getLastName());
        personDao.persist(p);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(PersonEndpointV2.class)
                .path(PersonEndpointV2.class, "findById")
                .build(p.getId());
        return Response.created(uri).build();
    }
}