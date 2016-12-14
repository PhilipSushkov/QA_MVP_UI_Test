Feature: Check Module Definition List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Module Definition List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Module Definition List item
       Then Module Definition List page opens
       And expected Title of the page is "Module Definition List"

   Scenario: Check that Module Definition List page contains Definition Names and pagination
       Given: Module Definition List page opened
       When I find Module Definition table
       Then Definition Name column contains more than 10 items
       When I find Module Definition pagination
       Then Module Definition pagination should exist