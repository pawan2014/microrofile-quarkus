package org.pk.model;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Project extends PanacheEntity {
	public String projectName;
	public String projectDesc;
	public LocalDate startDate;
	public LocalDate endDate;
	public String projectManager;
	public String technicalArch;
	public String developers;

	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	public ProjectType projectType;

	public static Project findByName(String name) {
		return find("projectName", name).firstResult();
	}

	public static List<Project> findByType(ProjectType name) {
		return list("projectType", name);
	}

	public static List<Project> findByColor(ProjectType color) {
		return list("projectType", color);
	}

	public static List<Project> getBeforeYear(int year) {
		return Project.<Project>streamAll().filter(p -> p.startDate.getYear() <= year).collect(Collectors.toList());
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getTechnicalArch() {
		return technicalArch;
	}

	public void setTechnicalArch(String technicalArch) {
		this.technicalArch = technicalArch;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
}
