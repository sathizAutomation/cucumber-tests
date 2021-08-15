@login-feature
Feature: Tests to validate login screen

  @test @login
  Scenario Outline: To verify that the user can verify the login feature of the application
    Given The user navigates to authentication page
    When he enters "<EmailId>" and "<Password>" for the application
    And logins to the application by pressing the login button
    Then verifies that he is in the home page

    Examples:
      | EmailId       | Password |
      | demo@test.com | Test123  |