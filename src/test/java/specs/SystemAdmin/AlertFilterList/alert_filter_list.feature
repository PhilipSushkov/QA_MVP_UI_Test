Feature: Check Alert Filter List page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find Alert Filter List menu item and open the page
        Given: Dashboard page opened
        When I click System Admin menu item
        And click Alert Filter List item
        Then Alert Filter List page opens
        And expected Title of the page is "Alert Filter List"

     Scenario: Check that Alert Filter List page contains alert filter list
        Given: Alert Filter List page opened
        When I find Alert Filter table
        Then Filter Name column contains more than 2 items