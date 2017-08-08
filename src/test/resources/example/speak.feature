@all
Feature: Speak

  @speak
  Scenario Outline: Speak on the screen
    Given I want to speak <Word>
    Then <Word> will be show

    Examples:
      | Word |
      | Muh  |
      | Di   |