package com.dewpoint.rts.service;

import com.dewpoint.rts.dao.UserDao;
import com.dewpoint.rts.dto.UserDTO;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.dto.UserResponseDTO;
import com.dewpoint.rts.errorconfig.ApiOperationException;
import com.dewpoint.rts.mapper.UserMapper;
import com.dewpoint.rts.model.User;
import com.dewpoint.rts.util.ApiConstants;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

	private UserMapper userMapper;
	private UserDao userDao;
	private PasswordEncoder passwordEncoder;

	public UserService(UserDao userDao, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponseDTO searchUsers(String userId) {
		UserResponseDTO response = new UserResponseDTO();
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		List<User> users = this.userDao.findByNamedQueryAndNamedParams("User.findSpecific", params);

		if(!users.isEmpty()) {
			List<UserDTO> usersList = new ArrayList<>();
			for(User aUser: users) {
				usersList.add(userMapper.convertEntityToDTO(aUser));
			}
			response.setUsers(usersList);
		}

		return response;
	}

	public UserResponseDTO retrieveAllUsers() {
		UserResponseDTO response = new UserResponseDTO();
		List<User> users = this.userDao.findAll();
		if(!users.isEmpty()) {
			List<UserDTO> usersList = new ArrayList<>();
			for(User aUser: users) {
				usersList.add(userMapper.convertEntityToDTO(aUser));
			}
			response.setUsers(usersList);
		}

		return response;
	}

	public void createUser(UserRequestDTO userRequestDTO, Principal principal) {
		User searchUser = userMapper.formatSearchEntry(userRequestDTO);
		List<User> users = this.userDao.findByEntity(searchUser);
		if(users != null && !users.isEmpty() && users.size() > 0) {
			throw new ApiOperationException("Invalid request to create user profile as id " + userRequestDTO.getUserId() + "already exists in the system.");
		}

		User user = userMapper.formatCreateEntry(userRequestDTO, principal);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userDao.create(user);
	}

	public void updateUser(UserRequestDTO userRequestDTO, Principal principal) {
		User searchUser = userMapper.formatSearchEntry(userRequestDTO);
		List<User> users = this.userDao.findByEntity(searchUser);
		if(users == null || users.isEmpty() || users.size() == 0) {
			throw new ApiOperationException("Invalid request to reset password for user with id " + userRequestDTO.getUserId());
		}

		User user = userMapper.formatUpdateEntry(users.get(0), principal);
		// Below happens when user is changing initial default password
		if(userRequestDTO.getPassword() != null
				&& !userRequestDTO.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		}

		// Future needs to allow change to role, full name
//		if(userRequestDTO.getRole() != null) {
//			user.setRole(userRequestDTO.getRole());
//		}
//
//		if(userRequestDTO.getUserFullName() != null) {
//			user.setFullName(userRequestDTO.getUserFullName());
//		}

		this.userDao.update(user);
	}

	public void resetUserPassword(UserRequestDTO userRequestDTO, Principal principal) {
		User searchUser = userMapper.formatSearchEntry(userRequestDTO);
		List<User> users = this.userDao.findByEntity(searchUser);
		if(users == null || users.isEmpty() || users.size() == 0) {
			throw new ApiOperationException("Invalid request to reset password for user with id " + userRequestDTO.getUserId());
		}

		User user = userMapper.formatUpdateEntry(users.get(0), principal);
		if(passwordEncoder.matches(ApiConstants.DEFAULT_INITIAL_PASSWORD, user.getPassword())){
			throw new ApiOperationException("Unable to perform operation as user id " + userRequestDTO.getUserId() +" was reset with default password already.");
		}

		// Below happens when Admin is resetting to initial default password
		user.setPassword(passwordEncoder.encode(ApiConstants.DEFAULT_INITIAL_PASSWORD));
		this.userDao.update(user);
	}

	public void resetUserStatus(UserRequestDTO userRequestDTO, Principal principal) {
		User searchUser = userMapper.formatSearchEntry(userRequestDTO);
		List<User> users = this.userDao.findByEntity(searchUser);
		if(users == null || users.isEmpty() || users.size() == 0) {
			throw new ApiOperationException("Invalid request to reset user status for id " + userRequestDTO.getUserId());
		}

		User user = userMapper.formatUpdateEntry(users.get(0), principal);
		if(ApiConstants.USER_STATUS_ACTIVE.equalsIgnoreCase(user.getStatus())){
			throw new ApiOperationException("Unable to perform operation as user id " + user.getUserId() + " is in active status already.");
		}

		// Below happens when Admin is resetting to initial default password
		user.setStatus(ApiConstants.USER_STATUS_ACTIVE);
		this.userDao.update(user);
	}

	public void deleteUser(UserRequestDTO userRequestDTO, Principal principal) {
		User searchUser = userMapper.formatSearchEntry(userRequestDTO);
		List<User> users = this.userDao.findByEntity(searchUser);
		if(users == null || users.isEmpty() || users.size() == 0) {
			throw new ApiOperationException("Invalid request to delete user with id " + userRequestDTO.getUserId());
		}

		User user = users.get(0);
		if(ApiConstants.USER_STATUS_INACTIVE.equalsIgnoreCase(user.getStatus())){
			throw new ApiOperationException("Unable to perform operation as user id " + user.getUserId() + " is in inactive status already.");
		}

		User deleteUser = userMapper.formatDeleteEntry(user, principal);
		this.userDao.update(deleteUser);
	}
}
