Feature: Live Order Board

  Scenario: Register an order
    Given There are no orders on the board
    When 'user-1' registers a 'sell' order of 3.5Kg for £303
    Then The live order board displays such order

  Scenario: Cancel an order
    Given 'user-1' registers a 'sell' order of 3.5Kg for £303
    When I cancel the registered order
    Then The live order board does not display the order anymore

  Scenario: Display summary of sell orders merged by price and ordered by price ascending
    Given 'user-1' registers a 'sell' order of 3.5Kg for £303
    And 'user-3' registers a 'sell' order of 5Kg for £400
    And 'user-2' registers a 'sell' order of 6Kg for £303
    And 'user-1' registers a 'sell' order of 2Kg for £304
    When I ask for the summary of the 'sell' orders
    Then the live order board looks as follows:
      | 9.5 kg £303 |
      | 2.0 kg £304 |
      | 5.0 kg £400 |

  Scenario: Display summary of buy orders merged by price and ordered by price descending
    Given 'user-1' registers a 'buy' order of 3.5Kg for £303
    And 'user-1' registers a 'buy' order of 2Kg for £304
    And 'user-3' registers a 'buy' order of 5Kg for £400
    And 'user-2' registers a 'buy' order of 6Kg for £303
    When I ask for the summary of the 'buy' orders
    Then the live order board looks as follows:
      | 5.0 kg £400 |
      | 2.0 kg £304 |
      | 9.5 kg £303 |
