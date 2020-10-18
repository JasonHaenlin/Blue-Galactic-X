Feature: Create a new mission

    Background: Init connexion with other department
        Given a handshake with all department
        And Elon from the rocket department check the rocket metrics and create a report for Richard
        And Tory from the weather department check the weather metrics and create a report for Richard
        And Gwynne from the payload department create a new payload

    Scenario: Execute a new mission and deliver the payload to the targeted location
        When Richard add a new mission
        And the weather report is valid
        And the rocket report is valid
        Then Richard can make a GO request
        Then Elon makes a launch request to rocket service
        Then the launch order to the rocket is triggered if the rocket is ready to launch
        When rocket first stage is empty in fuel
        Then Elon split the rocket
        And Jeff can consult the telemetry data
        And Jeff can inform that there is no anomaly
        When the payload is on the destination point
        Then the mission is succesfull
        And the booster first stage landed correctly
