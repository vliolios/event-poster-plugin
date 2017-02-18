package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.pull.*;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.event.api.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PullRequestEventListener extends SettingsAwareEventListener {

	private static final Logger log = LoggerFactory.getLogger(PullRequestEventListener.class);

	@Inject
	public PullRequestEventListener(RepositorySettingsService repositorySettingsService) {
		super(repositorySettingsService);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReviewersUpdatedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestUpdatedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReopenedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestRescopedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestParticipantStatusUpdatedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestMergedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestOpenedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestDeclinedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentAddedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentDeletedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentEditedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentRepliedEvent event) {
		postEvent(event);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommitCommentAddedEvent event) {
		postEvent(event);
	}

	private void postEvent(PullRequestEvent event) {
		Repository repository = event.getPullRequest().getToRef().getRepository();
		postEvent(event, repository);
	}

}
