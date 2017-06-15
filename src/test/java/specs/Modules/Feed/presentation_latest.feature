Feature: I can change the parameters of the PresentationLatest module

  Background: The PresentationLatest module exists on a page on the live site and the module edit page is open

  Scenario: I can change the number of presentations to display in the module
    Given that I am on the Module Edit page
    When I click Properties
      And I add an integer value to the NumOfPresentations field
      And I save, submit, and publish
    Then the number of presentations displayed in the module on the live site changes accordingly

  Scenario: I can show the bodies of presentations in the module
    Given that I am on the Module Edit page
    When I click Properties
      And I set ShowBody to True
      And I save, submit, and publish
    Then the bodies of presentations in the module are displayed

  Scenario: I can show the details pages of presentations in the module
    Given that I am on the Module Edit page
    When I click Properties
      And I set ShowDetailsPage to True
      And I save, submit, and publish
    Then the details pages of presentations in the module are displayed
