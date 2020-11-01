@functional
Feature: manage multiple rocket and monitor them
    With all check approved, Elon can launch, stage, destroy any rocket.

    Background: Creates multiple rockets
        Given Elon who added the rockets below :
            | 1 | 50,50,50 | 1 |
            | 2 | 40,40,40 | 2 |
    #     | 3 | 15,15,15 | 3 |
    #     | 4 | 5,5,5    | 4 |
    # And the boosters below :
    #     | 1 | 100 |
    #     | 2 | 80  |
    #     | 3 | 50  |
    #     | 4 | 30  |
    #     | 5 | 10  |

    Scenario: launch multiple rocket in space
        Given Elon get the authorisation to launch the rocket "1"
        And Elon get the authorisation to launch the rocket "2"
        When Elon launches the rocket "1"
        When Elon launches the rocket "2"
        And 10 time unit passed
        Then the rocket "1" is "FLOATING_IN_SPACE"
        Then the rocket "2" is "IN_SERVICE"


# ROCKET
# private String id;
# private RocketMetrics metrics;
# private RocketReport report;
# private RocketStatus status;
# private SpaceCoordinate objective;
# private String boosterId;
# private RocketApi rocketApi;

# BOOSTERS
# private String id;
# private BoosterStatus status;
# private BoosterLandingStep landingStep;
# private BoosterMetrics metrics;