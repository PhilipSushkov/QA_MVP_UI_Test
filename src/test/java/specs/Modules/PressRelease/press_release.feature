Feature: I can change the parameters of the Press Release module

  Background: The Press Release module exists on a page on the live site and the module edit page is open

  Scenario: I can add disclaimer text to the stock chart
    Given that I am on the Module Edit page
    When I click on Properties
    And I add text to the DisclaimerText field
    And I save, submit, and publish
    Then the disclaimer text appears on the stock chart on the live site