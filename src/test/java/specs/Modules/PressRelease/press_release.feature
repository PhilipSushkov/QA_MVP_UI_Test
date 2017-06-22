Feature: I can change the parameters of the Press Release module

  Background: The Press Release module exists on a page on the live site and the module edit page is open

  Scenario: I can disable seperate date feature
    Given that I am on the Module Edit page
    When I click on Properties
    And I set SeperateDate to 'False'
    And I save, submit, and publish
    Then the dates for press releases should not be in the table

  Scenario: I can hide years that are displayed
    Given that I am on the Module Edit page
    When I click on Properties
    And I set HideYears to 'True'
    And I save, submit, and publish
    Then the years that are normally displayed above Press Release table should not be there

  Scenario: I can enable Show More feature
    Given that I am on the Module Edit page
    When I click on Properties
    And I set ShowMore to 'True;
    And I save, submit, and publish
    Then press release titles should have "More" at the end