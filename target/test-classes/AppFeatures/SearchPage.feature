Feature: NHS Jobs Search Functionality

  Background:
    Given I am on the NHS Jobs search page

@regression @positive
  Scenario: Jobseeker searches for jobs using preferences and sorts by newest date
    When I enter my preferences like "nurse" in keyword and "London" in location
    And I click on the search button
    Then I should see a list of jobs that match my preferences
    And the results should be sorted by the newest Date Posted

@regression @negative
  Scenario: Jobseeker enters invalid characters in keyword and location fields
    When I enter my preferences like "@#$%" in keyword and "" in location
    And I click on the search button
    Then I should see a message or no results indicating invalid search
    
    