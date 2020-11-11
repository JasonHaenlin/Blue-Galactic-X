@functional @telemetry
Feature: Write and read telemetry sequence
    Verify telemetry receive correctly data from the topics and that we can write and read them

    Background: Given a kafka producer for the telemetry topics
        Given a producer to send event from booster "teamj.telemetry-booster.0"
        And a producer to send event from payload "teamj.telemetry-payload.0"
        And a producer to send event from rocket "teamj.telemetry-rocket.0"

    Scenario: Execute event on kafka topics
        When the booster topic prepare a telemetry with id "55"
        Then I should be able to write it to booster topic
        And read the booster data and check id "55" with 1 measure
        When the rocket topic prepare a telemetry with id "55"
        Then I should be able to write it 5 times to rocket topic
        And read the rocket data and check id "55" with 5 measures
        When the payload topic prepare a telemetry with id "42"
        Then I should be able to write it 2 times to payload topic
        When the payload topic prepare a telemetry with id "45"
        Then I should be able to write it to payload topic
        And read the payload data from id "42" with 2 measure
        And read the payload data from id "45" with 1 measure
