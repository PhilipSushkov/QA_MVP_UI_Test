Feature: I can change the parameters of the StockChart module

  Background: The StockChart module exists on a page on the live site and the module edit page is open

  Scenario: I can add disclaimer text to the stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I add text to the DisclaimerText field
      And I save, submit, and publish
    Then the disclaimer text appears on the stock chart on the live site

  Scenario: I can hide/show the interactive stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the InteractiveChart dropdown menu to False
      And I save, submit, and publish
    Then the interactive stock chart does not appear on the live site
    When I click on Properties
      And I set the InteractiveChart dropdown menu to True
      And I save, submit, and publish
    Then the interactive stock chart appears on the live site

  Scenario: I can modify the dimensions of the interactive stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the Width and Height fields to non-default values
      And I save, submit, and publish
    Then the interactive stock chart on the live site is displayed with the new dimensions

  Scenario: I can modify the line thicknesses of the interactive stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the LineThicknesses field to a non-default value
      And I save, submit, and publish
    Then the interactive stock chart on the live site is displayed with the new line thicknesses

  Scenario: I can modify the buffers of the interactive stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the LeftBuffer, RightBuffer, and BottomBuffer fields to non-default values
      And I save, submit, and publish
    Then the interactive stock chart on the live site is displayed with the new buffer dimensions

  Scenario: I can modify the height of the bottom volume/legend table
    Given that I am on the Module Edit page
    When I click on Properties
      And I set the BottomTableHeight field to a nonstandard value
      And I save, submit, and publish
    Then the volume/legend table at the bottom of the interactive stock chart is displayed with the new height

  Scenario: I can modify the colours of the interactive stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set all Color fields to valid, non-default values
      And I save, submit, and publish
    Then the interactive stock chart on the live site is displayed with the new colour scheme

  Scenario: I can show/hide the previous closing price
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowPrevClose to Yes
      And I save, submit, and publish
    Then the prev close is displayed in the StockChart module on the live site
    When I click on Properties
      And I set ShowPrevClose to No
      And I save, submit, and publish
    Then the prev close is not displayed in the StockChart module on the live site

  Scenario: I can place the grid in the foreground or background of the chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set GridLocation to Foreground
      And I save, submit, and publish
    Then the grid appears in the foreground of the stock chart on the live site
    When I click on Properties
      And I set GridLocation to Background
      And i save, submit, and publish
    Then the grid appears in the background of the stock chart on the live site

  Scenario: I can hide/show the volume table
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowVolumeTable to No
      And I save, submit, and publish
    Then the volume table is not displayed in the StockChart module
    When I click on Properties
      And I set ShowVolumeTable to yes
      And I save, submit, and publish
    Then the volume table is displayed in the StockChart module

  Scenario: I can show/hide the legend table
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowLegendTable to Yes
      And I save, submit, and publish
    Then the legend table is displayed in the StockChart module
    When I click on Properties
      And I set ShowLegendTable to No
      And I save, submit, and publish
    Then the legend table is not displayed in the StockChart module

  Scenario: I can set the display type for the stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set Type to a non-default value
      And I save, submit, and publish
    Then the stock chart data is displayed according to the chosen type

  Scenario: I can set the period of the the stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set Period to a non-default value
      And I save, submit, and publish
    Then the stock chart data is displayed over the chosen period

  Scenario: I can toggle the chart between % Change and Price
    Given that I am on the Module Edit page
    When I click on Properties
      And I set StartChartBy to Percentage
      And I save, submit, and publish
    Then the stock chart displays %Change data on the live site
    When I click on Properties
      And I set StartChartBy to Price
      And I save, submit, and publish
    Then the stock chart displays price data on the live site

  Scenario: I can modify the dimensions of the period tabs
    Given that I am on the Module Edit page
    When I click on Properties
      And I set TabWidth and TabHeight to non-default values
      And I save, submit, and publish
    Then the period tabs at the top of the stock chart are displayed with the modified dimensions

  Scenario: I can modify the font size and face of the stock chart
    Given that I am on the Module Edit page
    When I click on Properties
      And I set FontSize to a valid non-zero integer and FontFace to a valid non-default font
      And I save, submit, and publish
    Then the font size and face are modified on the stock chart

  Scenario: I can hide/show the various stock chart user options
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ChartByBox, ChartTypeBox, MovAvgsBox, CompareBox, and ShowSymBox to No
      And I save, submit, and publish
    Then the stock chart user options (e.g. Chart Type) are not displayed on the module
    When I click on Properties
      And I set ChartByBox, ChartTypeBox, MovAvgsBox, CompareBox, and ShowSymBox to Yes
      And I save, submit, and publish
    Then the stock chart user options (e.g. Chart Type) are displayed on the module

  Scenario: I can hide/show the ToolTip
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowToolTip to False
      And I save, submit, and publish
    Then the ToolTip (mouseover infobox showing date, stock price, and volume) does not appear on the stock chart
    When I click on Properties
      And I set ShowToolTip to True
      And I save, submit, and publish
    Then the ToolTip appears on the stock chart

  Scenario: I can hide/show the navigator
    Given that I am on the Module Edit page
    When I click on Properties
      And I set ShowNavigator to False
      And I save, submit, and publish
    Then the navigator does not appear below the stock chart
    When I click on Properties
      And I set ShowNavigator to True
      And I save, submit, and publish
    Then the navigator appears below the stock chart