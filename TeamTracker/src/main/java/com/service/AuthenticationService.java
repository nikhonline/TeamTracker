package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dao.UserTransactionsDao;
import com.dto.CustomUser;

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	@Qualifier("userDao")
	UserTransactionsDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		CustomUser userInfo = userDao.getUserByUserName(username);
		UserDetails userDetails = (UserDetails)userInfo;
		return userDetails;
	}
	
}