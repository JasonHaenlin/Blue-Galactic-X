Feature: Create a new mission

    Background: Init connexion with other department
        Given a handshake with all department
        And rocket department create a new report
        And weather department create a new report
        And payload department create a new payload

    Scenario: Send a GO request when the mission is valid
        When I add a new mission
        And the weather report is valid
        And the rocket report is valid
        Then I can make a GO request
        Then Elon makes a launch request to rocket service
        Then the launch order to the rocket is triggered if the rocket is ready to launch
        When rocket first stage is empty in fuel
        Then Elon split the rocket
        And Jeff can consult the telemetry data
        And Jeff can inform that there is no anomaly
        When the payload is on the destination point
        Then the mission is succesfull
