package com.dewpoint.rts.unit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dewpoint.rts.dao.UserDao;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.dto.UserResponseDTO;
import com.dewpoint.rts.errorconfig.ApiOperationException;
import com.dewpoint.rts.mapper.UserMapper;
import com.dewpoint.rts.model.User;
import com.dewpoint.rts.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao userDaoMock;
	@Mock
	private UserMapper userMapperMock;
	@InjectMocks
	private UserService userService;
	@Mock
	private Principal principalMock;
	@Mock
	private PasswordEncoder passwordEncoderMock;
	
	@Test
	public void searchUsersTest() {
		User user1 = new User();
		user1.setFullName("John Doe");
		user1.setUserId("johndoe");
		List<User> users = new ArrayList<User>();
		users.add(user1);
		
		when(userDaoMock.findByNamedQueryAndNamedParams(anyString(), anyMap())).thenReturn(users);
		when(userMapperMock.convertEntityToDTO(any(User.class))).thenCallRealMethod();
		
		UserResponseDTO userResponseDTO = userService.searchUsers("johndoe");
		
		assertEquals(1, userResponseDTO.getUsers().size());
		assertEquals("John Doe", userResponseDTO.getUsers().get(0).getUserFullName());
	}
	
	@Test
	public void retrieveAllUsersTest() {
		User user1 = new User();
		user1.setFullName("John Doe");
		user1.setUserId("johndoe");
		User user2 = new User();
		user2.setFullName("Harvey Spector");
		user2.setUserId("harveyspector");
		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		
		when(userDaoMock.findAll()).thenReturn(users);
		when(userMapperMock.convertEntityToDTO(any(User.class))).thenCallRealMethod();
		
		UserResponseDTO userResponseDTO = userService.retrieveAllUsers();
		
		assertEquals(2, userResponseDTO.getUsers().size());
		assertEquals("John Doe", userResponseDTO.getUsers().get(0).getUserFullName());
		assertEquals("Harvey Spector", userResponseDTO.getUsers().get(1).getUserFullName());
	}
	
	@Test
	public void createUserTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		when(userMapperMock.formatCreateEntry(userRequestDTO, principalMock)).thenCallRealMethod();
		
		userService.createUser(userRequestDTO, principalMock);
		
		verify(userDaoMock, times(1)).create(any(User.class));
	}
	
	@Test(expected = ApiOperationException.class)
	public void createUser_ExistingUserExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		
		userService.createUser(userRequestDTO, principalMock);
	}
	
	@Test
	public void updateUserTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		when(userMapperMock.formatUpdateEntry(user, principalMock)).thenCallRealMethod();
		
		userService.updateUser(userRequestDTO, principalMock);
		
		verify(userDaoMock, times(1)).update(any(User.class));
	}
	
	@Test(expected = ApiOperationException.class)
	public void updateUser_noUserFoundExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		userService.updateUser(userRequestDTO, principalMock);
	}
	
	@Test
	public void resetUserPassword() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		user.setPassword("welcome");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		when(userMapperMock.formatUpdateEntry(user, principalMock)).thenCallRealMethod();
		
		userService.resetUserPassword(userRequestDTO, principalMock);
		
		verify(userDaoMock, times(1)).update(any(User.class));
	}
	
	@Test(expected = ApiOperationException.class)
	public void resetUserPassword_ExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		userService.resetUserPassword(userRequestDTO, principalMock);
	}
	
	@Test
	public void resetUserPassword_noUserFoundExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		try {
			userService.resetUserPassword(userRequestDTO, principalMock);
		} catch(ApiOperationException exception) {
			assertEquals("Invalid request to reset password for user with id " + userRequestDTO.getUserId(), exception.getMessage());
		}
	}
	
	@Test
	public void resetUserPassword_defaulPasswordAlreadySetForThisUserExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("Welcome123!");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		user.setPassword("Welcome123!");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		when(userMapperMock.formatUpdateEntry(user, principalMock)).thenCallRealMethod();
		
		try {
			userService.resetUserPassword(userRequestDTO, principalMock);
		} catch(ApiOperationException exception) {
			assertEquals("Unable to perform operation as user id " + userRequestDTO.getUserId() +" was reset with default password already.", exception.getMessage());
		}
	}
	
	@Test
	public void resetUserStatus() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		user.setPassword("welcome");
		user.setStatus("InActive");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		when(userMapperMock.formatUpdateEntry(user, principalMock)).thenCallRealMethod();
		
		userService.resetUserStatus(userRequestDTO, principalMock);
		
		verify(userDaoMock, times(1)).update(any(User.class));
	}
	
	@Test(expected = ApiOperationException.class)
	public void resetUserStatus_ExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		userService.resetUserStatus(userRequestDTO, principalMock);
	}
	
	@Test
	public void resetUserStatus_noUserFoundExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("welcome");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		try {
			userService.resetUserStatus(userRequestDTO, principalMock);
		} catch(ApiOperationException exception) {
			assertEquals("Invalid request to reset user status for id " + userRequestDTO.getUserId(), exception.getMessage());
		}
	}
	
	@Test
	public void resetUserStatus_defaulStatusAlreadySetForThisUserExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setPassword("Welcome123!");
		userRequestDTO.setRole("Administrator");
		userRequestDTO.setUserId("johndoe");
		User user = new User();
		user.setUserId("johndoe");
		user.setPassword("Welcome123!");
		user.setStatus("Active");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		when(userMapperMock.formatUpdateEntry(user, principalMock)).thenCallRealMethod();
		
		try {
			userService.resetUserStatus(userRequestDTO, principalMock);
		} catch(ApiOperationException exception) {
			assertEquals("Unable to perform operation as user id " + user.getUserId() + " is in active status already.", exception.getMessage());
		}
	}
	
	@Test
	public void deleteUserTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("Harvey Spector");
		userRequestDTO.setRole("User");
		userRequestDTO.setUserId("harverspector");
		User user = new User();
		user.setUserId("harverspector");
		user.setStatus("Active");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		when(userMapperMock.formatDeleteEntry(user, principalMock)).thenCallRealMethod();
		
		userService.deleteUser(userRequestDTO, principalMock);
		
		verify(userDaoMock, times(1)).update(any(User.class));
	}
	
	@Test(expected = ApiOperationException.class)
	public void deleteUser_ExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("Harvey Spector");
		userRequestDTO.setRole("User");
		userRequestDTO.setUserId("harverspector");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		userService.deleteUser(userRequestDTO, principalMock);
	}
	
	@Test
	public void deleteUser_noUserFoundExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("Harvey Spector");
		userRequestDTO.setRole("User");
		userRequestDTO.setUserId("harverspector");
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(null);
		
		try {
			userService.deleteUser(userRequestDTO, principalMock);
		} catch(ApiOperationException exception) {
			assertEquals("Invalid request to delete user with id " + userRequestDTO.getUserId(), exception.getMessage());
		}
	}
	
	@Test
	public void deleteUser_userAlreadyInActiveExceptionTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("Harvey Spector");
		userRequestDTO.setRole("User");
		userRequestDTO.setUserId("harverspector");
		User user = new User();
		user.setUserId("harveyspector");
		user.setStatus("InActive");
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userMapperMock.formatSearchEntry(userRequestDTO)).thenCallRealMethod();
		when(userDaoMock.findByEntity(any(User.class))).thenReturn(users);
		
		try {
			userService.deleteUser(userRequestDTO, principalMock);
		} catch(ApiOperationException exception) {
			assertEquals("Unable to perform operation as user id " + user.getUserId() + " is in inactive status already.", exception.getMessage());
		}
	}
}
