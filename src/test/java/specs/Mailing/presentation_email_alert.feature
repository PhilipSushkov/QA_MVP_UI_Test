Feature: Get email notifications from publishing a presentation

  Background:
    Given that SendGrid is enabled
      And I am logged into Admin site
      And a testing list exists
      And a validated, active test Subscriber to that list exists
      And a presentation Alert Filter is set up

  Scenario: Send mail by publishing a presentation
    When I remove all filters from the presentation Alert Filter
      And I publish a presentation
    Then I receive an email stating that a new presentation has been published

  Scenario: Block mail with a Title Exclude filter
    When I add a Title Exclude filter to the presentation Alert Filter
      And I publish a presentation with the excluded words in the title
    Then I do not receive an email about the presentation`

  Scenario: Block mail with a Body Exclude filter
    When I add a Body Exclude filter to the presentation Alert Filter
      And I publish a presentation with the excluded words in the body
    Then I do not receive an email about the presentation

  Scenario: Send mail with a Title Include filter
    When I add a Title Include filter to the presentation Alert Filter
      And I publish a presentation with the included words in the title
    Then I receive an email about the presentation

  Scenario: Block mail with a Title Include filter
    When I add a Title Include filter to the presentation Alert Filter
      And I publish a presentation without the included words in the title
    Then I do not receive an email about the presentation

  Scenario: Send mail with a Body Include filter
    When I add a Body Include filter to the presentation Alert Filter
      And I publish a presentation with the included words in the body
    Then I receive an email about the presentation

  Scenario: Block mail with a Body Include filter
    When I add a Body Include filter to the presentation Alert Filter
      And I publish a presentation without the included words in the body
    Then I do not receive an email about the presentation