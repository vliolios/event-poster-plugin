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

@Named
class EventPoster {

	private final EventToJsonConverter eventToJsonConverter;

	@Inject
	public EventPoster(EventToJsonConverter eventToJsonConverter) {
		this.eventToJsonConverter = eventToJsonConverter;
	}

	void postEvent(ApplicationEvent applicationEvent, String destinationURL) {
		String eventJson = eventToJsonConverter.convertToJsonString(applicationEvent);
		HttpEntity<String> entity = createEntityWithHeaders(eventJson);
		doPost(entity, destinationURL);
	}

	void postEvent(JsonNode eventJsonNode, String destinationURL) {
		String eventJson = eventToJsonConverter.convertToJsonString(eventJsonNode);
		HttpEntity<String> entity = createEntityWithHeaders(eventJson);
		doPost(entity, destinationURL);
	}



	private void doPost(HttpEntity<String> entity, String destinationURL) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(destinationURL, entity, String.class, new HashMap<String, String>());
	}

	private HttpEntity<String> createEntityWithHeaders(String eventJson) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(eventJson, headers);
	}
}
