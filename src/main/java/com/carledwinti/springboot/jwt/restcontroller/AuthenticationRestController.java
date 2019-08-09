package com.carledwinti.springboot.jwt.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carledwinti.springboot.jwt.model.ApiResponse;
import com.carledwinti.springboot.jwt.model.AuthToken;
import com.carledwinti.springboot.jwt.model.LoginUser;
import com.carledwinti.springboot.jwt.model.User;
import com.carledwinti.springboot.jwt.service.UserService;
import com.carledwinti.springboot.jwt.util.JwtTokenUtil;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/token")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/generate-token")
	public ApiResponse<AuthToken> generate(@RequestBody LoginUser loginUser){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		
		final User user = userService.findByUsername(loginUser.getUsername());
		
		final String token = jwtTokenUtil.generateToken(user);
		
		return new ApiResponse<>(HttpStatus.OK.value(), "Token created.", new AuthToken(token, user.getUsername()));
	}
	
}
