Feature: Create a press release

  Background:
    Given that I am logged into Admin site (have valid session cookie)

  Scenario: Save a new press release
    When I click “Add a Press Release”
    And fill in necessary information (date, time, headline, comment)
    And click “Save”
    Then that press release is listed in the “Press Release Page”
    And the displayed status of that listing is “In Progress”

  Scenario: Submit a new press release
    When I click “Add a Press Release”
    And fill in necessary information (date, time, headline, comment)
    And click “Save And Submit”
    Then that press release is listed in the “Press Release Page”
    And the displayed status of that listing is “For Approval”

  Scenario: Publish a press release
    Given that there exists a press release with the status “For Approval”
    And I am, on the Press Release List page
    When I select the press release’s checkbox
    And I click “Publish”
    Then the press release’s status become “Live” or “Live - Publish Pending”

  Scenario: Verify the presence of a recently submitted press release on the preview site
    Given that I have just submitted a new press release
    When I navigate to the preview site’s press release page
    Then that press release is displayed

  Scenario: Verify the presence of a recently published press release on the public site
    Given that I have just published a new press release
    When I navigate to the public site’s press release page
    Then that press release is displayed

  Scenario: Change and Submit a new press release
    Given that I have a published (or pending for approval) press release
    When I click the Edit item
    And change the information of the press release
    And put in comments and click Save and Submit item
    When I go back to press release list
    Then that press release is listed in the “Press Release Page”
    And the displayed status of that listing is “For Approval”

  Scenario: Revert a press release
    Given that I have just changed and submitted a press release
    When  I click the Edit item
    And put in comments and click 'Revert to Live' in the edit page
    When  I go back to the press release page
    Then the data is reverted to the previous live one and status shows "Live"

  Scenario: Delete a press release
    Given that I have a published (or pending for approval) press release
    When I click the Edit item
    And put in comments and click Delete item
    When I go back to the press release page
    Then the displayed status of that press release is "For approval"

  Scenario: Remove a press release
    Given that I have a deleted pending press release
    When I click the Edit item
    And put in comments and click Publish item
    When I go back to the page list
    Then the press release is removed