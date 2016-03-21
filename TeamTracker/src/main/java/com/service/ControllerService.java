package com.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.DataTransactionDao;
import com.dao.UserTransactionsDao;
import com.model.ManagerModel;
import com.model.TeamMemberModel;
import com.model.User;

@Service
public class ControllerService {

	@Autowired
	@Qualifier("dataDao")
	DataTransactionDao dataDao;
	@Autowired
	@Qualifier("userDao")
	UserTransactionsDao userDao;

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public Boolean createUser(User user) {
		return userDao.createUser(user);
	}

	public Boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	public Boolean deleteUser(String username) {
		return userDao.deleteUser(username);
	}

	public Boolean changeUserPwd(String username, String pwd) {
		return userDao.changeUserPwd(username, pwd);
	}

	public List<TeamMemberModel> getAllTeamMembers(boolean fetchHistory,String mid, String uid, Date sdate,Date edate) {
		return dataDao.getAllTeamMembers(fetchHistory,mid,uid,sdate,edate);
	}

	public Number createTeamMember(TeamMemberModel member) {
		return dataDao.createTeamMember(member);
	}

	public Boolean updateTeamMember(TeamMemberModel member) {
		return dataDao.updateTeamMember(member);
	}
	
	public Boolean archiveTeamMember(String modifiedBy,String id) {
		return dataDao.archiveTeamMember(modifiedBy,id);
	}
	

	public List<Map<String, Object>> getAllAppData() {
		return dataDao.getAllAppData();
	}

	public Object createAppData(Map<String, String> data) {
		return dataDao.createAppData(data);
	}

	public List<String> getAssociatedProjects(String managerId) {
		return dataDao.getAssociatedProjects(managerId);
	}

	public List<String> getAllManagerIds() {
		return dataDao.getAllManagerIds();
	}

	public void createManager(ManagerModel manager) {
		dataDao.createManager(manager);

	}
	
	public void updateManager(ManagerModel manager) {
		dataDao.updateManager(manager);

	}

	public List<ManagerModel> getAllManagerMappings() {
		return dataDao.getAllManagerMappings();
	}

	public Boolean deleteProject(String pname) {
		return dataDao.deleteProject(pname);
	}

	public Boolean deleteManager(String mid) {
		return dataDao.deleteManager(mid);
	}
	
	public void importFile(MultipartFile fileBean) {

        Workbook workbook;
        try {
        	ByteArrayInputStream bis = new ByteArrayInputStream(fileBean.getBytes());
            if (fileBean.getOriginalFilename().endsWith("xls")) {
                workbook = new HSSFWorkbook(bis);
            } else if (fileBean.getOriginalFilename().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(bis);
            } else {
                throw new IllegalArgumentException("Received file does not have a standard excel extension.");
            }
            List<TeamMemberModel> members = new ArrayList<>();
            for (Row row : workbook.getSheetAt(workbook.getActiveSheetIndex())) {
            	if(row.getRowNum()==0){
            		checkHeaders(row);
            		continue;
            	}
            	TeamMemberModel member = new TeamMemberModel(row);
            	members.add(member);
               
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private boolean checkHeaders(Row row) {
		for (Cell cell : row) {
    		cell.setCellType(Cell.CELL_TYPE_STRING);
    		System.out.println(cell.getStringCellValue());
		}
		if (!"Mindtree ID"
				.equalsIgnoreCase(row.getCell(0).getStringCellValue())) {
			return false;
		} else if (!"Name"
				.equalsIgnoreCase(row.getCell(1).getStringCellValue())) {
			return false;
		} else if (!"PeopleSoft ID".equalsIgnoreCase(row.getCell(2)
				.getStringCellValue())) {
			return false;
		} else if (!"Months Of Experience".equalsIgnoreCase(row.getCell(3)
				.getStringCellValue())) {
			return false;
		} else if (!"Project ID".equalsIgnoreCase(row.getCell(4)
				.getStringCellValue())) {
			return false;
		} else if (!"Project Model".equalsIgnoreCase(row.getCell(5)
				.getStringCellValue())) {
			return false;
		} else if (!"Matrix Manager".equalsIgnoreCase(row.getCell(6)
				.getStringCellValue())) {
			return false;
		} else if (!"Group Head".equalsIgnoreCase(row.getCell(7)
				.getStringCellValue())) {
			return false;
		} else if (!"Tech Cabinet Lead".equalsIgnoreCase(row.getCell(8)
				.getStringCellValue())) {
			return false;
		} else if (!"AOL Area".equalsIgnoreCase(row.getCell(9)
				.getStringCellValue())) {
			return false;
		} else if (!"Team".equalsIgnoreCase(row.getCell(10)
				.getStringCellValue())) {
			return false;
		} else if (!"Cost Center".equalsIgnoreCase(row.getCell(11)
				.getStringCellValue())) {
			return false;
		} else if (!"Project Role".equalsIgnoreCase(row.getCell(12)
				.getStringCellValue())) {
			return false;
		} else if (!"Billable".equalsIgnoreCase(row.getCell(13)
				.getStringCellValue())) {
			return false;
		} else if (!"MindTree Manager".equalsIgnoreCase(row.getCell(14)
				.getStringCellValue())) {
			return false;
		} else if (!"Start Date".equalsIgnoreCase(row.getCell(15)
				.getStringCellValue())) {
			return false;
		} else if (!"End Date".equalsIgnoreCase(row.getCell(16)
				.getStringCellValue())) {
			return false;
		} else if (!"Working Status".equalsIgnoreCase(row.getCell(17)
				.getStringCellValue())) {
			return false;
		} else if (!"Comments".equalsIgnoreCase(row.getCell(18)
				.getStringCellValue())) {
			return false;
		} else if (!"Active".equalsIgnoreCase(row.getCell(19)
				.getStringCellValue())) {
			return false;
		}

		
		return true;
		
	}

}
