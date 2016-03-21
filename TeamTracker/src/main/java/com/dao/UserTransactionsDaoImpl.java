package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.dto.CustomUser;
import com.model.User;
import com.util.PropertyReader;

/**
 * Only for the Authentication details
 */
@Repository("userDao")
public class UserTransactionsDaoImpl implements UserTransactionsDao {
	
	private static final String USERNAME = "userId";
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public CustomUser getUserByUserName(String username) throws UsernameNotFoundException{
		CustomUser user = null;
		try {
			Map<String, String> input = new HashMap<String, String>();
			input.put(USERNAME, username.toLowerCase());
			user = getJdbcTemplate()
					.queryForObject(
							PropertyReader.getValue("SELECT_USER"),
							input,
							new BeanPropertyRowMapper<CustomUser>(
									CustomUser.class));
			
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			throw new UsernameNotFoundException(e.getLocalizedMessage(),e);
		}
		return user ;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = getJdbcTemplate()
		.query(PropertyReader.getValue("SELECT_ALL_USER"),
				new BeanPropertyRowMapper<User>(
						User.class));
		return users;
	}

	@Override
	public Boolean createUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				user);
		getJdbcTemplate().update(PropertyReader.getValue("INSERT_USER"),
				paramSource);
		return true;
	}

	@Override
	public Boolean updateUser(User user) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				user);
		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_USER"),
				paramSource);
		return true;
	}

	@Override
	public Boolean deleteUser(String username) {
		Map<String, String> input = new HashMap<String, String>();
		input.put(USERNAME, username.toLowerCase());
		getJdbcTemplate().update(PropertyReader.getValue("ARCHIVE_USER"),
				input);
		return true;
	}

	@Override
	public Boolean changeUserPwd(String username, String pwd) {
		Map<String, String> input = new HashMap<String, String>();
		input.put(USERNAME, username.toLowerCase());
		input.put("pwd", encoder.encode(pwd));
		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_USER_PWD"),
				input);
		return true;
	}

	

}
