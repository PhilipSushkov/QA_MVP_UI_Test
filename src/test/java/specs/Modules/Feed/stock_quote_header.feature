Feature: I can change the parameters of the StockQuoteHeader module

  Background: The StockQuoteHeader module exists on a page on the live site and the module edit page is open

  Scenario: I can add a custom css class to the StockQuoteHeader Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a css class to the CustomCss field
      And I set DefaultCssOverride to True
      And I save, submit, and publish
    Then the StockQuoteHeader module is styled according to the custom css on the live site

  Scenario: I can stop CustomCss from overriding the default css class
    Given that I am on the Module Edit page
      And a custom css class is set for the module
    When I click on Properties
      And I set DefaultCssOverride to False
      And I save, submit, and publish
    Then the custom css does not override the default css on the StockQuoteHeader on the live site

  Scenario: I can add description text to the beginning of the the stock quote
    Given that I am on the Module Edit page
    When I click on Properties
      And I add text to the Description1 field
      And I save, submit, and publish
    Then the text appears at the beginning of the stock quote on the live site

  Scenario: I can add text describing the stock quote
    Given that I am on the Module Edit page
    When I click on Properties
      And I add text to the Description2 field
      And I save, submit, and publish
    Then the text appears describing the stock quote on the live site

  Scenario: I can add description text to the end of the stock quote
    Given that I am on the Module Edit page
    When I click on Properties
      And I add text to the Description3 field
      And I save, submit, and publish
    Then the text appears at the end of the stock quote on the live site

  Scenario: I can change the exchange and symbol of the stock quote
    Given that I am on the Module Edit page
      And I have access to the current stock quote header data for comparison
    When I click on Properties
      And I change the Exchange and Symbol fields to a different valid stock exchange/symbol
      And I save, submit, and publish
    Then the new StockQuoteHeader module on the live site displays data for the new stock

  Scenario: I can customize the decimal format on the StockQuoteHeader Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid custom decimal format to the DecimalFormat field
      And I save, submit and publish
    Then the decimals in the StockQuoteHeader module on the live site are formatted according to the custom format

  Scenario: I can customize the date format on the StockQuoteHeader Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid custom date format to the DateFormat field
      And I save, submit, and publish
    Then the date in the StockQuoteHeader module on the live site is formatted according to the custom format

  Scenario: I can revert the StockQuoteHeader Module to the original settings
    Given that I am on the Module Edit page
    When I click on Properties
      And I remove all custom properties from CustomCss, Description and Format fields
      And I set the Exchange and Symbol fields back to their original values
      And I save, submit, and publish
    Then the StockQuoteHeader module on the live site is reverted to its original state