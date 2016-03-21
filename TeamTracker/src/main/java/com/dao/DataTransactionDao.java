package com.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.model.ManagerModel;
import com.model.TeamMemberModel;

public interface DataTransactionDao {

	List<TeamMemberModel> getAllTeamMembers(boolean fetchHistory, String mid, String uid, Date sdate,Date edate);
	Number createTeamMember(TeamMemberModel member);
	Boolean updateTeamMember(TeamMemberModel member);
	Boolean archiveTeamMember(String modifiedBy, String id);
	List<Map<String, Object>> getAllAppData();
	List<String> getAssociatedProjects(String managerId);
	List<String> getAllManagerIds();
	Object createAppData(Map<String, String> data);
	void createManager(ManagerModel manager);
	List<ManagerModel> getAllManagerMappings();
	void updateManager(ManagerModel manager);
	Boolean deleteProject(String pname);
	Boolean deleteManager(String mid);

}
