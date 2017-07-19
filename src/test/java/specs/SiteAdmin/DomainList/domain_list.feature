Feature: Check Domain List page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Domain List menu item and open the page
       Given: Dashboard page opened
       When I click Site Admin menu item
       And click Domain List item
       Then Domain List page opens
       And expected Title of the page is "Domain List"

   Scenario: Check that Domain List page contains Domains
       Given: Domain List page opened
       When I find Domain table
       Then Domain column contains at least 2 items
       When I find the link to Public Site Edit page
       Then the link Public Site Edit page should exist

    Scenario: Check that Domain can be saved without alternative domain
        Given: Domain List page is opened
        When I click "Add New"
        And I enter a valid domain name
        And I enter a valid landing page
        And I click "Save" button
        Then I should see

    Scenario: Check that alternative domains can be added
