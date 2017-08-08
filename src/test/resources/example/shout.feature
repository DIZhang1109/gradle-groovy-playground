@all
Feature: Shout

  @shout
  Scenario Outline: Shout on the screen
    Given I want to shout <Word>
    Then <Word> will be louder

    Examples:
      | Word |
      | Hi   |
      | Yeah |