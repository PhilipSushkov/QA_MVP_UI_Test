Feature: I can connect a Facebook account to Q4Web

  Background: Social Media Summary page is open
    Given that I am logged into Admin site / have valid session cookie
    When I click "Social Media Dashboard"
    Then the Social Media Summary page opens
      And the title of the page is "Social Media Summary"

  Scenario: I can authorize a Facebook account
    Given that no Facebook account is currently setup
      And I am not currently logged in to Facebook
    When I click "Authorize"
    Then I am directed to a Facebook login page
    When I enter in the email and password of my Facebook account
      And I click "Log In"
    Then I am returned to the Social Media Summary page
      And the Facebook section of this page says "Select the Facebook Page you want to link"
    When I select the radio button for one of the pages displayed
      And I click "Select"
    Then the Facebook account holder's name, page name, and number of fans are displayed
      And a green checkmark is displayed next to Facebook
      And "Settings", "De-Authorize", "Disable", and "Re-Authorize" buttons are displayed

  Scenario: I can disable a Facebook account
    Given that a Facebook account is setup and enabled
    When I click "Disable"
    Then the word "Facebook", the checkmark, the account holder's name, the company, and the number of followers become grey
      And a "Disable" button is no longer present
      And an "Enable" button is present

  Scenario: I can enable a disabled Facebook account
    Given that a Facebook account is setup and disabled
    When I click "Enable"
    Then the word "Facebook", the checkmark, the account holder's name, the company, and the number of followers are no longer grey
      And an "Enable" button is no longer present
      And a "Disable" button is present

  Scenario: I can re-authorize a Facebook account
    Given that a Facebook account is setup and enabled
      And I am not currently logged in to Facebook
        NOTE: Since authorization process involves logging in to Facebook, Facebook session cookie (cookie named "xs") will be removed at this point.
    When I click "Re-Authorize"
    Then I am directed to a Facebook login page
    When I enter in the email and password of my Facebook account
      And I click "Log In"
    Then I am returned to the Social Media Summary page
      And the Facebook section of this page says "Select the Facebook Page you want to link"
    When I select the radio button for one of the pages displayed
      And I click "Select"
    Then the Facebook account holder's name, page name, and number of fans are displayed
      And a green checkmark is displayed next to Facebook
      And "Settings", "De-Authorize", "Disable", and "Re-Authorize" buttons are displayed

  Scenario: I can modify the settings for Facebook posting
    When I click "Settings"
    Then the Facebook Social Templates screen opens
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

  Scenario: I can de-authorize a Facebook account
    Given that a Facebook account is setup
    When I click "De-Authorize"
    Then an alert appears saying "Are you sure you want to de-authorize Facebook account?"
    When I click "OK"
    Then a red "X" is displayed next to Facebook
      And "Facebook account is not setup. Click the Authorize button below to add your Facebook account." is displayed
      And "Settings" and "Authorize" buttons are displayed