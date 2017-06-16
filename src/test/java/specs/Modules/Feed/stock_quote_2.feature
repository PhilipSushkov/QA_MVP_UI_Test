Feature: I can change the parameters of the Stock Quote 2 module
  
  Background: The StockQuote2 module exists on a page on the live site and the module edit page is open

  Scenario: I can add a custom css class to the StockQuote2 Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a css class to the CustomCss field
      And I set DefaultCssOverride to True
      And I save, submit, and publish
    Then the StockQuote2 module is styled according to the custom css on the live site

  Scenario: I can show/hide the stock symbol selector
    Given that I am on the Module Edit page
    When I click on Properties
      And I set HideStockSelector to True
      And I save, submit, and publish
    Then the stock symbol selector is not displayed in the StockQuote2 module on the live site
    When I click on Properties
      And I set HideStockSelector to False
      And I save, submit, and publish
    Then the stock symbol selector is displayed in the StockQuote2 module on the live site
    
  Scenario: I can show/hide the delay text
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowDelayText to False
      And I save, submit, and publish
    Then the delay text is not displayed in the StockQuote2 module on the live site
    When I click on Properties
      And I set ShowDelayText to True
      And I save, submit, and publish
    Then the delay text is displayed in the StockQuote2 module on the live site

  Scenario: I can override the index to display
    Given that I am on the Module Edit page
      And I have access to the current StockQuote2 module data for comparison
    When I click on Properties
      And I add a valid index from SiteAdmin>Lookup List>indices to the IndiceOverride field
      And I save, submit, and publish
    Then the new StockQuote2 module data on the live site is different from the original data

  Scenario: I can override the lookup value for the index
    Given that there exists a ticker lookup other than 'indices' in Site Admin>Lookup List
    And I am on the Module Edit page
      And I have access to the current StockQuote2 module data for comparison
    When I click Properties
      And I add the name of the alternate ticker lookup to the LookupOverride field
      And I add a valid index from that ticker lookup to the IndiceOverride field
    Then the new StockQuote2 module data on the live site is different from the original data

  Scenario: I can customize the date format on the StockQuote2 Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid custom date format to the DateFormat field
      And I save, submit, and publish
    Then the date in the StockQuote2 module on the live site is formatted according to the custom format
    
  Scenario: I can customize the time format on the StockQuote2 Module
    Given that I am on the Module Edi page
    When I click on Properties
      And I add a valid custom time format to the TimeFormat field
      And I save, submit, and publish
    Then the time in the StockQuote2 module on the live site is formatted according to the custom format

  Scenario: I can customize the decimal format on the StockQuote2 Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid custom decimal format to the DecimalFormat field
      And I save, submit and publish
    Then the decimals in the StockQuote2 module on the live site are formatted according to the custom format

  Scenario: I can revert the StockQuote2 Module to the original settings
    Given that I am on the Module Edit page
    When I click on Properties
      And I set all fields to their original values
      And I save, submit, and publish
    Then the StockQuote2 module on the live site is reverted to its original state