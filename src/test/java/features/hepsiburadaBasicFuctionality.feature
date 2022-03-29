Feature: hepsiburada.com demo

  @run
  Scenario: Adding a product to basket

    Given a web browser is at the home page
    And hover moda under navigation bar
    And scroll until find women watch
    And click women watch title
    And scroll until find guess brand
    And click guess brand to filter
    And click third product from list
    And go to new tab
    And check product on product page
    And click add basket button
    And click basket button
    Then check product on basket page

