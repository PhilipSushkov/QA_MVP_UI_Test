Feature: Get email notifications from publishing an event
  
  Background: 
    Given that SendGrid is enabled
      And a testing list exists
      And a validated, active test Subscriber to that list exists
      And an event Alert Filter is set up
      And I am logged into Admin site on the Events page

  Scenario: Send mail by publishing an event
    When I remove all filters from the event Alert Filter
    And I publish an event
    Then I receive an email stating that a new event has been published

  Scenario: Block mail with a Title Exclude filter
    When I add a Title Exclude filter to the event Alert Filter
    And I publish an event with the excluded words in the title
    Then I do not receive an email about the event

  Scenario: Block mail with a Body Exclude filter
    When I add a Body Exclude filter to the event Alert Filter
    And I publish an event with the excluded words in the body
    Then I do not receive an email about the event

  Scenario: Send mail with a Title Include filter
    When I add a Title Include filter to the event Alert Filter
    And I publish an event with the included words in the title
    Then I receive an email about the event

  Scenario: Block mail with a Title Include filter
    When I add a Title Include filter to the event Alert Filter
    And I publish an event without the included words in the title
    Then I do not receive an email about the event

  Scenario: Send mail with a Body Include filter
    When I add a Body Include filter to the event Alert Filter
    And I publish an event with the included words in the body
    Then I receive an email about the event

  Scenario: Block mail with a Body Include filter
    When I add a Body Include filter to the event Alert Filter
    And I publish an event without the included words in the body
    Then I do not receive an email about the event