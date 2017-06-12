Feature: Check if Employee List can be opened, items can be added, and items can be edited

Background: I am logged into Admin site / have valid session cookie.

  Scenario: Find Employee List menu item and open the page
    Given: Dashboard page opened
    When I click Site Admin menu item
    And click Employee List item
    Then Employee List page opens
    And expected Title of the page is "Employee List"

  Scenario: Check that Employee List table exists
    Given: Employee List page is opened
    When: I find the Employee List table
    Then: I should find 4 columns (User Name, First Name, Last Name, Status)

  Scenario: Check that employee can be added
    Given: Employee List page is opened
    When: I find and click the "Add New" Button
    And I type in the email
    And I type in the password
    And I type in the first name
    And I type in the last name
    And I check the 'Active' check box
    And I press 'Save'
    And I go back to Employee List page
    Then: I should see a new employee with the same email, first name, and last name
    And Status should be active

  Scenario: Check that employee can be edited
    Given: Employee List Page is opened
    And There exists at least one employee already
    When: I click the 'Edit' button next to an employee entry
    And I change the first name
    And I change the last name
    And I change the email
    And I click Save
    And I go back to Employee List Page
    Then: I should see that the employee's email, first name, and last name has been changed

  Scenario: Check that employee can be deleted
    Given: Employee List Page is opened
    And There exists at least one employee already
    When: I click the 'Edit' button next to an employee entry
    And I click the "Delete" button
    Then: I should see that the employee's email, first name, and last name has been changed