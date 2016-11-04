Feature: Log in to Admin CMS part of web-site

    We need to login to Admin CMS as Admin user and get SessionID before start testing functionality.

Background:
    Given I have chosen Environment
    And I have chosen test web-site
    And I have valid admin credentials

Scenario: Login to Admin site with valid login/password
    When I open Login page
    And enter my login and password
    And I submit the form
    Then Dashboard page should open
    And SessionID should be added to Cookie
# Implemented in EnterToAdmin.java