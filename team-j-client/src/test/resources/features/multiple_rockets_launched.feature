@functional @multipleRockets
Feature: Multiple rockets launched
    With all check approved, Elon can launch, stage, destroy any rocket.

    Background: Creates multiple rockets
        Given Elon who added the rockets below :
            | rocketId    | x  | y  | z  | boosterId    |
            | 1mmr_rocket | 50 | 50 | 50 | 1mmr_booster |
            | 2mmr_rocket | 40 | 40 | 40 | 2mmr_booster |
            | 3mmr_rocket | 15 | 15 | 15 | 3mmr_booster |
            | 4mmr_rocket | 5  | 5  | 5  | 4mmr_booster |
        And the boosters below :
            | boosterId    | fuel |
            | 1mmr_booster | 100  |
            | 2mmr_booster | 80   |
            | 3mmr_booster | 50   |
            | 4mmr_booster | 30   |
            | 5mmr_booster | 10   |
        And Richard programs the following missions :
            | missionId    | rocketId    |
            | 1mmr_mission | 1mmr_rocket |
            | 2mmr_mission | 2mmr_rocket |

    Scenario: launch multiple rocket in space
        Given Elon get the authorisation to launch the rocket "1mmr_rocket" for the mission "1mmr_mission"
        And Elon get the authorisation to launch the rocket "2mmr_rocket" for the mission "2mmr_mission"
        When Elon launches the rocket "1mmr_rocket"
        And Elon launches the rocket "2mmr_rocket"
        When 10 time unit passed
        Then the rocket "1mmr_rocket" is "FLOATING_IN_SPACE"
        Then the rocket "2mmr_rocket" is "IN_SERVICE"
