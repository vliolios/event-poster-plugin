package com.vliolios.eventposter;

import com.atlassian.bitbucket.comment.Comment;
import com.atlassian.bitbucket.event.ApplicationEvent;
import com.atlassian.bitbucket.event.pull.PullRequestEvent;
import com.atlassian.bitbucket.pull.PullRequestParticipant;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.watcher.Watcher;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class EventListener {

	private final PluginSettings pluginSettings;

	EventListener(PluginSettingsFactory pluginSettingsFactory) {
		this.pluginSettings = pluginSettingsFactory.createSettingsForKey("com.vliolios.event-poster-plugin");
	}

	<T extends ApplicationEvent> void postEvent(ApplicationEvent applicationEvent, Repository repository, Class<T> clazz) {
		String eventJson = convertToJsonString(applicationEvent);
		HttpEntity<String> entity = createEntityWithHeaders(eventJson);
		doPost(repository, entity);
	}

	void postEvent(JsonNode eventJsonNode, Repository repository) {
		String eventJson = convertToJsonString(eventJsonNode);
		HttpEntity<String> entity = createEntityWithHeaders(eventJson);
		doPost(repository, entity);
	}

	private <T extends ApplicationEvent> String convertToJsonString(T applicationEvent) {
		T event = (T) applicationEvent;
		try {
			SimpleBeanPropertyFilter eventFilter = SimpleBeanPropertyFilter.serializeAllExcept("source");
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.serializeAllExcept("backingCrowdUser");
			SimpleBeanPropertyFilter watcherFilter = SimpleBeanPropertyFilter.serializeAllExcept("watchable");
			SimpleBeanPropertyFilter participantFilter = SimpleBeanPropertyFilter.serializeAllExcept("pullRequest", "entity");
			SimpleBeanPropertyFilter commentFilter = SimpleBeanPropertyFilter.serializeAllExcept("root", "parent", "comments", "tasks");
			FilterProvider filters = new SimpleFilterProvider()
					.addFilter("eventFilter", eventFilter)
					.addFilter("userFilter", userFilter)
					.addFilter("watcherFilter", watcherFilter)
					.addFilter("participantFilter", participantFilter)
					.addFilter("commentFilter", commentFilter);


			return new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
					.addMixIn(PullRequestEvent.class, PullRequestEventMixIn.class)
					.addMixIn(ApplicationUser.class, ApplicationUserMixIn.class)
					.addMixIn(Watcher.class, WatcherMixIn.class)
					.addMixIn(PullRequestParticipant.class, PullRequestParticipantMixIn.class)
					.addMixIn(Comment.class, CommentMixIn.class)
					.writer(filters)
					.writeValueAsString(event);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private  String convertToJsonString(JsonNode jsonNode) {
		try {
			return new ObjectMapper().writeValueAsString(jsonNode);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	<T extends ApplicationEvent> JsonNode convertToJsonNode(T applicationEvent) {
		T event = (T) applicationEvent;
		try {
			return new ObjectMapper().readTree(convertToJsonString(event));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void doPost(Repository repository, HttpEntity<String> entity) {
		String webhook = ((Map<String, String>) pluginSettings.get(Integer.toString(repository.getId()))).get("webhook");
		if (StringUtils.hasText(webhook)) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity(webhook, entity, String.class, new HashMap<String, String>());
		}
	}

	private HttpEntity<String> createEntityWithHeaders(String eventJson) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(eventJson, headers);
	}

	@JsonFilter("eventFilter")
	private class PullRequestEventMixIn {}

	@JsonFilter("userFilter")
	private class ApplicationUserMixIn {}

	@JsonFilter("watcherFilter")
	private class WatcherMixIn {}

	@JsonFilter("participantFilter")
	private class PullRequestParticipantMixIn {}

	@JsonFilter("commentFilter")
	private class CommentMixIn {}
}
