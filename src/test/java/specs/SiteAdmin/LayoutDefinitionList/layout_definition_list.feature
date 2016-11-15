Feature: Check Layout Definition List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Layout Definition List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Layout Definition List item
       Then Layout Definition List page opens
       And expected Title of the page is "Layout Definition List"

   Scenario: Check that Layout Definition List page contains Definition Names
       Given: Layout Definition List page opened
       When I find Layout Definition table
       Then Definition Name column contains more or equal to 3 items