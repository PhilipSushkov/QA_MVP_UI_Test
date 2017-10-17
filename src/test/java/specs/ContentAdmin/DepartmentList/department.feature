Feature: Check Department List page opens well

  Background: I am logged into Admin site / have valid session cookie

  Scenario: Find Department menu item and open the page
    Given: Dashboard page opened
    When I click Content Admin menu item
    And click Department List item
    Then Department List page opens
    And expected Title of the page is "Department List"

  Scenario: Save a new department
    Given: Department List page opened
    When I click Add New item
    And  Fill out the form to create a new Department
    When I click Save item
    Then the page is refreshed
    And  the status shows the department is in progress
    Then the status as "in progress"
    And  expect data of the new department matches that of the JSON file

  Scenario: revert the change of a department
    Given: Department List page opened
    When I click Edit on the existing department
    And  change the data inside
    Then click Save And Submit
    And  publish the new changes
    Then click revert to live
    And  expect the changes reverted
