Feature: Test email alert features on the public site
  Background: Email Alert page is open
    Given I go to the public page
    And I click on "Email Alerts" from the side nav
    Then Email Alerts page is displayed

  Scenario: Check for email
    Given I do not input an email address
    When Submit button is pressed
    Then Email Alert should not submit
    And Warning for email address being required should be displayed

  Scenario: Check for correct email formatting
    Given I write down incorrect email address
    And Email address does not have correct formatting "*@*.*"
    When Submit button is pressed
    Then Email Alert should not submit
    And Warning for email address being invalid should be displayed

  Scenario: Check for mailing list checkbox
    Given I do not have any of the mailing lists checked
    And I have inputted valid email address
    When Submit button is pressed
    Then Email Alert should not submit
    And Warning for mailing list selection being required should be displayed

  Scenario: Check for successful email list sign up
    Given I input valid email address
    And At least one mailing list checkbox is checked
    When Submit button is pressed
    Then Email Alert should submit
    And Message for successful sign up should be displayed

  Scenario: Check for invalid email unsubscription
    Given I input a random email address
    When Unsubscribe button is pressed
    Then Warning for unsubscription failing should be displayed

  Scenario: Check for email unsubscription
    Given I input a valid email address
    When Unsubscribe button is pressed
    Then Message for successful unsubscription should be displayed