Feature: All the process to cover first scenario

    Background: Init connexion
        Given a handshake with all services

    Scenario: Launch rocket
        When Tory makes a request to the weather service for weather forecast 
        And the weather service's response is SUNNY
        And  Elon makes a request to the rocket service to get the rocket status
        Then the weather and the rocket status is ok
        Then Richard makes a request to the rocket service that everything is ok
        Then Elon makes a launch request to rocket service
        Then the launch order to the rocket is triggered if the rocket is ready to launch.
        