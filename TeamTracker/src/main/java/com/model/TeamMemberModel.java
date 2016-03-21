package com.model;

import java.sql.Date;

import org.apache.poi.ss.usermodel.Row;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author M1031956
 *
 */
public class TeamMemberModel {
	private Integer id;
	private String mid;
	private String name;
	private String comments;
	private String workingStatus;
	private Boolean billable;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	private String matrixManager;
	private String groupHead;
	private String techCabinetLead;
	private String AOLArea;
	private String costCenter;
	private String team;
	private String projectRole;
	private String projectId;
	private String mindtreeManager;
	private String projectModel;
	private String pplSftId;
	private String modifiedBy;
	private Integer monthsOfExp;
	private Boolean active;

	public TeamMemberModel(Row row) {

	}

	public TeamMemberModel() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public Boolean getBillable() {
		return billable;
	}

	public void setBillable(Boolean billable) {
		this.billable = billable;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMatrixManager() {
		return matrixManager;
	}

	public void setMatrixManager(String matrixManager) {
		this.matrixManager = matrixManager;
	}

	public String getGroupHead() {
		return groupHead;
	}

	public void setGroupHead(String groupHead) {
		this.groupHead = groupHead;
	}

	public String getTechCabinetLead() {
		return techCabinetLead;
	}

	public void setTechCabinetLead(String techCabinetLead) {
		this.techCabinetLead = techCabinetLead;
	}

	@JsonProperty("AOLArea")
	public String getAOLArea() {
		return AOLArea;
	}

	@JsonProperty("AOLArea")
	public void setAOLArea(String aOLArea) {
		AOLArea = aOLArea;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMindtreeManager() {
		return mindtreeManager;
	}

	public void setMindtreeManager(String mindtreeManager) {
		this.mindtreeManager = mindtreeManager;
	}

	public String getPplSftId() {
		return pplSftId;
	}

	public void setPplSftId(String pplSftId) {
		this.pplSftId = pplSftId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getMonthsOfExp() {
		return monthsOfExp;
	}

	public void setMonthsOfExp(Integer monthsOfExp) {
		this.monthsOfExp = monthsOfExp;
	}

	public String getProjectModel() {
		return projectModel;
	}

	public void setProjectModel(String projectModel) {
		this.projectModel = projectModel;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
