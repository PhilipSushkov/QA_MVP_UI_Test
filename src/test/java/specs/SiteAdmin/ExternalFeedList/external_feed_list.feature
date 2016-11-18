Feature: Check External Feed List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find External Feed List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click External Feed List item
       Then External Feed List page opens
       And expected Title of the page is "External Feed List"

   Scenario: Check that External Feed List page contains Descriptions
       Given: External Feed List page opened
       When I find External Feed table
       Then Description column equals 4 items