Feature: Get email notifications from publishing a press release

  Background:
    Given that SendGrid is enabled
      And a test Mailing List exists
      And a validated, active test Subscriber to that list exists
      And a press release Alert Filter is set up
      And I am logged into Admin site on the Press Release page

  Scenario: Send mail by publishing a press release
    When I remove all filters from the press release Alert Filter
      And I publish a press release
    Then I receive an email stating that a new press release has been published

  Scenario: Block mail with a Title Exclude filter
    When I add a Title Exclude filter to the press release Alert Filter
      And I publish a press release with the excluded words in the title
    Then I do not receive an email about the press release

  Scenario: Block mail with a Body Exclude filter
    When I add a Body Exclude filter to the press release Alert Filter
      And I publish a press release with the excluded words in the body
    Then I do not receive an email about the press release

  Scenario: Send mail with a Title Include filter
    When I add a Title Include filter to the press release Alert Filter
      And I publish a press release with the included words in the title
    Then I receive an email about the press release

  Scenario: Block mail with a Title Include filter
    When I add a Title Include filter to the press release Alert Filter
      And I publish a press release without the included words in the title
    Then I do not receive an email about the press release

  Scenario: Send mail with a Body Include filter
    When I add a Body Include filter to the press release Alert Filter
      And I publish a press release with the included words in the body
    Then I receive an email about the press release

  Scenario: Block mail with a Body Include filter
    When I add a Body Include filter to the press release Alert Filter
      And I publish a press release without the included words in the body
    Then I do not receive an email about the press release