package com.carledwinti.springboot.jwt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carledwinti.springboot.jwt.model.User;
import com.carledwinti.springboot.jwt.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			
			throw new UsernameNotFoundException("Usuário o Senha inválidos!");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	public List<User> findAll(){
		
		List<User> list = new ArrayList<>();
		
		userRepository.findAll().iterator().forEachRemaining(list ::add);
		
		return list;
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findById(Long id) {
		//return userRepository.findById(id);
		//TODO implementar
		return null;
	}
	
	public User update(User user) {
		
		User userToUpdate = findById(user.getId());
		
		if(userToUpdate != null) {
			
			BeanUtils.copyProperties(user, userToUpdate, "password");
			userRepository.save(userToUpdate);
		}
		
		return user;
	}
	
	public User save(User user) {
		
		return userRepository.save(user);
	}
}
