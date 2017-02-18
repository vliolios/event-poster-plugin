package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.task.TaskCreatedEvent;
import com.atlassian.bitbucket.event.task.TaskDeletedEvent;
import com.atlassian.bitbucket.event.task.TaskEvent;
import com.atlassian.bitbucket.event.task.TaskUpdatedEvent;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.fasterxml.jackson.databind.JsonNode;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TaskEventListener extends EventListener {

	private final RepositoryService repositoryService;

	@Inject
	public TaskEventListener(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
	                         @ComponentImport RepositoryService repositoryService) {
		super(pluginSettingsFactory);
		this.repositoryService = repositoryService;
	}

	@com.atlassian.event.api.EventListener
	public void postPullRequestEvent(TaskCreatedEvent event) {
		postEvent(event, TaskCreatedEvent.class);
	}

	@com.atlassian.event.api.EventListener
	public void postPullRequestEvent(TaskDeletedEvent event) {
		postEvent(event, TaskDeletedEvent.class);
	}

	@com.atlassian.event.api.EventListener
	public void postPullRequestEvent(TaskUpdatedEvent event) {
		postEvent(event, TaskUpdatedEvent.class);
	}

	private <T extends TaskEvent> void postEvent(TaskEvent event, Class<T> clazz) {
		JsonNode eventJsonNode = convertToJsonNode(event);
		int repositoryId = eventJsonNode.path("task").path("context").path("fromRef").path("repository").path("id").asInt();
		Repository repository = repositoryService.getById(repositoryId);

		if (repository != null) {
			postEvent(eventJsonNode, repository);
		}
	}
}
