package com.sm.rsm.services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sm.rsm.model.Users;


public class UserInfoUserDetails  implements UserDetails{

	
	private static final long serialVersionUID = -2274614237642970835L;
	private Users user;
	
	
	public UserInfoUserDetails (Users user)
	{
		this.user=user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		if(user.getRole().equals("user"))
		{
			set.add(new GrantedAuthority() {

				@Override
				public String getAuthority() {
					return "ROLE_USER";
				}
			});
		}else if(user.getRole().equals("manager"))
		{
			set.add(new GrantedAuthority() {

				@Override
				public String getAuthority() {
					return "ROLE_MANAGER";
				}
			});
		}
		return set;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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
	
		return true;
	}

}
