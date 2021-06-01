Feature: Ex1

Background:
    Given Web page is opened
    And User is logged in as "Roman" with password "Jdi1234"

Scenario: Test browser title
    Then Browser has a correct title

Scenario: Test user logging
    Then Username is "ROMAN IOVLEV"

Scenario: Test page header
    Then There are 4 items in the header
    And Header items have correct names

Scenario: Test benefit images on the Home Page
    When Focus is on the Home Page
    Then There are 4 benefit images
    And Benefit images has correct captions
    And Home page has correct header
    And Home page has correct text

Scenario: Test central Iframe
    When Focus is on the Home Page
    Then Central iframe is displayed
    And EPAM logo is displayed
    And Focus is switched to the Home Page

Scenario: Test sub header of the Home Page
    When Focus is on the Home Page
    Then There is correct text in a sub header
    And Sub header has correct link

Scenario: Test side sections of the Home Page
    When Focus is on the Home Page
    Then Home Page has left section and footer