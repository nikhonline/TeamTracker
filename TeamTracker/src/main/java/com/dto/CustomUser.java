package com.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails, CredentialsContainer {
	
	private static final long serialVersionUID = 1L;
	private String password;
	private String userId;
	private String name;
	List<SimpleGrantedAuthority> authorities;
	private boolean active;
	
	public void setRole(String role) {
		this.authorities = new ArrayList<SimpleGrantedAuthority>();
		this.authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void eraseCredentials() {
		password=null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof User) {
			return userId.equals(((User) rhs).getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}

}
