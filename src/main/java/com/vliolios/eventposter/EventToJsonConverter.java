package com.vliolios.eventposter;

import com.atlassian.bitbucket.comment.Comment;
import com.atlassian.bitbucket.event.ApplicationEvent;
import com.atlassian.bitbucket.event.pull.PullRequestEvent;
import com.atlassian.bitbucket.pull.PullRequestParticipant;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.watcher.Watcher;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import javax.inject.Named;
import java.io.IOException;

@Named
public class EventToJsonConverter {

	String convertToJsonString(ApplicationEvent event) {
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


			return getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
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

	String convertToJsonString(JsonNode jsonNode) {
		try {
			return getObjectMapper().writeValueAsString(jsonNode);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	JsonNode convertToJsonNode(ApplicationEvent event) {
		try {
			return getObjectMapper().readTree(convertToJsonString(event));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	@JsonFilter("eventFilter")
	interface PullRequestEventMixIn {}

	@JsonFilter("userFilter")
	interface ApplicationUserMixIn {}

	@JsonFilter("watcherFilter")
	interface WatcherMixIn {}

	@JsonFilter("participantFilter")
	interface PullRequestParticipantMixIn {}

	@JsonFilter("commentFilter")
	interface CommentMixIn {}
}
