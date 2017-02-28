Feature: This feature tests the Zoo Adoption page

  Scenario: Adopt a Turtle
    Given Im in the ZOO Adoption homepage
    When I navigate to the Adoption page
    And Check Availability of "Turtle"
    Then Enter the Details
    And Submit the Form
