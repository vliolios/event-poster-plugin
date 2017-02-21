package com.vliolios.eventposter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RepositorySettingsTest {
	
	@Test
	public void testBuilderDefaultSettings() {
		
		RepositorySettings settings = new RepositorySettings.Builder().build();
		
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
	public void testBuilderCustomSettings() {

		RepositorySettings settings = new RepositorySettings.Builder()
				.webhook("http://localhost:8080/abc")
				.pullRequestReviewersUpdatedOn(false)
				.pullRequestUpdatedOn(false)
				.pullRequestReopenedOn(false)
				.pullRequestRescopedOn(false)
				.pullRequestParticipantStatusUpdatedOn(false)
				.pullRequestMergedOn(false)
				.pullRequestOpenedOn(false)
				.pullRequestDeclinedOn(false)
				.pullRequestCommentAddedOn(false)
				.pullRequestCommentDeletedOn(false)
				.pullRequestCommentEditedOn(false)
				.pullRequestCommentRepliedOn(false)
				.pullRequestCommitCommentAddedOn(false)
				.taskCreatedOn(false)
				.taskDeletedOn(false)
				.taskUpdatedOn(false)
				.build();

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
}
