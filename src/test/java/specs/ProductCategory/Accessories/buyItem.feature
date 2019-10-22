Feature: User should be able to buy an item from store.demoqa.com e-commerce website

  Background: No authorization needed

  Scenario: Place the order for Magic Mouse item in Product category, Accessories section
    Given Go to http://store.demoqa.com

    When Go to Product category
    And select Accessories
    And Click on 'Add to Cart' for just Magic Mouse
    And Click on 'Checkout'
    Then Check-Out Page opens
    And Magic Mouse item exists on the page
    And Quantity of items equal to 1

    When Click on Continue
    Then Email and billing/contact details sections appeared

    When Fill up Email field
    And Fill up billing/contact details fields
    And click Purchase
    Then Transaction Results page opens
    Then Order has been placed: Magic Mouse item exists on the page
    And Quantity of items equal to 1
    And Total amount equals to $150