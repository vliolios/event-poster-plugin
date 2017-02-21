package com.vliolios.eventposter;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.BooleanUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class RepositorySettingsService {


	private PluginSettings pluginSettings;

	@Inject
	public RepositorySettingsService(@ComponentImport PluginSettingsFactory pluginSettingsFactory) {
		this.pluginSettings = pluginSettingsFactory.createSettingsForKey("com.vliolios.event-poster-plugin");
	}

	public RepositorySettings getSettings(int repositoryId) {
		String repositoryIdString = Integer.toString(repositoryId);
		Map<String, String> settings = (Map<String, String>) pluginSettings.get(repositoryIdString);
		return mapToSettings(settings);
	}

	public RepositorySettings setSettings(int repositoryId, RepositorySettings repositorySettings) {
		pluginSettings.put(Integer.toString(repositoryId), settingsToMap(repositorySettings));
		return repositorySettings;
	}

	private RepositorySettings mapToSettings(Map<String, String> settingsMap) {
		if (settingsMap == null) {
			//return the default settings
			return new RepositorySettings.Builder().build();
		}

		RepositorySettings repositorySettings = new RepositorySettings.Builder()
				.webhook(settingsMap.get(RepositorySettings.Keys.WEBHOOK))
				.pullRequestReviewersUpdatedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_REVIEWERS_UPDATED_ON)))
				.pullRequestUpdatedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_UPDATED_ON)))
				.pullRequestReopenedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_REOPENED_ON)))
				.pullRequestRescopedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_RESCOPED_ON)))
				.pullRequestParticipantStatusUpdatedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_PARTICIPANT_STATUS_UPDATED_ON)))
				.pullRequestMergedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_MERGED_ON)))
				.pullRequestOpenedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_OPENED_ON)))
				.pullRequestDeclinedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_DECLINED_ON)))
				.pullRequestCommentAddedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_COMMENT_ADDED_ON)))
				.pullRequestCommentDeletedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_COMMENT_DELETED_ON)))
				.pullRequestCommentEditedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_COMMENT_EDITED_ON)))
				.pullRequestCommentRepliedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_COMMENT_REPLIED_ON)))
				.pullRequestCommitCommentAddedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.PULL_REQUEST_COMMIT_COMMENT_ADDED_ON)))
				.taskCreatedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.TASK_CREATED_ON)))
				.taskDeletedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.TASK_DELETED_ON)))
				.taskUpdatedOn(BooleanUtils.toBoolean(settingsMap.get(RepositorySettings.Keys.TASK_UPDATED_ON)))
				.build();
		return repositorySettings;
	}

	private Map<String, String> settingsToMap(RepositorySettings settings) {
		Map<String, String> settingsMap = ImmutableMap.<String, String>builder().put(RepositorySettings.Keys.WEBHOOK, settings.getWebhook())
				.put(RepositorySettings.Keys.PULL_REQUEST_REVIEWERS_UPDATED_ON, String.valueOf(settings.isPullRequestReviewersUpdatedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_UPDATED_ON, String.valueOf(settings.isPullRequestUpdatedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_REOPENED_ON, String.valueOf(settings.isPullRequestReopenedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_RESCOPED_ON, String.valueOf(settings.isPullRequestRescopedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_PARTICIPANT_STATUS_UPDATED_ON, String.valueOf(settings.isPullRequestParticipantStatusUpdatedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_MERGED_ON, String.valueOf(settings.isPullRequestMergedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_OPENED_ON, String.valueOf(settings.isPullRequestOpenedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_DECLINED_ON, String.valueOf(settings.isPullRequestDeclinedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_COMMENT_ADDED_ON, String.valueOf(settings.isPullRequestCommentAddedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_COMMENT_DELETED_ON, String.valueOf(settings.isPullRequestCommentDeletedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_COMMENT_EDITED_ON, String.valueOf(settings.isPullRequestCommentEditedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_COMMENT_REPLIED_ON, String.valueOf(settings.isPullRequestCommentRepliedOn()))
				.put(RepositorySettings.Keys.PULL_REQUEST_COMMIT_COMMENT_ADDED_ON, String.valueOf(settings.isPullRequestCommitCommentAddedOn()))
				.put(RepositorySettings.Keys.TASK_CREATED_ON, String.valueOf(settings.isTaskCreatedOn()))
				.put(RepositorySettings.Keys.TASK_DELETED_ON, String.valueOf(settings.isTaskDeletedOn()))
				.put(RepositorySettings.Keys.TASK_UPDATED_ON, String.valueOf(settings.isTaskUpdatedOn()))
				.build();

		return settingsMap;
	}
}
