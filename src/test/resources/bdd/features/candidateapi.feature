@RTSTests
@CandidateAPI
Feature: Candidate API Operations
	As a user, I want the ability to add, remove, update, retrieve candidate information in the RTS system

	Background:
		Given a client with an active primary User account

  Scenario: Get a list of all the candidates in the system
      Given there is at least one default candidate in the system
    When the client issues a GET request to the uri /candidates
    Then the client receives status code of 200
    And the client receives a list of all candidates

  Scenario: Create a new candidate profile in the system
    When the client issues a POST request to the uri /candidates with new candidate details
    Then the client receives status code of 200
    And the candidate profile is created in the system

  Scenario: Update an existing candidate profile in the system
  	Given a candidate in the system
    When the client issues a PUT request to the uri /candidates with updated candidate details
    Then the client receives status code of 200
    And the candidate profile is updated in the system
    
  Scenario: Get the details of a candidate in the system by candidate id
  	Given a candidate in the system
    When the client issues a GET request to the uri /candidates with the candidate id
    Then the client receives status code of 200
    And the candidate details are retrieved