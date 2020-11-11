@integration @demo
Feature: demo sequence
    Launch a rocket

    Background: create all the modules
        Given Elon create 2 boosters in the booster micro-service
        And Gwynne create a new rocket in the rocket micro-service
        And Gwynne create a new payload in the payload micro-service
        And Richard add a new mission in the mission preparation micro-service

    Scenario: Execute a mission without any issue
        When Richard has the weather and rocket department not ready for launch
        Then Richard make a no go for the mission department
        When Tory from weather department see the weather is good
        Then Tory set the weather department to go to notify it is ready
        When Elon see the rocket is ready based on the Telemetry
        Then Elon set the rocket department to go to notify it is ready
        When Richard has the weather and rocket department ready for mission launch
        Then Richard make a go for the mission department in the mission preparation micro-service
        And Richard set the rocket ready to be launch
        Then Elon should see the rocket department is "READY_FOR_LAUNCH"
        And Also the Rocket is "READY_FOR_LAUNCH"
        And The Rocket Step should be "PREPARATION"
        When Elon make the request for the launch order to begin the countdown from the rocket micro-service
        Then The rocket status should be starting in the rocket micro-service
        * The payload status should be "ON_MISSION" in the payload micro-service
        * The booster status should be "RUNNING" in the booster micro-service
        * The mission status should be "STARTED" in the mission control micro-service
        When The rocket sequence is starting
        Then After a short time, the main engine start
        * The rocket liftoff after a few second
        * The rocket arrive at Max Q
        * The rocket quit Max Q
        * The main engine cutoff
        * The rocket separate from the first stage
        * The booster is now landing
        * The Rocket should arrive soon at the objective location
        When Richard check the booster logs to see the booster landing process
        Then Richard is able to see the landing sequence in the logs
        And The payload should start to emit telemetry from orbite
