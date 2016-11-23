Feature: Check Press Release Categories page opens well

Background: I am logged into Admin site / have valid session cookie

   Scenario: Find Press Release Categories menu item and open the page
       Given: Dashboard page opened
       When I click Content Admin menu item
       And click Press Release Categories item
       Then Press Release Categories page opens
       And expected Title of the page is "Press Release Categories"

   Scenario: Check that Press Release Categories page contains Category Names
       Given: Press Release Categories page opened
       When I find Press Release Categories table
       Then CategoryName column contains at least 1 item