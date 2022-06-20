Feature: Testing different request on the booking

  Scenario: I get all bookings from application
    Given I am on homepage of application of booking
    When I send Get request to list endpoint of booking
    Then I must get back a valid status code 200 of booking

  Scenario: I create booking from application
    Given I am on homepage of application of booking
    When I send Post request to list endpoint of booking
    Then I must get back a valid status code 200 of booking

  Scenario: I update booking from application
    Given I am on homepage of application of booking
    When I send Put request to list endpoint of booking
    Then I must get back a valid status code 200 of booking

  Scenario: I delete booking from application
    Given I am on homepage of application of booking
    When I send Delete request to list endpoint of booking
    Then I must get back a valid status code 201 of booking
    And I validate if booking is deleted

