package com.vliolios.eventposter;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RepositorySettingsServiceTest {

	private final PluginSettingsFactory pluginSettingsFactory = mock(PluginSettingsFactory.class);
	private final PluginSettings pluginSettings = mock(PluginSettings.class);

	private RepositorySettingsService repositorySettingsService;

	@Before
	public void setUp() {
		when(pluginSettingsFactory.createSettingsForKey("com.vliolios.event-poster-plugin")).thenReturn(pluginSettings);
		repositorySettingsService = new RepositorySettingsService(pluginSettingsFactory);
	}

	@Test
	public void testGetSettings() {
		Map<String, String> settingsMap =  ImmutableMap.<String, String>builder().put("webhook", "http://localhost:8080/abc")
				.put("pullRequestReviewersUpdatedOn", "false")
				.put("pullRequestUpdatedOn", "false")
				.put("pullRequestReopenedOn", "false")
				.put("pullRequestRescopedOn", "false")
				.put("pullRequestParticipantStatusUpdatedOn", "false")
				.put("pullRequestMergedOn", "false")
				.put("pullRequestOpenedOn", "false")
				.put("pullRequestDeclinedOn", "false")
				.put("pullRequestCommentAddedOn", "false")
				.put("pullRequestCommentDeletedOn", "false")
				.put("pullRequestCommentEditedOn", "false")
				.put("pullRequestCommentRepliedOn", "false")
				.put("pullRequestCommitCommentAddedOn", "false")
				.put("taskCreatedOn", "false")
				.put("taskDeletedOn", "false")
				.put("taskUpdatedOn", "false")
				.build();;
		when(pluginSettings.get("1")).thenReturn(settingsMap);

		RepositorySettings settings = repositorySettingsService.getSettings(1);

		assertThat(settings.getWebhook(), equalTo("http://localhost:8080/abc"));
		assertThat(settings.isPullRequestReviewersUpdatedOn(), equalTo(false));
		assertThat(settings.isPullRequestUpdatedOn(), equalTo(false));
		assertThat(settings.isPullRequestReopenedOn(), equalTo(false));
		assertThat(settings.isPullRequestRescopedOn(), equalTo(false));
		assertThat(settings.isPullRequestParticipantStatusUpdatedOn(), equalTo(false));
		assertThat(settings.isPullRequestMergedOn(), equalTo(false));
		assertThat(settings.isPullRequestOpenedOn(), equalTo(false));
		assertThat(settings.isPullRequestDeclinedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentAddedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentDeletedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentEditedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentRepliedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommitCommentAddedOn(), equalTo(false));
		assertThat(settings.isTaskCreatedOn(), equalTo(false));
		assertThat(settings.isTaskDeletedOn(), equalTo(false));
		assertThat(settings.isTaskUpdatedOn(), equalTo(false));
	}

	@Test
	public void testGetSettingsNull() {
		when(pluginSettings.get("1")).thenReturn(null);

		RepositorySettings settings = repositorySettingsService.getSettings(1);

		assertThat(settings.getWebhook(), equalTo(""));
		assertThat(settings.isPullRequestReviewersUpdatedOn(), equalTo(true));
		assertThat(settings.isPullRequestUpdatedOn(), equalTo(true));
		assertThat(settings.isPullRequestReopenedOn(), equalTo(true));
		assertThat(settings.isPullRequestRescopedOn(), equalTo(true));
		assertThat(settings.isPullRequestParticipantStatusUpdatedOn(), equalTo(true));
		assertThat(settings.isPullRequestMergedOn(), equalTo(true));
		assertThat(settings.isPullRequestOpenedOn(), equalTo(true));
		assertThat(settings.isPullRequestDeclinedOn(), equalTo(true));
		assertThat(settings.isPullRequestCommentAddedOn(), equalTo(true));
		assertThat(settings.isPullRequestCommentDeletedOn(), equalTo(true));
		assertThat(settings.isPullRequestCommentEditedOn(), equalTo(true));
		assertThat(settings.isPullRequestCommentRepliedOn(), equalTo(true));
		assertThat(settings.isPullRequestCommitCommentAddedOn(), equalTo(true));
		assertThat(settings.isTaskCreatedOn(), equalTo(true));
		assertThat(settings.isTaskDeletedOn(), equalTo(true));
		assertThat(settings.isTaskUpdatedOn(), equalTo(true));
	}

	@Test
	public void testGetSettingsPartiallyMissing() {
		Map<String, String> settingsMap =  ImmutableMap.<String, String>builder().put("webhook", "http://localhost:8080/abc")
				.put("pullRequestReviewersUpdatedOn", "false")
				.put("pullRequestUpdatedOn", "false")
				.put("pullRequestReopenedOn", "false")
				.put("pullRequestRescopedOn", "false")
				.put("pullRequestParticipantStatusUpdatedOn", "false")
				.put("pullRequestMergedOn", "false")
				.put("pullRequestOpenedOn", "false")
				.put("pullRequestDeclinedOn", "false")
				.put("pullRequestCommentAddedOn", "false")
				.build();;
		when(pluginSettings.get("1")).thenReturn(settingsMap);

		RepositorySettings settings = repositorySettingsService.getSettings(1);

		assertThat(settings.getWebhook(), equalTo("http://localhost:8080/abc"));
		assertThat(settings.isPullRequestReviewersUpdatedOn(), equalTo(false));
		assertThat(settings.isPullRequestUpdatedOn(), equalTo(false));
		assertThat(settings.isPullRequestReopenedOn(), equalTo(false));
		assertThat(settings.isPullRequestRescopedOn(), equalTo(false));
		assertThat(settings.isPullRequestParticipantStatusUpdatedOn(), equalTo(false));
		assertThat(settings.isPullRequestMergedOn(), equalTo(false));
		assertThat(settings.isPullRequestOpenedOn(), equalTo(false));
		assertThat(settings.isPullRequestDeclinedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentAddedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentDeletedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentEditedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommentRepliedOn(), equalTo(false));
		assertThat(settings.isPullRequestCommitCommentAddedOn(), equalTo(false));
		assertThat(settings.isTaskCreatedOn(), equalTo(false));
		assertThat(settings.isTaskDeletedOn(), equalTo(false));
		assertThat(settings.isTaskUpdatedOn(), equalTo(false));
	}
	
	@Test
	public void testSetSettings() {
		RepositorySettings settings = new RepositorySettings.Builder().build();
		repositorySettingsService.setSettings(1, settings);

		ArgumentCaptor<Map> settingsMapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pluginSettings, times(1)).put(eq("1"), settingsMapArgumentCaptor.capture());
		
		Map<String, String> settingsMap = settingsMapArgumentCaptor.getValue();
		
		assertThat(settingsMap.get("webhook"), equalTo(""));
		assertThat(settingsMap.get("pullRequestReviewersUpdatedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestUpdatedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestReopenedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestRescopedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestParticipantStatusUpdatedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestMergedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestOpenedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestDeclinedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestCommentAddedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestCommentDeletedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestCommentEditedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestCommentRepliedOn"), equalTo("true"));
		assertThat(settingsMap.get("pullRequestCommitCommentAddedOn"), equalTo("true"));
		assertThat(settingsMap.get("taskCreatedOn"), equalTo("true"));
		assertThat(settingsMap.get("taskDeletedOn"), equalTo("true"));
		assertThat(settingsMap.get("taskUpdatedOn"), equalTo("true"));
	}

}
