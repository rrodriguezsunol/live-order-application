Feature: Live Order Board

  Scenario: Register an order
    Given there are no orders on the board
    When I register order order_1
    Then the live order board displays such order