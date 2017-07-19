Feature: I can change the parameters of the Person module

  Background: The Person module exists on a page on the live site and the module edit page is open

  Scenario: I can display a department
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a department to the Department field
      And I save, submit, and publish
    Then the Person module on the live site displays the department

  Scenario: I can hide/show links in a person's name/photo
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowLink and ShowPersonLink to False
      And I save, submit, and publish
    Then the names and photos in the Person module on the live site do not contain links
    When I click on Properties
      And I set ShowLink and ShowPersonLink to True
      And I save, submit, and publish
    Then the names and photos in the Person module on the live site contain links

  Scenario: I can modify the LinkTo value for the details page
    Given that I am on the Module Edit page
    When I click on Properties
      And I set LinkToDetailsPage to a valid non-default LinkTo value
      And I save, submit, and publish
    Then the name/photo links in the Person module link to the linked page