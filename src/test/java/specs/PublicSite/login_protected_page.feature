Feature: Login-protected page

  #This feature file is based on Login Protected Page test case
  #From TestRail: https://q4web.testrail.com/index.php?/cases/view/73748&group_by=cases:section_id&group_order=asc&group_id=2122

  Background:
    When I go to CMS Admin Page and login in
    Then The Admin site is displayed


  Scenario: Create a new employee
    Given That I'm on an admin site home page
    When I go to Site Admin -> Employee List
    Then Employee List should open
    When I click Add New Button
    Then Employee Edit should open
    When I fill in the information as follows:
         "Email -> test@q4inc.com
          Password -> q4pass1234!
          First Name -> FN
          Last Name -> LN
          Active Checkbox -> checked"
    And  Click Save button
    Then A new employee account should be in the list


  # Note: From this step on, the page on which a login module will be added is referred to as the ORIGIN page, and the page that is to be login protected is referred to as the TARGET page

   Scenario: Create a new Link To Page
     Given That I just added a new employee
     When I go to Site Admin -> Link To Page List
     Then Link To Page list should open
     When I click on Add New button
     Then Link To Page Edit should open
     When I fill in the information as follows:
          "Key Name: .testingLink
           Link To Page: Stock Information (this can be changed to whatever TARGET page you want)
           Comment: misc"
     And  Click Save and Submit button
     Then A new Link To Page pending for approval should be in the list
     When I Click the checkbox next to the new Link To Page
     And  Click Publish button
     Then The new Link To Page should be successfully published

    Scenario: Set up modules for the ORIGIN page
      Given That I just added a new Link To Page
      When I go to Page Admin
      And  Select the tab for the ORIGIN page that I want
      Then The ORIGIN page tab should be opened with ORIGIN page displayed
      When I click on Edit button for the ORIGIN page
      Then Page Edit for the ORIGIN page should open
      When I click on Add New button for module
      Then Module Edit page should open
      When I fill in the information as follows:
           "Module Title: Public Login 4.3.0
            Module Definition: Public Login 4.3.0
            Region Name: Content Pane
            Comment: misc"
      And  Click Save And Submit button
      Then The new module should be pending for approval in Modules list
      When I click Edit button for Public Login 4.3.0 Module
      Then Module Edit page should open
      When I click Publish button
      Then The module should be published
      When I click on Edit button for Public Login 4.3.0 Module
      Then Module Edit page should open
      When I click Properties
      And  Fill in the information as follows:
           "AllowRegister: False
            AutoLogon: False
            LinkTo: .testingLink
            ShowLoggedON: True
            Comment: misc"
      And  Click Save and Submit button
      Then The module should be pending for approval in Modules list
      When I click on Edit button for Public Login 4.3.0 module
      Then Page Edit should open
      When I click Publish button
      Then The Module should be published
      When I fill in comment: "misc" for Page Edit page
      And  Click Save and Submit
      Then The edited page should be pending for approval in Page list
      When From the Public page list, I navigate to the ORIGN page
      And  Click edit
      Then Edit Page should open
      When I enter the comment: "misc"
      And  Click publish
      Then The page should be published

      Scenario: Set up the TARGET page
        Given That I just set up the ORIGIN page
        When I go to Page Admin -> TARGET page tab
        And  Click Edit button for TARGET page
        Then Page Edit page should open
        When I click the Security tab
        And  Remember checkbox position for cleanup
        And  Change the check boxes
        And  Click Save Security
        Then I should be back at Section tab of the Page Edit
        When I add comment: "misc"
        And  Click Save and Submit
        And  Publish the changes to the page
        Then The update should be published

       Scenario: Check in public site
         Given That I finished all the set-up above
         When I go to public site
         And  Look for the TARGET login protected page
         Then The TARGET login protected page should NOT be visible/accessible
         When I go to the ORIGIN page
         And  Scroll to the bottom
         And  Login to the Public Login 4.3.0 Module
         Then I should be directed to the TARGET login protected page
         When I click on a different menu bar page
         Then The TARGET login protected page menu option should still be accessible
         When I go to the ORIGIN page
         And  Scroll to the bottom
         And  Click the Logout button
         Then I should no longer be able to access the TARGET login protected page

        Scenario: Clean up
          Given That I ran the test successfully
          When I revert the security tab of the TARGET page
          And  Delete the login module from the ORIGIN page
          And  Delete the .testingLink Link To Page
          Then The site should be back to the original configuration before test
          #Note : DO NOT DELETE the Employee List Account