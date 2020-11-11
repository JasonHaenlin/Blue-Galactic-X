@functional @gonogo
Feature: Department go nogo sequence
    Check the process of the go nogo

    Background: Given a mission and a rocket
        Given Gwynne create a rocket
        And Richard add a mission

    Scenario: Execute the go nogo process
        When Richard has the weather and rocket department not ready
        Then Richard make a no go for the mission department with id
        When Tory see the weather is good
        Then Tory set the weather department to go
        When Elon see the rocket is ready
        Then Elon set the rocket department to go with id
        When Richard has the weather and rocket department ready for mission
        Then Richard make a go for the mission department
        Then Elon should see the corresponding validation on the rocket department
