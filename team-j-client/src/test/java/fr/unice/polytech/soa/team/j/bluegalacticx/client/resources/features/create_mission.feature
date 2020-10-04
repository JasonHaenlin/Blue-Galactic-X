Feature: Create a new mission

    Background: Init connexion with other department
        Given a handshake with all department
        And rocket department create a new report
        And weather department create a new report

    Scenario: Send a GO request when the mission is valid
        When I add a new mission
        And the weather report is valid
        And the rocket report is valid
        Then I can make a GO request
        Then Elon makes a launch request to rocket service
        Then the launch order to the rocket is triggered if the rocket is ready to launch.
        