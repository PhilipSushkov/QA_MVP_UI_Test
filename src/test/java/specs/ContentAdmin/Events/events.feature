Feature: Create an event

  Background:
    Given that I am logged into Admin site (have valid session cookie)

  Scenario: Submit a new event
    When I click “Add an Event”
    And fill in necessary information (date, time, headline, comment)
    And click “Save And Submit”
    Then that event is listed in the “Event Page”
    And the displayed status of that listing is “For Approval”

  Scenario: Publish an event
    Given that there exists an event with the status “For Approval”
    And I a, on the event List page
    When I select the event’s checkbox
    And I click “Publish”
    Then the event’s status become “Live” or “Live - Publish Pending”

  Scenario: Verify the presence of a recently published event on the public site
    Given that I have just published a new event
    When I navigate to the public site’s event page
    Then that event is displayed (allow for delay of up to 2 minutes for that event to appear)