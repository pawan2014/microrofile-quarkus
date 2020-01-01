package org.pk.service;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class PersonDao {

    @Inject
    EntityManager entityManager;

    public void persist(Person person) {
        entityManager.persist(person);
    }

    public void delete(Person person) {
        entityManager.remove(person);
    }

    public Person findById(Long id) {
        return entityManager.find(Person.class, id);
    }

    public List<Person> findAll() {
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    public List<Person> findByName(String lastName) {
        return entityManager.createQuery("FROM Person WHERE lastName = :lastName", Person.class).setParameter("lastName", lastName).getResultList();
    }

    public List<Person> findBornAfter(Date date) {
        return entityManager.createQuery("FROM Person WHERE birth > :date", Person.class).setParameter("date", date).getResultList();
    }
}