package org.pk.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import io.agroal.api.AgroalDataSource;

@ApplicationScoped
public class WBSService {

	@Inject
	EntityManager entityManager;

	public WBSEntity getEntity() {

		WBSEntity entity = entityManager.find(WBSEntity.class, new Integer(1));
		if(entity ==null) {
			System.out.print(entity);
		}
		return entity;

	}

	@Inject
	AgroalDataSource defaultDataSource;

	public String getServiceName() {
		return "WBSService";
	}
}
