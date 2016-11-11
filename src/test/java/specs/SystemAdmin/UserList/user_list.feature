Feature: Check User List page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find User List menu item and open the page
        Given: Dashboard page opened
        When I click System Admin menu item
        And click User List item
        Then User List page opens
        And expected Title of the page is "User List"

     Scenario: Check that User List page contains user list
        Given: User List page opened
        When I find User table
        Then User Name column contains more than 10 items