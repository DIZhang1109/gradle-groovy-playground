@sftp
Feature: SFTP example
  As a user
  I want to interact with SFTP service
  so as to know the SFTP server is okay

  Background:
    Given there is a Sample SFTP service

  Scenario Outline: Verify <Name> sftp server is running okay
    Given a <Name> SFTP server
    When I SSH login to the server
    And navigate to <Destination Directory>
    Then I should know the amount of files is <File Amount>

    Examples:
      | Name   | Destination Directory | File Amount |
      | Sample | /*.*                  | 1           |
      | Sample | /pub//example/*.*     | 19          |
