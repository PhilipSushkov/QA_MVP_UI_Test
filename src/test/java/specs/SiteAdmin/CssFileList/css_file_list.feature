Feature: Check Css File List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Css File List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Css File List item
       Then Css File List page opens
       And expected Title of the page is "Css File List"

   Scenario: Check that Css File List page contains Definition Names and pagination
       Given: Css File List page opened
       When I find Css File table
       Then Css Name column equals 2 items