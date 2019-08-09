package com.carledwinti.springboot.jwt.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carledwinti.springboot.jwt.model.ApiResponse;
import com.carledwinti.springboot.jwt.model.User;
import com.carledwinti.springboot.jwt.service.UserService;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ApiResponse<User> save(@RequestBody User user){
		return new ApiResponse<>(HttpStatus.CREATED.value(), "User saved successfully.", userService.save(user));
	}
	
	@GetMapping
	public ApiResponse<List<User>> list(){
		return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.", userService.findAll());
	}
	
	@GetMapping("/{id}")
	public ApiResponse<User> getOne(@PathVariable Long id){
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", userService.findById(id));
	}
	
	@PutMapping
	public ApiResponse<User> update(@RequestBody User user){
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", userService.update(user));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id){
		userService.delete(id);
		return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "User fetched successfully.", null);
	}
	
}
