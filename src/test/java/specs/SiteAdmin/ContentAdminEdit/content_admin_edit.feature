Feature: Check Edit Content Admin Pages page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Edit Content Admin Pages menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Edit Content Admin Pages item
       Then Edit Content Admin Pages page opens
       And expected Title of the page is "Edit Content Admin Pages"

   Scenario: Check that Edit Content Admin Pages page contains Title items and checkboxes
       Given: Edit Content Admin Pages page opened
       When I find Edit Content Admin Pages table
       Then Title columns contains more than 20 items
       When I find Show In Nav? checkboxes
       Then Show In Nav? column contains more than 20 checkboxes