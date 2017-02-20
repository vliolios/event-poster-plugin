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
				.webhook(settingsMap.get("webhook"))
				.pullRequestReviewersUpdatedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestReviewersUpdatedOn")))
				.pullRequestUpdatedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestUpdatedOn")))
				.pullRequestReopenedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestReopenedOn")))
				.pullRequestRescopedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestRescopedOn")))
				.pullRequestParticipantStatusUpdatedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestParticipantStatusUpdatedOn")))
				.pullRequestMergedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestMergedOn")))
				.pullRequestOpenedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestOpenedOn")))
				.pullRequestDeclinedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestDeclinedOn")))
				.pullRequestCommentAddedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestCommentAddedOn")))
				.pullRequestCommentDeletedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestCommentDeletedOn")))
				.pullRequestCommentEditedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestCommentEditedOn")))
				.pullRequestCommentRepliedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestCommentRepliedOn")))
				.pullRequestCommitCommentAddedOn(BooleanUtils.toBoolean(settingsMap.get("pullRequestCommitCommentAddedOn")))
				.taskCreatedOn(BooleanUtils.toBoolean(settingsMap.get("taskCreatedOn")))
				.taskDeletedOn(BooleanUtils.toBoolean(settingsMap.get("taskDeletedOn")))
				.taskUpdatedOn(BooleanUtils.toBoolean(settingsMap.get("taskUpdatedOn")))
				.build();
		return repositorySettings;
	}

	private Map<String, String> settingsToMap(RepositorySettings settings) {
		Map<String, String> settingsMap = ImmutableMap.<String, String>builder().put("webhook", settings.getWebhook())
				.put("pullRequestReviewersUpdatedOn", String.valueOf(settings.isPullRequestReviewersUpdatedOn()))
				.put("pullRequestUpdatedOn", String.valueOf(settings.isPullRequestUpdatedOn()))
				.put("pullRequestReopenedOn", String.valueOf(settings.isPullRequestReopenedOn()))
				.put("pullRequestRescopedOn", String.valueOf(settings.isPullRequestRescopedOn()))
				.put("pullRequestParticipantStatusUpdatedOn", String.valueOf(settings.isPullRequestParticipantStatusUpdatedOn()))
				.put("pullRequestMergedOn", String.valueOf(settings.isPullRequestMergedOn()))
				.put("pullRequestOpenedOn", String.valueOf(settings.isPullRequestOpenedOn()))
				.put("pullRequestDeclinedOn", String.valueOf(settings.isPullRequestDeclinedOn()))
				.put("pullRequestCommentAddedOn", String.valueOf(settings.isPullRequestCommentAddedOn()))
				.put("pullRequestCommentDeletedOn", String.valueOf(settings.isPullRequestCommentDeletedOn()))
				.put("pullRequestCommentEditedOn", String.valueOf(settings.isPullRequestCommentEditedOn()))
				.put("pullRequestCommentRepliedOn", String.valueOf(settings.isPullRequestCommentRepliedOn()))
				.put("pullRequestCommitCommentAddedOn", String.valueOf(settings.isPullRequestCommitCommentAddedOn()))
				.put("taskCreatedOn", String.valueOf(settings.isTaskCreatedOn()))
				.put("taskDeletedOn", String.valueOf(settings.isTaskDeletedOn()))
				.put("taskUpdatedOn", String.valueOf(settings.isTaskUpdatedOn()))
				.build();

		return settingsMap;
	}
}
