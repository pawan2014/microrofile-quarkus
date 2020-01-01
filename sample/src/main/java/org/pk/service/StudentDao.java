package org.pk.service;

import java.sql.Date;
import java.util.List;

import javax.inject.Singleton;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@Singleton
public class StudentDao implements PanacheRepository<Student> {

    public List<Student> findByName(String lastName) {
        return find("lastName", lastName).list();
    }

    public List<Student> findBornAfter(Date date) {
        return find("birth > :date", Parameters.with("date", date)).list();
    }
}