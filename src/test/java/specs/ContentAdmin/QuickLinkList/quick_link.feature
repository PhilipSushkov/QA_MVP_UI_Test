Feature: Quick Link page features work as intended
  Background: I am logged into Admin site / have valid session cookie
    And I have selected Quick Link page from Content Admin

    Scenario: I can open Quick Link Edit page
      Given: Quick Link List page is opened
      When I click on "Add New"
      And I wait for the new page to load
      Then I should see the title, "Quick Link Edit"

    Scenario: I can save and submit a quick link entity
      Given: Quick Link edit page is opened
      When I fill out the 'Description' field
      And the Type field
      And the Url field
      And the Text field
      And the Tags field
      And the Link To New checkbox
      And the Active checkbox
      And I fill out the comment
      And I click 'Save and Submit' button
      Then I should be taken back to the Quick Link List page
      And The entry that I just saved should have the workflow state of "For Approval"

    Scenario: I can check that quick link entity data saved well
      Given: I have created a quick link entity by steps outlined above
      When I go to the Edit page of the entity
      And I check the data
      Then all the fields should match the input data

    Scenario: I can publish a quick link entity
      Given: I have saved and submitted a quick link entity
      When I go to the edit page of the entuty
      And I fill out the comment
      And I click on "Publish" button
      Then The workflow state should change to "Live"

    Scenario: I can change the content of the quick link entity
      Given: An entity already exists
      And I am on the edit page of the said entity
      When: I make changes to the entity - tags, url, etc
      And I fill out the comments section
      And I click on "Save" button
      Then The workflow state should change to "In Progress"

    Scenario: I can check that editing quick link entity saved well
      Given: I have edited a quick link entity by steps outlined above
      When I go to the Edit page of the entity
      And I check the data
      Then all the fields should match the input data

    Scenario:  I can revert a quick link entity
      Given: I have made the changes to the quick link entity
      When I click on "Revert to Live" button
      Then All changes should be reverted

    Scenario: I can set a quick link entity for deletion
      Given: I am on the Edit page of a quick link entity
      When I fill out the comment
      And I click on "Delete"
      Then I should be taken back to the Quick Link list page
      And The D column for the entity should have the letter Y
      And Workflow state should be set to "For Approval"

    Scenario: I can remove a qucik link entity
      Given: Quick link entity has been set up for deletion
      When I go to the edit page of the said entity
      And I input a comment
      And I click on "Publish" button
      Then The entity should be deleted
      And I should be taken back to the quick link list page
      And I should not see the particular entity that was deleted