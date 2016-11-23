Feature: Check Financial Reports page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Financial Reports menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Financial Reports item
       Then Financial Reports page opens
       And expected Title of the page is "Financial Report List"

   Scenario: Check that Financial Reports page contains Title items, pagination and Filter By Tag field
       Given: Financial Reports page opened
       When I find Financial Reports table
       Then Title column contains at least 6 items

       When I find Financial Reports pagination
       Then Financial Reports pagination should exist

       When I find Filter By Tag field
       Then Filter By Tag field should exist