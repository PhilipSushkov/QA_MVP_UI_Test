Feature: Check Site Maintenance page opens well

    Background: I am logged into Admin site / have valid session cookie

    Scenario: Find Site Maintenance menu item and open the page
         Given: Dashboard page opened
         When I click System Admin menu item
         And click Site Maintenance item
         Then Site Maintenance page opens
         And expected Title of the page is "Site Maintenance"

    Scenario: Check that Site Maintenance page contains goLive, toggleOneTouch and toggleTwoFactorAuthentication buttons
         Given: Site Maintenance page opened
         When I find goLive button
         Then goLive button should exist
         When I find toggleOneTouch button
         Then toggleOneTouch should exist
         When I find toggleTwoFactorAuthentication button
         Then toggleTwoFactorAuthentication should exist