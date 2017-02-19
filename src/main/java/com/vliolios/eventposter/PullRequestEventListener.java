package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.pull.*;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.event.api.EventListener;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PullRequestEventListener {

	private final RepositorySettingsService repositorySettingsService;
	private final EventPoster eventPoster;

	@Inject
	public PullRequestEventListener(RepositorySettingsService repositorySettingsService, EventPoster eventPoster) {
		this.repositorySettingsService = repositorySettingsService;
		this.eventPoster = eventPoster;
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReviewersUpdatedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestReviewersUpdatedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestUpdatedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestUpdatedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReopenedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestReopenedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestRescopedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestRescopedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestParticipantStatusUpdatedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestParticipantStatusUpdatedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestMergedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestMergedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestOpenedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestOpenedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestDeclinedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestDeclinedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentAddedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestCommentAddedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentDeletedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestCommentDeletedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentEditedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestCommentEditedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentRepliedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestCommentRepliedOn);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommitCommentAddedEvent event) {
		postEvent(event, RepositorySettings::isPullRequestCommitCommentAddedOn);
	}

	private void postEvent(PullRequestEvent event, RepositorySettingsChecker checker) {
		Repository repository = event.getPullRequest().getToRef().getRepository();
		RepositorySettings repositorySettings = repositorySettingsService.getSettings(repository.getId());
		if (repositorySettings != null && StringUtils.hasText(repositorySettings.getWebhook()) && checker.isEventNotificationOn(repositorySettings)) {
			eventPoster.postEvent(event, repositorySettings.getWebhook());
		}
	}

}
