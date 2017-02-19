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

@Named
public class TaskEventListener extends EventPostingEventListener {

	private RepositorySettingsService repositorySettingsService;

	@Inject
	public TaskEventListener(RepositorySettingsService repositorySettingsService) {
		super();
		this.repositorySettingsService = repositorySettingsService;
	}

	@EventListener
	public void postPullRequestEvent(TaskCreatedEvent event) {
		postEvent(event, settings -> settings.isTaskCreatedOn());
	}

	@EventListener
	public void postPullRequestEvent(TaskDeletedEvent event) {
		postEvent(event, settings -> settings.isTaskDeletedOn());
	}

	@EventListener
	public void postPullRequestEvent(TaskUpdatedEvent event) {
		postEvent(event, settings -> settings.isTaskUpdatedOn());
	}


	private void postEvent(TaskEvent event, RepositorySettingsChecker checker) {
		JsonNode eventJsonNode = convertToJsonNode(event);
		int repositoryId = eventJsonNode.path("task").path("context").path("fromRef").path("repository").path("id").asInt();
		RepositorySettings repositorySettings = repositorySettingsService.getSettings(repositoryId);

		if (StringUtils.hasText(repositorySettings.getWebhook()) && checker.isEventNotificationOn(repositorySettings)) {
			postEvent(eventJsonNode, repositorySettings.getWebhook());
		}
	}


}
