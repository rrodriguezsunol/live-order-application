Feature: Live Order Board

  Scenario: Register an order
    Given There are no orders on the board
    When I register a sell order of 3.5Kg for £303
    Then The live order board displays such order

  Scenario: Cancel an order
    Given I register a sell order of 3.5Kg for £303
    When I cancel the registered order
    Then The live order board does not display the order anymore