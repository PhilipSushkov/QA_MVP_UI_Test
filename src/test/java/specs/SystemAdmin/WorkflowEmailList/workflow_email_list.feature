Feature: Check Workflow Email List page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find Workflow Email List menu item and open the page
        Given: Dashboard page opened
        When I click System Admin menu item
        And click Workflow Email List item
        Then Workflow Email List page opens
        And expected Title of the page is "Workflow Email List"

    Scenario: Check that Workflow Email List page contains workflow notifications list
        Given: Workflow Email List page opened
        When I find Workflow Email table
        Then Description column contains more than 5 items