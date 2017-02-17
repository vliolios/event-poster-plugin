package com.vliolios.eventposter;

import com.atlassian.bitbucket.comment.Comment;
import com.atlassian.bitbucket.event.pull.*;
import com.atlassian.bitbucket.pull.PullRequestParticipant;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.watcher.Watcher;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Named
public class PullRequestEventListener {

	private static final Logger log = LoggerFactory.getLogger(PullRequestEventListener.class);

	private final PluginSettings pluginSettings;

	@Inject
	public PullRequestEventListener(@ComponentImport PluginSettingsFactory pluginSettingsFactory) {
		this.pluginSettings = pluginSettingsFactory.createSettingsForKey("com.vliolios.event-poster-plugin");
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReviewersUpdatedEvent event) {
		postEvent(event, PullRequestReviewersUpdatedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestUpdatedEvent event) {
		postEvent(event, PullRequestUpdatedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReopenedEvent event) {
		postEvent(event, PullRequestReopenedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestRescopedEvent event) {
		postEvent(event, PullRequestRescopedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestParticipantStatusUpdatedEvent event) {
		postEvent(event, PullRequestParticipantStatusUpdatedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestMergedEvent event) {
		postEvent(event, PullRequestMergedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestOpenedEvent event) {
		postEvent(event, PullRequestOpenedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestDeclinedEvent event) {
		postEvent(event, PullRequestDeclinedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentAddedEvent event) {
		postEvent(event, PullRequestCommentAddedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentDeletedEvent event) {
		postEvent(event, PullRequestCommentDeletedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentEditedEvent event) {
		postEvent(event, PullRequestCommentEditedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentRepliedEvent event) {
		postEvent(event, PullRequestCommentRepliedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommitCommentAddedEvent event) {
		postEvent(event, PullRequestCommitCommentAddedEvent.class);
	}

	private <T extends PullRequestEvent> void postEvent(PullRequestEvent pullRequestEvent, Class<T> clazz) {
		T event = (T) pullRequestEvent;
		Repository repository = event.getPullRequest().getToRef().getRepository();
		String webhook = ((Map<String, String>) pluginSettings.get(Integer.toString(repository.getId()))).get("webhook");

		if (StringUtils.hasText(webhook)) {
			RestTemplate restTemplate = new RestTemplate();
			try {
				SimpleBeanPropertyFilter eventFilter = SimpleBeanPropertyFilter.serializeAllExcept("source");
				SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.serializeAllExcept("backingCrowdUser");
				SimpleBeanPropertyFilter watcherFilter = SimpleBeanPropertyFilter.serializeAllExcept("watchable");
				SimpleBeanPropertyFilter participantFilter = SimpleBeanPropertyFilter.serializeAllExcept("pullRequest", "entity");
				SimpleBeanPropertyFilter commentFilter = SimpleBeanPropertyFilter.serializeAllExcept("root", "parent", "comments");
				FilterProvider filters = new SimpleFilterProvider()
						.addFilter("eventFilter", eventFilter)
						.addFilter("userFilter", userFilter)
						.addFilter("watcherFilter", watcherFilter)
						.addFilter("participantFilter", participantFilter)
						.addFilter("commentFilter", commentFilter);

				String eventJson = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
						.addMixIn(PullRequestEvent.class, PullRequestEventMixIn.class)
						.addMixIn(ApplicationUser.class, ApplicationUserMixIn.class)
						.addMixIn(Watcher.class, WatcherMixIn.class)
						.addMixIn(PullRequestParticipant.class, PullRequestParticipantMixIn.class)
						.addMixIn(Comment.class, CommentMixIn.class)
						.writer(filters)
						.writeValueAsString(event);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<>(eventJson, headers);

				restTemplate.postForEntity(webhook, entity, String.class, new HashMap<String, String>());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@JsonFilter("eventFilter")
	public class PullRequestEventMixIn {}

	@JsonFilter("userFilter")
	public class ApplicationUserMixIn {}

	@JsonFilter("watcherFilter")
	public class WatcherMixIn {}

	@JsonFilter("participantFilter")
	public class PullRequestParticipantMixIn {}

	@JsonFilter("commentFilter")
	public class CommentMixIn {}

}
