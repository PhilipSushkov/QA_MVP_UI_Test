Feature: I can connect a LinkedIn account to Q4Web

  Background: Social Media Summary page is open
    Given that I am logged into Admin site / have valid session cookie
    When I click "Social Media Dashboard"
    Then the Social Media Summary page opens
      And the title of the page is "Social Media Summary"

  Scenario: I can authorize a LinkedIn account
    Given that no LinkedIn account is currently setup
      And I am not currently logged in to LinkedIn
    When I click "Authorize"
    Then I am directed to a LinkedIn authorization page
    When I enter in the email and password of my LinkedIn account
      And I click "Allow access"
    Then I am returned to the Social Media Summary page
      And the LinkedIn section of this page says "Select the Company page you want to link"
    When I select the radio button for one of the companies displayed
      And I click "Select"
    Then the LinkedIn account holder's name, company name, and number of followers are displayed
      And a green checkmark is displayed next to LinkedIn
      And "Settings", "De-Authorize", "Disable", and "Re-Authorize" buttons are displayed

  Scenario: I can disable a LinkedIn account
    Given that a LinkedIn account is setup and enabled
    When I click "Disable"
    Then the word "LinkedIn", the checkmark, the account holder's name, the company, and the number of followers become grey
      And a "Disable" button is no longer present
      And an "Enable" button is present

  Scenario: I can enable a disabled LinkedIn account
    Given that a LinkedIn account is setup and disabled
    When I click "Enable"
    Then the word "LinkedIn", the checkmark, the account holder's name, the company, and the number of followers are no longer grey
      And an "Enable" button is no longer present
      And a "Disable" button is present

  Scenario: I can re-authorize a LinkedIn account
    Given that a LinkedIn account is setup and enabled
      And I am not currently logged in to LinkedIn
    When I click "Re-Authorize"
    Then I am directed to a LinkedIn authorization page
    When I enter in the email and password of my LinkedIn account
      And I click "Allow access"
    Then I am returned to the Social Media Summary page
      And the LinkedIn section of this page says "Select the Company page you want to link"
    When I select the radio button for one of the companies displayed
      And I click "Select"
    Then the LinkedIn account holder's name, company name, and number of followers are displayed
      And a green checkmark is displayed next to LinkedIn
      And "Settings", "De-Authorize", "Disable", and "Re-Authorize" buttons are displayed

  Scenario: I can modify the settings for LinkedIn posting
    When I click "Settings"
    Then the LinkedIn Social Templates screen opens
      And the templates for different types of postings are displayed
      And an edit button is displayed beside each template
    When I click "Edit" next to one of the templates
    Then the edit screen for that template opens
    When I modify the textbox
      And I click "Save"
    Then I am returned to the list of templates
      And that template appears as modified
    [test will include reverting template back to original setting]
    When I click the "X" on the top-right corner of the templates screen
    Then the templates screen will close

  Scenario: I can de-authorize a LinkedIn account
    Given that a LinkedIn account is setup
    When I click "De-Authorize"
    Then an alert appears saying "Are you sure you want to de-authorize LinkedIn account?"
    When I click "OK"
    Then a red "X" is displayed next to LinkedIn
      And "LinkedIn account is not setup. Click the Authorize button below to add your LinkedIn account." is displayed
      And "Settings" and "Authorize" buttons are displayed