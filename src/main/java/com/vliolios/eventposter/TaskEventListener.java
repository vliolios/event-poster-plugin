package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.task.TaskCreatedEvent;
import com.atlassian.bitbucket.event.task.TaskDeletedEvent;
import com.atlassian.bitbucket.event.task.TaskEvent;
import com.atlassian.bitbucket.event.task.TaskUpdatedEvent;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.fasterxml.jackson.databind.JsonNode;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TaskEventListener extends SettingsAwareEventListener {

	private final RepositoryService repositoryService;

	@Inject
	public TaskEventListener(RepositorySettingsService repositorySettingsService, @ComponentImport RepositoryService repositoryService) {
		super(repositorySettingsService);
		this.repositoryService = repositoryService;
	}

	@EventListener
	public void postPullRequestEvent(TaskCreatedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(TaskDeletedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(TaskUpdatedEvent event) {
		postEvent(event);
	}

	private <T extends TaskEvent> void postEvent(TaskEvent event) {
		JsonNode eventJsonNode = convertToJsonNode(event);
		int repositoryId = eventJsonNode.path("task").path("context").path("fromRef").path("repository").path("id").asInt();
		Repository repository = repositoryService.getById(repositoryId);

		if (repository != null) {
			postEvent(eventJsonNode, repository);
		}
	}
}
