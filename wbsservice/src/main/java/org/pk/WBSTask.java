package org.pk;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class WBSTask extends PanacheEntity {
	
	public String taskName;
	public String taskDesc;
	@JsonbDateFormat("dd.MM.yyyy")
	public LocalDate startDate;
	public LocalDate endDate;
	public String developers;
	public String taskStatus;
	
	
	@OneToMany(mappedBy="parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<WBSTask> children = new ArrayList<WBSTask>();

	@ManyToOne
	@JoinColumn(name="parent_id")
	WBSTask parent;
	
	public static WBSTask findByName(String name) {
		return find("taskName", name).firstResult();
	}

	public static WBSTask findByparentID(Long name){
        return find("parent_id=?1", name).firstResult();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
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

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public List<WBSTask> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "WBSTask [children=" + children + ", taskDesc=" + taskDesc + ", taskName=" + taskName + ", taskStatus="
				+ taskStatus + "]";
	}

	// Need to mark this as transient as enabling leds to the nested cyclic JSON error 
 	@JsonbTransient
	public WBSTask getParentWBS() {
		return parent;
	}




	// public Set<WBSTask> getChildren() {
	// 	return children;
	// }

	// public void setChildren(Set<WBSTask> children) {
	// 	this.children = children;
	// }

	// public WBSTask getParent() {
	// 	return parent;
	// }

	// public void setParent(WBSTask parent) {
	// 	this.parent = parent;
	// }

	
	
	
}
