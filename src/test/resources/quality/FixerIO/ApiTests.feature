@fixer
Feature: Testing Fixer API
  
  # Setting the API_KEY as a data-store available to any scenario below.
  Background:
    Given I add the following data-store "API_KEY" "89e502dd1619ca38ebf9eca1d79ae3c7"
  

  Scenario:  Get All currencies and store 10 in CSV

    # Checking that site is responsive.
    And I send a GET to URL "{Site}"
    And response code is "200"

    # Getting the latest currency rates.
    When I send a GET to URL "{Api}/latest?access_key={API_KEY}"

    # Getting 10 random currencies from the above response and storing them into a CSV.
    And I use response and save random 10 currencies in CSV file "currencies.csv"

    # Asserting that the CSV file has been created.
    Then the file "currencies.csv" exists
