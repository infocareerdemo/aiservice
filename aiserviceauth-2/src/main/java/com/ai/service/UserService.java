package com.ai.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ai.config.JwtUtil;
import com.ai.dto.UsersDto;
import com.ai.entity.Users;
import com.ai.exception.ApplicationException;
import com.ai.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	

	public Users saveUser(UsersDto usersDto) {
		
	    Users user = new Users();
	    user.setUsername(usersDto.getUsername());
	    user.setPassword(usersDto.getPassword());
	  
		userRepository.save(user);	
		return user;
	}
	
	
	  public String authenticateUserAndGenerateToken(UsersDto usersDto) throws Exception {
	        Users user = userRepository.findByUsername(usersDto.getUsername());
	        if (user != null) {
	            String pwd = user.getPassword();
	            if (pwd != null && pwd.equals(usersDto.getPassword())) {
	                return jwtUtil.createToken(user.getUsername());
	            } else {
	                throw new ApplicationException(HttpStatus.UNAUTHORIZED, 1001, LocalDateTime.now(), "Invalid credentials");
	            }
	        } else {
	            throw new ApplicationException(HttpStatus.UNAUTHORIZED, 1001, LocalDateTime.now(), "Invalid credentials");
	        }
	    }

	    public UsersDto getUserDetails(String username) {
	        Users user = userRepository.findByUsername(username);
	        UsersDto usersData = new UsersDto();
	        usersData.setUsername(user.getUsername());
	        return usersData;
	    }
	
}
