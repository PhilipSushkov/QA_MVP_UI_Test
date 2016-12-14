Feature: Check Lookup List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Lookup List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Lookup List item
       Then Lookup List page opens
       And expected Title of the page is "Lookup List"

   Scenario: Check that Lookup List page contains Lookup Text
       Given: Lookup List page opened
       When I find Lookup table

       When I find Lookup Type dropdown list
       Then Lookup Type dropdown list should exist