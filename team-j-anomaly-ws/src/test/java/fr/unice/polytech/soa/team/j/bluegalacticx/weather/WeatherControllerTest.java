package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.kafka.DepartmentStatusProducer;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { AnomalyController.class, WeatherService.class, DepartmentStatusProducer.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-weather") })
@TestMethodOrder(OrderAnnotation.class)
class WeatherControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
    private DepartmentStatusProducer departmentStatusProducer;

	@Test
	@Order(1)
	public void getWeatherStatusShouldBeOkTest() throws Exception {
		mvc.perform(get("/weather").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(8)));
	}

}
