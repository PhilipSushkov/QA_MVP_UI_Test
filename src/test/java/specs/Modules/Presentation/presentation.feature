Feature: I can change the parameters of the Presentation module

  Background: The Presentation module exists on a page on the live site and the module edit page is open

  Scenario: I can change the number of presentations to display in the module
    Given that I am on the Module Edit page
    When I click Properties
      And I add an integer value to the NumOfPresentations field
      And I save, submit, and publish
    Then the number of presentations displayed in the module on the live site changes accordingly

  Scenario: I can show all presentations
    Given that I am on the Module Edit page
    When I click Properties
      And I set ShowAll to True
      And I save, submit, and publish
    Then all presentations are displayed in the module

  Scenario: I can filter presentations with a tag
    Given that I am on the Module Edit page
    When I click Properties
      And I add a tag to the Tags field
      And I set the TagMode field to 'Override tags from page'
      And I save, submit, and publish
    Then only presentations with the tag are displayed in the Presentations module

  Scenario: I can inherit content filter tags from the host page
    Given that I am on the Page Edit page
    When I add a tag to the TagFilters field
      And I click on the Presentations module edit button
      And I click Properties
      And I set the TagMode Field to 'Inherit tags from page'
      And I save, submit, and publish all changes
    Then only presentations with the page tag are displayed in the Presentations module

  Scenario: I can promote content filter tags to the host page
    Given that I am on the Module Edit page
      And another content module exists on the host page
      And that module's TagMode is 'Inherit tags from page'
    When I click on Properties
      And I add a tag to the Tags field
      And I set PromoteToPage to True
      And I save, submit, and publish
    Then the other content module is filtered by the tag