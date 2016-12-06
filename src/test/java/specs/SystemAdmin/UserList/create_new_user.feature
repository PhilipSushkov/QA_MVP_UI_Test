Feature: I can add, edit, and then delete a user

  Background: User List page is open
    Given that I am logged into Admin site / have valid session cookie
    When I click System Admin menu item
      And click User List item
    Then User List page opens
      And expected Title of the page is "User List"

  Scenario: I can add a new user
    Given that a user with username "quick-test" does not exist
    When I click on "Add New"
    Then a blank "User Edit" page is opened
    When I enter an email address into the "Email" field
      And I enter "quick-test" into the "User Name" field
      And I enter a password into the "Password" field
      And one or more roles are selected
      And I click on "Save"
    Then the user list page will reopen
      And an entry with user name "quick-test" will appear in the user list

  Scenario: I can edit an existing user
    Given that a user with the username "quick-test" exists and is set to be active
    When I click the edit icon next to the username "quick-test"
    Then the "User Edit" page for this user opens
    When I deselect the checkbox next to "Active"
      And I click on "Save"
    Then the user list page will reopen
      And the entry with user name "quick-test" will display "False" under the "Active" column

  Scenario: I can delete an existing user
    Given that a user with the username "quick-test" exists
    When I click the edit icon next to the username "quick-test"
    Then the "User Edit" page for this user opens
    When I click on "Delete"
    Then the user list page will reopen
      And an entry with user name "quick-test" will not appear in the user list