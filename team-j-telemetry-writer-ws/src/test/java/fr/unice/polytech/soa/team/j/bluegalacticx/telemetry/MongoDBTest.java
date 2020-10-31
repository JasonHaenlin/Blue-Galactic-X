package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryBoosterDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryBoosterDataRepository;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoDBTest {

    @MockBean
    private TelemetryService telemetryService;
    private TelemetryRocketData rocketData;
    private TelemetryBoosterData boosterData;

    @BeforeEach
    public void setup() {
        rocketData = new TelemetryRocketData();
        rocketData.setRocketId("125");
        rocketData.heatShield(50);
        boosterData = new TelemetryBoosterData();
        boosterData.boosterId("10").rocketID("125").fuel(50);
    }

    @Test
    public void testSavingBoosterData(@Autowired MongoTemplate mongoTemplate) throws TelemetryDataBoosterIdException {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                mongoTemplate.save(boosterData, "boosterTelemetry");
                return null;
            }
        }).when(telemetryService).createBoosterData(boosterData);
        telemetryService.createBoosterData(boosterData);
        assertEquals(true, mongoTemplate.findAll(TelemetryBoosterData.class, "boosterTelemetry").size() != 0);
    }

    @Test
    public void testSavingRocketData(@Autowired MongoTemplate mongoTemplate) throws TelemetryDataRocketIdException {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                mongoTemplate.save(rocketData, "rocketTelemetry");
                return null;
            }
        }).when(telemetryService).createRocketData(rocketData);
        telemetryService.createRocketData(rocketData);
        assertEquals(true, mongoTemplate.findAll(TelemetryRocketData.class, "rocketTelemetry").size() != 0);
    }

    @Test
    public void testSavingBadBoosterData(@Autowired TelemetryBoosterDataRepository telemetryBoosterDataRepository)
            throws TelemetryDataBoosterIdException, NoTelemetryBoosterDataException {
        boosterData = new TelemetryBoosterData();
        boosterData.boosterId("10").fuel(50);
        try {
            telemetryService.createBoosterData(boosterData);
        } catch (Exception e) {
            assertEquals(true, e instanceof TelemetryDataBoosterIdException);
        }
    }

    @Test
    public void testSavingBadRocketData(@Autowired MongoTemplate mongoTemplate) {
        rocketData = new TelemetryRocketData().distance(0);
        try {
            telemetryService.createRocketData(rocketData);
        } catch (Exception e) {
            assertEquals(true, e instanceof TelemetryDataRocketIdException);
        }
    }
}
