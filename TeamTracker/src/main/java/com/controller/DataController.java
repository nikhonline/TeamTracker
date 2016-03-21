package com.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.CustomUser;
import com.model.FileUploadForm;
import com.model.ManagerModel;
import com.model.TeamMemberModel;
import com.model.User;
import com.service.ControllerService;

@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	ControllerService service;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String save(@ModelAttribute("uploadForm") FileUploadForm uploadForm,
			Model map) {
//		application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		MultipartFile multipartFile = uploadForm.getFile();

		String fileName = "";

		if (multipartFile != null) {
			service.importFile(multipartFile);

		}

		map.addAttribute("files", fileName);
		return "file_upload_success";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<User> getUsers() {
		return service.getAllUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Boolean createUser(@RequestBody User customer) {
		return service.createUser(customer);
	}

	@RequestMapping(value = "/users", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	Boolean updateUser(@RequestBody User customer) {
		return service.updateUser(customer);
	}

	@RequestMapping(value = "/users/{pwd}", method = RequestMethod.PUT)
	Boolean updateUserPwd(Principal user, @PathVariable("pwd") String pwd) {
		return service.changeUserPwd(user.getName(), pwd);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	Boolean deleteUser(@PathVariable("id") String username) {
		return service.deleteUser(username);
	}

	/**
	 * Team members details
	 * 
	 * @param fetchHistory
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<TeamMemberModel> getAllTeamMembers(
			@RequestParam(value = "history", defaultValue = "false") boolean fetchHistory,
			@RequestParam(value = "mid", required = false) String mid,
			@RequestParam(value = "uid", required = false) String uid,
			@RequestParam(value = "sdate", required = false) Date sdate,
			@RequestParam(value = "edate", required = false) Date edate,
			Principal user, HttpServletRequest req) {
		if("All".equals(mid)){
			mid=null;
		}
		if (req.isUserInRole("ROLE_MANAGER") && mid ==null) {
			com.dto.CustomUser usr = (CustomUser) ((UsernamePasswordAuthenticationToken) user)
					.getPrincipal();
			mid = usr.getName();
		}
		return service.getAllTeamMembers(fetchHistory, mid,uid,sdate,edate);
	}

	@RequestMapping(value = "/members", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Number createTeamMember(@RequestBody TeamMemberModel member,Principal user) {
		member.setModifiedBy(user.getName());
		return service.createTeamMember(member);
	}

	@RequestMapping(value = "/members", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	Boolean updateTeamMember(@RequestBody TeamMemberModel member,Principal user) {
		member.setModifiedBy(user.getName());
		return service.updateTeamMember(member);
	}
	
	@RequestMapping(value = "/members/{id}", method = RequestMethod.DELETE)
	Boolean archiveTeamMember(Principal user, @PathVariable("id") String id) {
		return service.archiveTeamMember(user.getName(), id);
	}

	@RequestMapping(value = "/appdata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<Map<String, Object>> getAllAppData() {
		return service.getAllAppData();
	}

	@RequestMapping(value = "/appdata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	void createAppData(@RequestBody Map<String, String> data) {
		service.createAppData(data);
	}

	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<String> getAssociatedProjects(HttpServletRequest req,
			Principal user,
			@RequestParam(value = "all", defaultValue = "false") boolean fetchAllProjects) {
		String mid = null;
//		if (req.isUserInRole("ROLE_ADMIN")) {
			fetchAllProjects=true;
//		}
		if (!fetchAllProjects) {
			mid = user.getName();
		}
		return service.getAssociatedProjects(mid);
	}

	@RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
	Boolean deleteProject(@PathVariable("id") String pname) {
		return service.deleteProject(pname);
	}

	@RequestMapping(value = "/managers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<String> getAllManagerIds() {
		return service.getAllManagerIds();
	}

	@RequestMapping(value = "/managermapping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<ManagerModel> getAllManagerMappings() {
		return service.getAllManagerMappings();
	}

	@RequestMapping(value = "/managers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	void createManager(@RequestBody ManagerModel manager) {
		service.createManager(manager);
	}

	@RequestMapping(value = "/managers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	void updateManager(@RequestBody ManagerModel manager) {
		service.updateManager(manager);
	}

	@RequestMapping(value = "/managers/{id}", method = RequestMethod.DELETE)
	Boolean deleteManager(@PathVariable("id") String mid) {
		return service.deleteManager(mid);
	}

}
