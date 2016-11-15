Feature: Check User Group List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find User Group List menu item and open the page
       Given: Dashboard page opened
       When I click System Admin menu item
       And click User Group List item
       Then User Group List page opens
       And expected Title of the page is "User Group List"

   Scenario: Check that User Group List page contains User Group Names
       Given: User Group List page opened
       When I find User Group table
       Then User Group Name column contains more than 5 items