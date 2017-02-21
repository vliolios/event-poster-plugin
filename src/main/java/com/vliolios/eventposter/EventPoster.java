package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.ApplicationEvent;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.function.Function;

@Named
class EventPoster {

	private final EventToJsonConverter eventToJsonConverter;
	private final RestTemplate restTemplate;

	@Inject
	public EventPoster(EventToJsonConverter eventToJsonConverter, RestTemplate restTemplate) {
		this.eventToJsonConverter = eventToJsonConverter;
		this.restTemplate = restTemplate;
	}

	void postEvent(ApplicationEvent applicationEvent, String destinationURL) {
		doPost(destinationURL, converter -> converter.convertToJsonString(applicationEvent));
	}

	void postEvent(JsonNode eventJsonNode, String destinationURL) {
		doPost(destinationURL, converter -> converter.convertToJsonString(eventJsonNode));
	}

	private void doPost(String destinationURL, Function<EventToJsonConverter, String> eventToJsonConversion) {
		String eventJson = eventToJsonConversion.apply(eventToJsonConverter);
		HttpEntity<String> entity = createEntityWithHeaders(eventJson);
		restTemplate.postForEntity(destinationURL, entity, String.class, new HashMap<String, String>());
	}

	private HttpEntity<String> createEntityWithHeaders(String eventJson) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(eventJson, headers);
	}
}
