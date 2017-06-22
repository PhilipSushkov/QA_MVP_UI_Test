Feature: I can change the parameters of the StockHistorical2 module

  Background: The StockHistorical2 module exists on a page on the live site and the module edit page is open

  Scenario: I can add a custom css class to the StockHistorical2 Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a css class to the CustomCss field
      And I save, submit, and publish
    Then the StockHistorical2 module is styled according to the custom css on the live site

  Scenario: I can set the starting year for the StockHistorical2 Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid year to the StartYear field
      And I save, submit, and publish
    Then the available years in the StockHistorical2 module on the live site start at the proper year

  Scenario: I can override the index to display
    Given that I am on the Module Edit page
      And I have access to the current StockHistorical2 module data for comparison
    When I click on Properties
      And I add a valid index from SiteAdmin>Lookup List>indices to the IndiceOverride field
      And I save, submit, and publish
    Then the new StockHistorical2 module data on the live site is different from the original data

  Scenario: I can override the lookup value for the index
    Given that there exists a valid ticker lookup other than 'indices' in Site Admin>Lookup List
      And I am on the Module Edit page
      And I have access to the current StockHistorical2 module data for comparison
    When I click Properties
      And I add the name of the alternate ticker lookup to the LookupOverride field
      And I add a valid index from that ticker lookup to the IndiceOverride field
    Then the new StockHistorical2 module data on the live site is different from the original data

  Scenario: I can customize the decimal format on the StockHistorical2 Module
    Given that I am on the Module Edit page
    When I click on Properties
      And I add a valid custom decimal format to the DecimalFormat field
      And I save, submit and publish
    Then the decimals in the StockHistorical2 module on the live site are formatted according to the custom format

  Scenario: I can revert the StockHistorical2 Module to the original settings
    Given that I am on the Module Edit page
    When I click on Properties
      And I remove all custom properties from CustomCss, Description and Format fields
      And I set the Exchange and Symbol fields back to their original values
      And I save, submit, and publish
    Then the StockHistorical2 module on the live site is reverted to its original state
