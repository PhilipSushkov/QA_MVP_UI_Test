Feature: I can change the parameters of the SECFiling module

  Background: The SECFiling module exists on a page on the live site and the module edit page is open

  Scenario: I can add an Exchange and Ticker to retrieve the SEC filing data for the company
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid exchange to the Exchange field and ticker to the Ticker field, or put 'CIK' in the Exchange field and a valid CIK number in the ticker field
      And I save, submit, and publish
    Then the SECFiling module on the live site displays all the company's SEC filings

  Scenario: I can add a custom css class to the SECFiling Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a css class to the CustomCss field
      And I save, submit, and publish
    Then the SECFiling module is styled according to the custom css on the live site

  Scenario: I can customize the date format for the SECFiling Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid date format to the DateFormat field
      And I save, submit, and publish
    Then the date in the SECFiling module is formatted according to the custom format

  Scenario: I can customize the number of filings on the SECFiling Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a small number to the NumOfFilings field
      And I save, submit and publish
    Then that number of SEC filings are displayed in the SECFiling module on the live site

  Scenario: I can show all SEC filings
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the ShowAll dropdown menu to True
      And I save, submit, and publish
    Then all SEC filings are displayed in the SECFiling module on the live site

  Scenario: I can hide or show the RSS link
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the ShowRssLink dropdown menu to False
      And I save, submit, and publish
    Then the RSS link is not visible on the SECFiling module on the live site
    When I click on Properties
      And I set the ShowRssLink dropdown menu to True
      And I save, submit, and publish
    Then the RSS link is visible on the SECFiling module on the live site

  Scenario: I can position the RSS link
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the RssLinkPosition dropdown menu to Show Top Only
      And I save, submit, and publish
    Then the RSS link is  visible at the top of the SECFiling module on the live site
    When I click on Properties
      And I set the RssLinkPosition dropdown menu to Show Bottom Only
      And I save, submit, and publish
    Then the RSS link is visible at the bottom of the SECFiling module on the live site
    When I click on Properties
      And I set the RssLinkPosition dropdown menu to Show Top and Bottom
      And I save, submit, and publish
    Then the RSS link is visible at thetop and bottom bottom of the SECFiling module on the live site

  Scenario: I can show or hide the column with the filer person name
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the ShowFiler dropdown menu to True
      And I save, submit, and publish
    Then the SEC Filing module on the live site has a column displaying the name of the filer for each filing

  Scenario: I can set the starting year for SEC filing data
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a year to the StartYear field
      And I save, submit, and publish
    Then the SEC Filing module on the live site does not display filings from before that year

  Scenario: I can show and hide filings without filing documents
    Given that I am on the Module Edit page
      And there exists at least one SEC filing without a filing document
    When I click on Properties
      And I set the HideFilingsWithNoDocuments dropdown menu to false
      And I save, submit, and publish
    Then the SEC Filing module on the live site displays the filings with no documents
    When I click on Properties
      And I set the HideFilingsWithNoDocuments dropdown menu to true
      And I save, submit, and publish
    Then the SEC Filing module on the live site does not display the filings with no documents

  Scenario: I can revert the SECFilings Module to the original settings
    Given that I am on the Module Edit page
    When I click on Properties
      And I remove all custom properties from all fields except Exchange and Ticker
      And I set all dropdown menus back to their default options
      And I save, submit, and publish
    Then the SECFilings module on the live site is reverted to its original state
