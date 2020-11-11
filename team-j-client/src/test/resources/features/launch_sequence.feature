@functional @launchsequence
Feature: Rocket launch sequence
    With all check approved, Elon can launch, stage, destroy any rocket.

    Background: Create a mission context
        Given Elon who added the boosters below
        And the rocket below
        And Richard who program the following mission

    Scenario: Follow the launch procedure
        When Richard set the rocket ready to be launched
        Then the rocket should be in preparation
        When Elon start the launch procedure
        Then the rocket should be starting
        * after a short time, the main engine start
        * the rocket liftoff after a few second
        * the rocket arrive at Max Q
        * the rocket quit Max Q
        * the main engine cutoff
        * we separate from the stage
        And the booster is now landing
        Then we wait for the rocket to arrive
        When we check the mission logs
        Then we are able to see the landing sequence in the logs
