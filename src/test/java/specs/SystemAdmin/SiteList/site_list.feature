Feature: Check Site List page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find Site List menu item and open the page
        Given: Dashboard page opened
        When I click System Admin menu item
        And click Site List item
        Then Site List page opens
        And expected Title of the page is "Site List"

     Scenario: Check that Site List page contains workflow notifications list
        Given: Site List page opened
        When I find Site List table
        Then Site Name column contains more than 1 item