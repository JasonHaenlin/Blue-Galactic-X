@functional @status
Feature: rocket update status sequence
    Check rocket status changements when the rocket start and is destroyed. verify the payload and mission are updated correctly

    Background: Given a created mission and a payload
        Given Gwynne create a new rocket
        And Richard add a new mission
        And Gwynne create a new payload

    Scenario: Execute a mission with a rocket and a payload and should begin but end up destroyed
        When Elon make the request for the launch order
        Then the rocket status should be "IN_SERVICE"
        Then the payload status should be "ON_MISSION"
        Then the mission status should be "STARTED"
        When Richard send a destruction order to the rocket
        Then the rocket status should be "DESTROYED"
        Then the payload status should be "DESTROYED"
        Then the mission status should be "FAILED"
