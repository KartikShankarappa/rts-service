package com.dewpoint.rts.controller;

import com.dewpoint.rts.dto.*;
import com.dewpoint.rts.service.CandidateSearchService;
import com.dewpoint.rts.service.CandidateService;
import com.dewpoint.rts.service.UserSecurityService;
import com.dewpoint.rts.service.UserService;
import com.dewpoint.rts.util.ApiConstants;
import com.dewpoint.rts.util.ApiValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Api(value="RTS", description="Operations pertaining to Resume Tracking System (RTS).")
@RestController
@RequestMapping("/v1/")
//@PreAuthorize("hasAnyAuthority('" + ApiConstants.ROLE_ADMINISTRATOR + "','" + ApiConstants.ROLE_USER + "')")
public class RtsRestApiController {

	public static final Logger logger = Logger.getLogger("RestApiController");

	@Autowired
	private CandidateSearchService candidateSearchService;

	@Autowired
	private UserService userService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private UserSecurityService userSecurityService;

	@ApiOperation(value = "Returns encrypted password to UI")
	@RequestMapping(value = "/encrypted", method = RequestMethod.GET)
	public String encPassword(@RequestParam("userId") String userId) {
		return userSecurityService.userPassword(userId);
	}

	@ApiOperation(value = "Returns logged in user info to UI")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public Principal user(@AuthenticationPrincipal Principal user) {
		return user;
	}

	@ApiOperation(value = "Returns all users (Active and InActive)")
//	@Secured(ApiConstants.ROLE_ADMINISTRATOR)
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> retrieveAllUsers() {
		UserResponseDTO response = userService.retrieveAllUsers();
		ApiValidation.validateRetriveAllUsersResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Return all users based on userid based search")
//	@Secured(ApiConstants.ROLE_ADMINISTRATOR)
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> searchUser(@PathVariable("userId") String userId) {
		ApiValidation.validateSearchUserRequest(userId);
		UserResponseDTO response = userService.searchUsers(userId);
		ApiValidation.validateSearchUserResponse(response);
		return new ResponseEntity<>(response.getUsers(), HttpStatus.OK);
	}


	@ApiOperation(value = "Creates a new user")
//	@Secured(ApiConstants.ROLE_ADMINISTRATOR)
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserRequestDTO requestDTO, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateCreateUserRequest(requestDTO);
		userService.createUser(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Reset user password")
//	@Secured(ApiConstants.ROLE_ADMINISTRATOR)
	@RequestMapping(value = "/users/{userId}/reset", method = RequestMethod.PUT)
	public ResponseEntity<String> resetUserPassword(@PathVariable("userId") String userId, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateSearchUserRequest(userId);
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUserId(userId);
		userService.resetUserPassword(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Reset user status (make inactive profile to active profile again)")
//	@Secured(ApiConstants.ROLE_ADMINISTRATOR)
	@RequestMapping(value = "/users/{userId}/activate", method = RequestMethod.PUT)
	public ResponseEntity<String> resetUserStatus(@PathVariable("userId") String userId, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateSearchUserRequest(userId);
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUserId(userId);
		userService.resetUserStatus(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Change default user password")
//	@Secured({ApiConstants.ROLE_ADMINISTRATOR, ApiConstants.ROLE_USER})
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<String> changeUserPassword(@RequestBody UserRequestDTO requestDTO, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateUpdateUserRequest(requestDTO);
		userService.updateUser(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Inactivate user profile")
//	@Secured(ApiConstants.ROLE_ADMINISTRATOR)
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateSearchUserRequest(userId);
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUserId(userId);
		userService.deleteUser(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves candidate recent resume")
	@RequestMapping(value = "/candidates/resume", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> retrieveResume(@RequestParam(value = "candidateId")  String candidateId) throws Exception {
		ApiValidation.validateSearchCandidateRequest(candidateId);
		File resumeFile  = candidateSearchService.identifyFile(candidateId);
		String mediaType = candidateSearchService.determineMediaTypeFromFile(resumeFile);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType(mediaType))
				.body(new InputStreamResource(new FileInputStream(resumeFile + ".pdf")));
	}

	@ApiOperation(value = "Retrieves all candidates (non indexed data)")
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public ResponseEntity<CandidateResponseDTO> retrieveAllCandidates() {
		CandidateResponseDTO response = candidateService.retrieveAllCandidates();
		ApiValidation.validateRetriveAllCandidatesResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves an existing candidate full details")
	@RequestMapping(value = "/candidates/{candidateId}", method = RequestMethod.GET)
	public ResponseEntity<CandidateResponseDTO> searchCandidate(@PathVariable("candidateId") String candidateId) {
		ApiValidation.validateSearchCandidateRequest(candidateId);
		CandidateResponseDTO response = candidateService.searchCandidates(candidateId);
		ApiValidation.validateSearchCandidateResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Creates a new candidate profile")
	@RequestMapping(value = "/candidates", method = RequestMethod.POST)
	public ResponseEntity<?> createCandidate(@RequestBody CandidateRequestDTO requestDTO, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateCreateCandidateRequest(requestDTO);
		candidateService.createCandidate(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Updates an existing candidate profile")
	@RequestMapping(value = "/candidates", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateRequestDTO requestDTO, @AuthenticationPrincipal Principal user) {
		ApiValidation.validateUpdateCandidateRequest(requestDTO);
		candidateService.updateCandidate(requestDTO, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves RTS candidates (from indexed data first and then from database)")
	@RequestMapping(value = "/candidates/search", method = RequestMethod.POST)
	public ResponseEntity<CandidateSearchResponseDTO> searchCandidates(@RequestBody CandidateSearchRequestDTO requestDTO) {
		ApiValidation.validateSearchCandidatesRequest(requestDTO);
		// first search index records
		CandidateSearchResponseDTO responseDTO = candidateSearchService.searchForCandidates(requestDTO);
		// if no index records found, search database (This works as Cache hit or Cache miss scenario). Mostly below call will never be used.
		if(responseDTO == null || responseDTO.getCandidates() == null || responseDTO.getCandidates().size() == 0) {
			CandidateResponseDTO dbResponse = candidateService.searchCandidates(requestDTO);
			responseDTO = candidateSearchService.searchForCandidates(dbResponse);
		}
		ApiValidation.validateSearchCandidatesResponse(responseDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}