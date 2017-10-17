Feature: Create a presentation

  Background:
    Given that I am logged into Admin site (have valid session cookie)

  Scenario: Submit a new presentation
    When I click “Add a Presentation”
    And fill in necessary information (date, time, headline, comment)
    And click “Save And Submit”
    Then that presentation is listed in the “Presentation Page”
    And the displayed status of that listing is “For Approval”

  Scenario: Publish a presentation
    Given that there exists a presentation with the status “For Approval”
    And I a, on the presentation List page
    When I select the presentation’s checkbox
    And I click “Publish”
    Then the presentation’s status become “Live” or “Live - Publish Pending”

  Scenario: Verify the presence of a recently published presentation on the public site
    Given that I have just published a new presentation
    When I navigate to the public site’s presentation page
    Then that presentation is displayed (allow for delay of up to 2 minutes for that presentation to appear)

  Scenario: Change and Submit a new presentation
    Given that I have a published (or pending for approval) presentation
    When I click the Edit item
    And change the information of the presentation
    And put in comments and click Save and Submit item
    When I go back to presentation list
    Then that presentation is listed in the “Presentation Page”
    And the displayed status of that listing is “For Approval”

  Scenario: Revert a presentation
    Given that I have just changed and submitted a presentation
    When  I click the Edit item
    And put in comments and click 'Revert to Live' in the edit page
    When  I go back to the presentation page
    Then the data is reverted to the previous live one and status shows "Live"

  Scenario: Delete a presentation
    Given that I have a published (or pending for approval) presentation
    When I click the Edit item
    And put in comments and click Delete item
    When I go back to the presentation page
    Then the displayed status of that presentation is "For approval"

  Scenario: Remove a presentation
    Given that I have a deleted pending presentation
    When I click the Edit item
    And put in comments and click Publish item
    When I go back to the page list
    Then the presentation is removed