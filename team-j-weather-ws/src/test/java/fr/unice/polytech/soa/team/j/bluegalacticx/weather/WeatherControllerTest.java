package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherType;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.utils.JsonUtil;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { WeatherController.class, WeatherService.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-weather") })
@TestMethodOrder(OrderAnnotation.class)
class WeatherControllerTest {

	private static final String WEATHER_OVERALL = "Good weather for the end of the afternoon";

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	public void getWeatherStatusShouldBeOkTest() throws Exception {
		mvc.perform(get("/weather").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.beacons", hasSize(4)));
	}

	@Test
	@Order(2)
	public void getWeatherNoReportShouldBeNotFoundTest() throws Exception {
		mvc.perform(get("/weather/report").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	public void postWeatherReportShouldBeOKTest() throws Exception {
		mvc.perform(
				post("/weather/report").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(buildReport())))
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void getWeatherReportShouldBeFoundTest() throws Exception {
		mvc.perform(get("/weather/report").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.overallResult", is(WEATHER_OVERALL)));
	}

	private WeatherReport buildReport() {
		return new WeatherReport().avgRainfall(50).avgHumidity(10).avgWind(20).avgVisibility(90).avgTemperature(25)
				.weatherType(WeatherType.SUNNY).overallResult(WEATHER_OVERALL);
	}

}
