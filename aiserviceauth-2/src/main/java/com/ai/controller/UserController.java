package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.config.JwtUtil;
import com.ai.dto.UsersDto;
import com.ai.entity.Users;
import com.ai.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/save")
	public Users saveUser(@RequestBody UsersDto usersDto)throws Exception{
		Users  users = userService.saveUser(usersDto);
		return users;		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UsersDto usersDto, HttpServletResponse response) throws Exception {
	  
		String token = userService.authenticateUserAndGenerateToken(usersDto);	   
		response.setHeader("Authorization", "Bearer " + token);    		
	    HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
      
	    String headerToken = response.getHeader("Authorization");
	    System.out.println("Token in Response Header: " + headerToken);	    
	    UsersDto userData = userService.getUserDetails(usersDto.getUsername());
	    return new ResponseEntity<>(userData, headers,HttpStatus.OK);
	}

	
	
	@GetMapping("/msg")
	public String getMsg() {
		return "Check Token";
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
	    // Retrieve the token from the Authorization header
	    String token = request.getHeader("Authorization");

	    if (token != null && token.startsWith("Bearer ")) {
	        // Extract the actual token (remove the "Bearer " prefix)
	        String jwtToken = token.substring(7);
	        response.setHeader("Authorization", null);

	        // Send a success response
	        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Authorization token is missing or invalid", HttpStatus.BAD_REQUEST);
	    }
	}



	
	
}