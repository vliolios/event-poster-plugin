package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.task.TaskCreatedEvent;
import com.atlassian.bitbucket.event.task.TaskDeletedEvent;
import com.atlassian.bitbucket.event.task.TaskEvent;
import com.atlassian.bitbucket.event.task.TaskUpdatedEvent;
import com.atlassian.event.api.EventListener;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Predicate;

@Named
public class TaskEventListener {

	private final RepositorySettingsService repositorySettingsService;
	private final EventPoster eventPoster;
	private final EventToJsonConverter eventToJsonConverter;

	@Inject
	public TaskEventListener(RepositorySettingsService repositorySettingsService, EventPoster eventPoster, EventToJsonConverter eventToJsonConverter) {
		this.repositorySettingsService = repositorySettingsService;
		this.eventPoster = eventPoster;
		this.eventToJsonConverter = eventToJsonConverter;
	}

	@EventListener
	public void postTaskEvent(TaskCreatedEvent event) {
		postEvent(event, RepositorySettings::isTaskCreatedOn);
	}

	@EventListener
	public void postTaskEvent(TaskDeletedEvent event) {
		postEvent(event, RepositorySettings::isTaskDeletedOn);
	}

	@EventListener
	public void postTaskEvent(TaskUpdatedEvent event) {
		postEvent(event, RepositorySettings::isTaskUpdatedOn);
	}


	private void postEvent(TaskEvent event, Predicate<RepositorySettings> repositorySettingsChecker) {
		JsonNode eventJsonNode = eventToJsonConverter.convertToJsonNode(event);
		int repositoryId = eventJsonNode.path("task").path("context").path("fromRef").path("repository").path("id").asInt();
		RepositorySettings repositorySettings = repositorySettingsService.getSettings(repositoryId);

		if (repositorySettings != null && StringUtils.hasText(repositorySettings.getWebhook()) && repositorySettingsChecker.test(repositorySettings)) {
			eventPoster.postEvent(eventJsonNode, repositorySettings.getWebhook());
		}
	}


}
