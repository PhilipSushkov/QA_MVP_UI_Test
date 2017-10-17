Feature: Check Download List page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find Download menu item and open the page
        Given: Dashboard page opened
        When I click Content Admin menu item
        And click Download List item
        Then Download List page opens
        And expected Title of the page is "Download List"

    Scenario: Save a new department
        Given: Download List page opened
        When I click Add New item
        And  Fill out the form to create a new Download
        When I click Save item
        Then the page is refreshed
        And  the status shows the download is in progress
        Then the status is "in progress"
        And  expect data of the new download matches that of the JSON file

    Scenario: Save and submit download
        Given: Download List page opened
        When I click edit button
        And I fill in comment
        When I click save and submit button
        Then the status is "for approval"
        Then I save URL of page to JSON file
        Then I compare contents of form with JSON file

    Scenario: Publish download
        Given: Download List page is open
        When I go to saved URL
        And I click on publish button
        Then the status is "live"
        And expected data is saved into JSON file

    Scenario: Revert download
        Given: Download List page is open
        When I go to saved URL
        And I change download description
        And I enter comment
        When I click save and submit button
        Then the status is "for approval"
        And expected data is saved into JSON file
        Given: Download List page is open
        When I go to saved URL
        When I click revert button
        Then the status is "live"
        And expected data is saved into JSON file
        Given: Download List page is open
        When I go to saved URL
        Then I compare contents of form with JSON file

    Scenario: Change and submit download
        Given: Download List page is open
        When I go to saved URL
        And I change download description
        And I enter comment
        When I click save and submit button
        Then the status is "for approval"
        And expected data is saved into JSON file
        Given: Download List page is open
        When I go to saved URL
        Then I compare contents of form with JSON file

    Scenario: Publish edited download
        Given: Download List page is open
        When I go to saved URL
        And I click on publish button
        Then the status is "live"
        And expected data is saved into JSON file

    Scenario: Delete download
        Given: Download List page is open
        When I go to saved URL
        And I enter comment
        When I click delete button
        Then the status is "for approval"
        And expected data is saved into JSON file

    Scenario: Remove download
        Given: Download List page is open
        When I go to saved URL
        And status is "delete pending"
        And I enter comment
        When I click save and submit button
        And expected data is saved into JSON file