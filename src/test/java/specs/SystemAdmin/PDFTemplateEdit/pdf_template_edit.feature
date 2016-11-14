Feature: Check PDF Template Edit page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find PDF Template Edit menu item and open the page
         Given: Dashboard page opened
         When I click System Admin menu item
         And click PDF Template Edit item
         Then PDF Template Edit page opens
         And expected Title of the page is "PDF Template Edit"

    Scenario: Check that PDF Template Edit page contains Header, Body and Footer blocks
         Given: PDF Template Edit page opened
         When I find Header block
         Then HeaderRadEditor should exist
         When I find Body block
         Then BodyRadEditor should exist
         When I find Footer block
         Then FooterRadEditor should exist