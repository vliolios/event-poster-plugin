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
		Repository repository = event.getPullRequest().getToRef().getRepository();
		postEvent(event, PullRequestReviewersUpdatedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestUpdatedEvent event) {
		postEvent(event, PullRequestUpdatedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestReopenedEvent event) {
		postEvent(event, PullRequestReopenedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestRescopedEvent event) {
		postEvent(event, PullRequestRescopedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestParticipantStatusUpdatedEvent event) {
		postEvent(event, PullRequestParticipantStatusUpdatedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestMergedEvent event) {
		postEvent(event, PullRequestMergedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestOpenedEvent event) {
		postEvent(event, PullRequestOpenedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestDeclinedEvent event) {
		postEvent(event, PullRequestDeclinedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentAddedEvent event) {
		postEvent(event, PullRequestCommentAddedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentDeletedEvent event) {
		postEvent(event, PullRequestCommentDeletedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentEditedEvent event) {
		postEvent(event, PullRequestCommentEditedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommentRepliedEvent event) {
		postEvent(event, PullRequestCommentRepliedEvent.class);
	}

	@EventListener
	public void postPullRequestEvent(PullRequestCommitCommentAddedEvent event) {
		postEvent(event, PullRequestCommitCommentAddedEvent.class);
	}

	private <T extends PullRequestEvent> void postEvent(PullRequestEvent event, Class<T> clazz) {
		Repository repository = event.getPullRequest().getToRef().getRepository();
		postEvent(event, repository, clazz);
	}

}
