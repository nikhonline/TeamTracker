package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.model.ManagerModel;
import com.model.TeamMemberModel;
import com.util.PropertyReader;

@Repository("dataDao")
public class DataTransactionDaoImpl implements DataTransactionDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public List<TeamMemberModel> getAllTeamMembers(boolean fetchHistory, String mid, String uid,Date sdate,Date edate) {
		String query = PropertyReader.getValue("SELECT_TEAM_MEMBER");
		Map<String, Object> data = new HashMap<>(1);
		data.put("managerId", mid);
		data.put("userId", uid);
		if (!fetchHistory) {
			data.put("startDate", sdate);
			//condition to filter the team members with end date
			query += " " + PropertyReader.getValue("SELECT_TEAM_MEMBER_WHERE");
			
		}else{
			data.put("startDate", sdate);
			data.put("endDate", edate);
			//condition to filter the team members with end date
			String condition = PropertyReader.getValue("SELECT_TEAM_MEMBER_HISTORY_WHERE");
			
			if(edate !=null){
				
				condition= "("+condition+ " OR (start_date between :startDate  and :endDate )"+")";
			}
			query += " WHERE " + condition;
		}
		if (uid != null) {
			query += " AND MID = :userId";
		}else if (mid != null) {
			query += " AND MINDTREE_MANAGER = :managerId";
		}
		
		query += " ORDER BY NAME";

		List<TeamMemberModel> members = getJdbcTemplate().query(
				query,data,
				new BeanPropertyRowMapper<TeamMemberModel>(
						TeamMemberModel.class));
		return members;
	}

	@Override
	public Number createTeamMember(TeamMemberModel member) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				member);
		String mid = member.getMid();
		if(mid !=null && mid.trim().length() >0){
			Number checkUsr = getJdbcTemplate().queryForObject(PropertyReader.getValue("CHECK_TEAM_MEMBER_EXISTANCE"), paramSource, Number.class);
			if(checkUsr.longValue()!=0){
				return (long) -1;
			}
		}
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(PropertyReader.getValue("INSERT_TEAM_MEMBER"),	paramSource,keyHolder, new String[] { "ID" });
		Number key =  keyHolder.getKey();
		return key;
	}

	@Override
	public Boolean updateTeamMember(TeamMemberModel member) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				member);
		String mid = member.getMid();
		if(mid !=null && mid.trim().length() >0){
		Number checkUsr = getJdbcTemplate().queryForObject(PropertyReader.getValue("CHECK_TEAM_MEMBER_EXISTANCE_UPDATE"), paramSource, Number.class);
		if(checkUsr.longValue()!=0){
			return false;
		}}
		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_TEAM_MEMBER"),
				paramSource);
		return true;
	}

	@Override
	public Boolean archiveTeamMember(String modifiedBy, String id){
		Map<String, Object> data = new HashMap<>(1);
		data.put("id", id);
		data.put("modifiedBy", modifiedBy);
		getJdbcTemplate().update(PropertyReader.getValue("ARCHIVE_TEAM_MEMBER"),
				data);
		return true;
	}
	
	
	@Override
	public List<Map<String, Object>> getAllAppData() {
		Map<String, String> input = new HashMap<>(1);
		List<Map<String, Object>> data = getJdbcTemplate().queryForList(
				PropertyReader.getValue("SELECT_APP_DATA"), input);
		return data;
	}

	@Override
	public Object createAppData(Map<String, String> data) {
		if (!data.containsKey("PN")) {
			// insert project role
			getJdbcTemplate().update(
					PropertyReader.getValue("INSERT_APP_DATA"), data);
		} else {
			// insert project name
			getJdbcTemplate().update(PropertyReader.getValue("INSERT_PROJECT"),
					data);
		}

		return null;
	}

	@Override
	public List<String> getAssociatedProjects(String managerId) {
		List<String> projects;
		Map<String, String> input = new HashMap<>(1);
		String query;
		if (managerId != null) {
			input.put("managerId", managerId);
			query = PropertyReader.getValue("SELECT_ASSOCIATED_PROJECTS");
		} else {
			query = PropertyReader.getValue("SELECT_ALL_PROJECTS");
		}
		projects = getJdbcTemplate().queryForList(query,input, String.class);

		return projects;
	}

	@Override
	public List<String> getAllManagerIds() {
		Map<String, String> input = new HashMap<>(1);
		List<String> managers = getJdbcTemplate()
				.queryForList(PropertyReader.getValue("SELECT_MANAGERS"),
						input, String.class);
		return managers;
	}

	@Override
	public void createManager(ManagerModel manager) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				manager);
		// insert manager
		getJdbcTemplate().update(PropertyReader.getValue("INSERT_MANAGER"),
				paramSource);
		insertProjectMapping(manager);

	}

	@Override
	public void updateManager(ManagerModel manager) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				manager);
		// update manager
		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_MANAGER"),
				paramSource);
		// delete old manager-project mapping b4 update
		getJdbcTemplate().update(
				PropertyReader.getValue("DELETE_MANAGER_MAPPING"), paramSource);
		insertProjectMapping(manager);

	}

	private void insertProjectMapping(ManagerModel manager) {
		// insert manager-project mapping
		String managerId = manager.getManagerId();
		List<String> projectIds = manager.getProjectIds();
		List<SqlParameterSource> parameters = new ArrayList<>();
		for (String projectId : projectIds) {
			Map<String, String> data = new HashMap<>(2);
			data.put("managerId", managerId);
			data.put("projectId", projectId);
			SqlParameterSource entry = new MapSqlParameterSource(data);
			parameters.add(entry);
		}

		getJdbcTemplate().batchUpdate(
				PropertyReader.getValue("INSERT_PROJECT_MAPPING"),
				parameters.toArray(new SqlParameterSource[0]));
	}

	@Override
	public List<ManagerModel> getAllManagerMappings() {
		List<ManagerModel> managers = getJdbcTemplate().query(
				PropertyReader.getValue("SELECT_MANAGERS_ALL_DATA"),
				new BeanPropertyRowMapper<ManagerModel>(ManagerModel.class));

		for (ManagerModel manager : managers) {
			manager.setProjectIds(getAssociatedProjects(manager.getManagerId()));
		}
		return managers;
	}

	@Override
	public Boolean deleteProject(String pname) {
		Map<String, String> input = new HashMap<String, String>();
		input.put("projectId", pname);
		getJdbcTemplate().update(PropertyReader.getValue("ARCHIVE_PROJECT"),
				input);
		return true;
	}

	@Override
	public Boolean deleteManager(String mid) {
		Map<String, String> input = new HashMap<String, String>();
		input.put("managerId", mid);
		getJdbcTemplate().update(PropertyReader.getValue("ARCHIVE_MANAGER"),
				input);
		return true;
	}

}
