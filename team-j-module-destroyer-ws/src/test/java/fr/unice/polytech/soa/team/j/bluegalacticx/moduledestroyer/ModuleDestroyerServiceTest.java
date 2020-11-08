package fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.kafka.producers.ModuleDestructionProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest.ModuleType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModuleDestroyerServiceTest {

    @Captor
    ArgumentCaptor<ModuleType> moduleTypeCaptor;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Mock
    ModuleDestructionProducer moduleDestructionProducer;

    @InjectMocks
    ModuleDestroyerService moduleDestroyerService;

    @Test
    void shouldDestroyRocketTest() throws InterruptedException {
        AnomalyRequest req = AnomalyRequest.newBuilder().setAnomalyType(AnomalyRequest.AnomalyType.AWAY_FROM_TRAJECTORY)
                .setModuleId("0").setModuleType(AnomalyRequest.ModuleType.ROCKET).build();
        moduleDestroyerService.destroyModule(req);

        Mockito.verify(moduleDestructionProducer).notifyModuleEmergencyDestruction(stringCaptor.capture(),
                moduleTypeCaptor.capture());

        assertEquals(ModuleType.ROCKET, moduleTypeCaptor.getValue());
        assertEquals("0", stringCaptor.getValue());
    }

    @Test
    void shouldNotDestroyRocketTest() {
        AnomalyRequest req = AnomalyRequest.newBuilder().setAnomalyType(AnomalyRequest.AnomalyType.OVERHEATING)
                .setModuleId("0").setModuleType(AnomalyRequest.ModuleType.ROCKET).build();
        moduleDestroyerService.destroyModule(req);

        verify(moduleDestructionProducer, never()).notifyModuleEmergencyDestruction(any(), any());
    }
}
