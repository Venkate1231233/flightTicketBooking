@smoke @regression
Feature: Flight search on Momondo

  @smoke
  Scenario Outline: Search for flights and verify the cheapest price
    Given I am on the Momondo homepage
    When I search for flights from "<startLocation>" to "<endLocation>"
    Then I should see the cheapest flight price

    Examples:
      | startLocation | endLocation |
      | Tampa         | Detroit     |
      | Chicago       | Detroit     |
      | Los Angeles   | Dallas      |

  @regression
  Scenario Outline: Search for flights and verify the longest roundtrip duration
    Given I am on the Momondo homepage
    When I search for flights from "<startLocation>" to "<endLocation>"
    Then I should see the longest roundtrip duration with outbound and inbound details for "<startLocation>" to "<endLocation>"

   Examples:
      | startLocation | endLocation |
      | Tampa         | Detroit     |
      | Chicago       | Detroit     |
      | Los Angeles   | Dallas      |