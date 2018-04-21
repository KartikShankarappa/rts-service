@RTSTests
@UserAPI
Feature: User API Operations
	As an administrator, I want the ability to add, remove, update, retrieve user information in the RTS system

	Background
		Given a client with an active primary Administrator account

  Scenario: Get a list of all users in the system
    Given a user in the system
    When the client issues a get request to the uri /users
    Then the client receives status code of 200
    And the client receives list of all users in the system

  Scenario: Add a new user into the system
    When the client issues a POST request to the uri /users with user details
    Then the client receives status code of 200
    And the new user is created in the system

  Scenario: Get a user details from the system
      Given a user in the system
    When the client issues a GET request to the uri /users with user id
    Then the client receives status code of 200
    And the user details are retrieved from the system

    Scenario: Inactivate a user in the system
      Given an active user in the system
      When the client issues a DELETE request to the uri /users with user id
      Then the client receives status code of 200
      And the user is inactivated in the system

  Scenario: Reset a user password in the system
  	Given a user in the system
    When the client issues a PUT request to the uri /users/userId/reset with password reset user details
    Then the client receives status code of 200
    And the user password is reset to default password in the system

  Scenario: Change a user status to active in the system
    Given an Inactive user in the system
    When the client issues a PUT request to the uri /users/userId/activate with user id
    Then the client receives status code of 200
    And the user is activated in the system

  Scenario: Change a user's default password in the system
    Given a user in the system
    When the client issues a PUT request to the uri /users/ with new password and user details
    Then the client receives status code of 200
    And the user password is updated in the system
