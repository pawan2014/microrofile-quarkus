package org.pk.service;

import java.sql.Date;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Student extends PanacheEntity {

    public String firstName;
    public String lastName;
    public Date birth;
}