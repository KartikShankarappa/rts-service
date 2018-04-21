package com.dewpoint.rts.unit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.dewpoint.rts.dao.CandidateDao;
import com.dewpoint.rts.dto.CandidateRequestDTO;
import com.dewpoint.rts.dto.CandidateResponseDTO;
import com.dewpoint.rts.dto.CandidateSearchRequestDTO;
import com.dewpoint.rts.mapper.CandidateMapper;
import com.dewpoint.rts.model.Candidate;
import com.dewpoint.rts.service.CandidateService;

@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceTest {
	
	@Mock
    private CandidateDao candidateDaoMock;
	@Mock
    private CandidateMapper candidateMapperMock;
	@InjectMocks
	private CandidateService candidateService;
	
	@Test
	public void retieveAllCandidatesTest() {
		Candidate candidate1 = new Candidate();
		candidate1.setFirstName("John");
		candidate1.setLastName("Snow");
		candidate1.setStatus("Available");
		Candidate candidate2 = new Candidate();
		candidate2.setFirstName("Tyrian");
		candidate2.setLastName("Lannister");
		candidate2.setStatus("Available");
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(candidate1);
		candidates.add(candidate2);
		
		when(candidateDaoMock.findAll()).thenReturn(candidates);
		when(candidateMapperMock.convertEntityToDTO(any(Candidate.class))).thenCallRealMethod();
		
		CandidateResponseDTO response = candidateService.retrieveAllCandidates();
		
		assertEquals(2, response.getCandidates().size());
		assertEquals("John", response.getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getCandidates().get(0).getLastName());
		assertEquals("Available", response.getCandidates().get(0).getApplicationStatus());
		assertEquals("Tyrian", response.getCandidates().get(1).getFirstName());
		assertEquals("Lannister", response.getCandidates().get(1).getLastName());
		assertEquals("Available", response.getCandidates().get(1).getApplicationStatus());
	}
	
	@Test
	public void createCandidateTest() {
		CandidateRequestDTO candidateRequestDTO = new CandidateRequestDTO();
		candidateRequestDTO.setFirstName("John");
		candidateRequestDTO.setLastName("Snow");
		candidateRequestDTO.setApplicationStatus("Available");
		
		when(candidateMapperMock.formatCreateEntry(candidateRequestDTO, null)).thenCallRealMethod();
		
		candidateService.createCandidate(candidateRequestDTO);
		
		verify(candidateDaoMock, times(1)).create(any(Candidate.class));
	}
	
	@Test
	public void updateCandidateTest() {
		CandidateRequestDTO candidateRequestDTO = new CandidateRequestDTO();
		candidateRequestDTO.setFirstName("John");
		candidateRequestDTO.setLastName("Snow");
		candidateRequestDTO.setApplicationStatus("Available");
		Candidate candidate = new Candidate();
		candidate.setFirstName("John");
		candidate.setLastName("Snow");
		candidate.setStatus("Available");
		List<Candidate> candidates  = new ArrayList<Candidate>();
		candidates.add(candidate);
		
		when(candidateMapperMock.formatSearchEntry(candidateRequestDTO)).thenCallRealMethod();
		when(candidateDaoMock.findByEntity(any(Candidate.class))).thenReturn(candidates);
		when(candidateMapperMock.formatUpdateEntry(any(Candidate.class), any(CandidateRequestDTO.class), isNull(Principal.class))).thenCallRealMethod();
		
		candidateService.updateCandidate(candidateRequestDTO);
		
		verify(candidateDaoMock, times(1)).update(any(Candidate.class));
	}
	
	@Test
	public void searchCandidatesTest() {
		Candidate candidate1 = new Candidate();
		candidate1.setFirstName("John");
		candidate1.setLastName("Snow");
		candidate1.setStatus("Available");
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(candidate1);
		
		when(candidateDaoMock.findByNamedQueryAndNamedParams(any(String.class), anyMap())).thenReturn(candidates);
		when(candidateMapperMock.convertEntityToDTO(any(Candidate.class))).thenCallRealMethod();
		
		CandidateResponseDTO response = candidateService.searchCandidates("JOHN_SNOW");
		
		assertEquals(1, response.getCandidates().size());
		assertEquals("John", response.getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getCandidates().get(0).getLastName());
		assertEquals("Available", response.getCandidates().get(0).getApplicationStatus());
	}
	
	@Test
	public void searchCandidates_MultipleTest() {
		CandidateSearchRequestDTO candidateSearchRequestDTO = new CandidateSearchRequestDTO();
		candidateSearchRequestDTO.setStatus("Available");
		candidateSearchRequestDTO.setSource("External");
		candidateSearchRequestDTO.setSkills("Java");
		Candidate candidate1 = new Candidate();
		candidate1.setFirstName("John");
		candidate1.setLastName("Snow");
		candidate1.setStatus("Available");
		candidate1.setSource("External");
		candidate1.setSkills("Java");
		Candidate candidate2 = new Candidate();
		candidate2.setFirstName("Tyrian");
		candidate2.setLastName("Lannister");
		candidate2.setStatus("Available");
		candidate2.setSource("External");
		candidate2.setSkills("Java");
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(candidate1);
		candidates.add(candidate2);
		
		when(candidateDaoMock.findByEntity(any(Candidate.class))).thenReturn(candidates);
		when(candidateMapperMock.convertEntityToDTO(any(Candidate.class))).thenCallRealMethod();
		
		CandidateResponseDTO response = candidateService.searchCandidates(candidateSearchRequestDTO);
		
		assertEquals(2, response.getCandidates().size());
		assertEquals("John", response.getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getCandidates().get(0).getLastName());
		assertEquals("Available", response.getCandidates().get(0).getApplicationStatus());
		assertEquals("External", response.getCandidates().get(0).getSource());
		assertEquals("Java", response.getCandidates().get(0).getSkills());
		assertEquals("Tyrian", response.getCandidates().get(1).getFirstName());
		assertEquals("Lannister", response.getCandidates().get(1).getLastName());
		assertEquals("Available", response.getCandidates().get(1).getApplicationStatus());
		assertEquals("External", response.getCandidates().get(1).getSource());
		assertEquals("Java", response.getCandidates().get(1).getSkills());
	}
}
