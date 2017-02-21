package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.ApplicationEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class EventPosterTest {

	private final RestTemplate restTemplate = mock(RestTemplate.class);
	private final EventToJsonConverter eventToJsonConverter = mock(EventToJsonConverter.class);

	private EventPoster eventPoster;

	@Before
	public void setUp() {
		eventPoster = new EventPoster(eventToJsonConverter, restTemplate);
	}

	@Test
	public void testPostApplicationEvent() {
		ApplicationEvent applicationEvent = mock(ApplicationEvent.class);
		when(eventToJsonConverter.convertToJsonString(applicationEvent)).thenReturn("\"id\": 1");

		eventPoster.postEvent(applicationEvent, "http://localhost:8080/abc");

		ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate, times(1)).postForEntity(eq("http://localhost:8080/abc"), httpEntityArgumentCaptor.capture(), eq(String.class), eq(new HashMap<>()));
		HttpEntity<String> httpEntity = httpEntityArgumentCaptor.getValue();
		assertThat(httpEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON));
		assertThat(httpEntity.getBody(), equalTo("\"id\": 1"));
	}

	@Test
	public void testPostJsonNodeEvent() {
		JsonNode jsonNodeEvent = mock(JsonNode.class);
		when(eventToJsonConverter.convertToJsonString(jsonNodeEvent)).thenReturn("\"id\": 1");

		eventPoster.postEvent(jsonNodeEvent, "http://localhost:8080/abc");

		ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate, times(1)).postForEntity(eq("http://localhost:8080/abc"), httpEntityArgumentCaptor.capture(), eq(String.class), eq(new HashMap<>()));
		HttpEntity<String> httpEntity = httpEntityArgumentCaptor.getValue();
		assertThat(httpEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON));
		assertThat(httpEntity.getBody(), equalTo("\"id\": 1"));
	}
}
