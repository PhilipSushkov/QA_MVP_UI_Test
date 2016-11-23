Feature: pages on public site are displayed properly

  Background:
    When I navigate to the URL of the public site
    Then the home page of the public site is displayed

  Scenario: Version number is correct
    Given that I am on a page of the public site
    Then the version number on that page is valid

  Scenario: Stock Information page contains Xignite stock chart
    When I select "Stock Information" from the site menu
    Then the Xignite stock chart is displayed at the top of the page
    When I click on the time periods above the chart
    Then the chart displays those time periods
    When I hover the mouse over the chart
    Then the stock price and volume values at that position are displayed

  Scenario: Stock Information page contains stock quote
    When I select "Stock Information" from the site menu
    Then the stock quote is displayed below the Xignite stock chart
    And all the appropriate values are displayed
    And the values displayed are accurate (keeping into account the 20 minute delay)

  Scenario: Stock Information page contains TickerTech stock chart
    When I select "Stock Information" from the site menu
    Then the TickerTech stock chart is displayed below the stock quote
    When I click on the time periods above the chart
    Then the chart displays those time periods
    When I click on "% change"
    Then the chart displays % change
    When I click on "price"
    Then the chart displays stock price again
    When I click on each of the chart types
    Then the chart displays that type
    When I type the symbol of a stock into the "Compare vs." textbox
    And I select the checkboxes of one or more indices
    And I click the "Compare" button
    Then the chart displays a comparison between my stock, the entered in stock, and those indices

  Scenario: Stock Information page contains historical stock quotes
    When I select "Stock Information" from the site menu
    Then the "Historical Stock Quote" section is displayed at the bottom of the page
    And the appropriate values for the previous trading day are displayed
    And the values displayed are accurate
    When a different date is selected
    And I click the "Look Up" button
    Then the values displayed are accurate for that day

  Scenario: Financial Reports page contains reports
    Given that the site has financial reports
    When I select "Financial Reports" from the site menu
    Then a list of financial reports is displayed
    When I click on a title of a report
    Then the report opens in a new tab (as either a web page or a pdf file)

  Scenario: Press Releases page contains press releases
    Given that the site has press releases from both the current and previous years
    When I select "Press Releases" from the site menu
    Then a list of press releases from the current year is displayed
    When I click on a different year (displayed at the top of the list)
    Then press releases from that year are displayed
    When I click on the headline of a press release
    Then the press release opens

  Scenario: Events page contains events
    Given that the site has events
    When I select "Events" from the site menu
    Then a list of upcoming events is displayed (if there are no upcoming events then "No items found" will be displayed)
    When I click on "Past Events"
    Then a list of past events will be displayed
    When I click on the name of an event
    Then a page displaying more information on that event will be loaded

  Scenario: Presentation page contains presentations
    Given that the site has presentations from both the current and previous years
    When I select "Presentations" from the site menu
    Then a list of presentations from the current year is displayed
    When I click on a different year (displayed at the top of the list)
    Then presentations from that year are displayed
    When I click on "View this Presentation"
    Then then the presentation opens in a new tab

  Scenario: Subscribing to email alerts works
    Given that the site allows email alerts
    When I select "Site Map" from the site footer
    And select "Email Alerts" from the site map
    Then the email alerts page opens
    When I input my email credentials in the subscribe section
    And select alert options
    And submit the form
    Then a message appears signifying subscription email went out

  Scenario: Unsubscribing to email alerts works
    Given that the site allows email alerts
    When I select "Site Map" fromm the site footer
    And select "Email Alerts" from the site map
    Then the email alerts page opens
    When I input my email credentials in the unsubscibe section
    And submit the form
    Then a message appears signifying unsubscribe success

  Scenario: SEC Filings page contains filings
    Given that the site has filings from both the current and previous years
    When I select "SEC Filings" from the site menu
    Then a list of filings from the current year is displayed
    When I click on a different year (displayed at the top of the list)
    Then filings from that year are displayed
    When I click on a pdf icon
    Then a filing opens in a new tab (as a pdf file)

  # Consider adding FAQ test (FAQ page doesn't currently exist on chicagotest)

  Scenario: RSS Feeds page works
    Given that the site has RSS feeds
    When I select "RSS Feeds" from the site menu
    Then a list of RSS feeds is displayed
    When I click on the name of a RSS feed
    Then the feed opens in a new tab (as a RSS feed)

  Scenario: Board of Directors page displays people
    Given that the site has people identified as part of the "Board of Directors"
    When I select "Board of Directors" from the site menu
    Then a list of people is displayed with biographical information

