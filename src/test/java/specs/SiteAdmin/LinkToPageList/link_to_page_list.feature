Feature: Check Link To Page List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Link To Page List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Link To Page List item
       Then Link To Page List page opens
       And expected Title of the page is "Link To Page List"

   Scenario: Check that Link To Page List page contains Key Names and pagination
       Given: Link To Page List page opened
       When I find Link To Page table
       Then Key Name column contains 15 items
       When I find Link To Page List pagination
       Then Link To Page List pagination should exist