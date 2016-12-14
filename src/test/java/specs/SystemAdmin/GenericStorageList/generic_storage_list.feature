Feature: Generic Storage Filter page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Generic Storage Filter List menu item and open the page

    Given: Dashboard page opened
        When I click System Admin menu item
        And click Generic Storage Filter item
        Then Generic Storage Filter page opens
        And expected Title of the page is "Generic Storage Filter"

    Scenario: Check that Generic Storage Filter page contains alert filter list
        Given: Generic Storage Filter page opened
        When I find Generic Storage Filter table
        Then Generic Storage Filter table contains headers