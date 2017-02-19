package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.pull.*;
import com.atlassian.bitbucket.repository.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class PullRequestEventListenerTest {

	private final RepositorySettingsService repositorySettingsService = mock(RepositorySettingsService.class);
	private final EventPoster eventPoster = mock(EventPoster.class);
	private final Repository repository = mock(Repository.class);

	private PullRequestEventListener pullRequestEventListener;

	@Before
	public void setUp() {
		pullRequestEventListener = new PullRequestEventListener(repositorySettingsService, eventPoster);
		when(repository.getId()).thenReturn(1);
	}

	@Test
	public void testPostPullRequestReviewersUpdatedEventNotificationsOff() {
		PullRequestReviewersUpdatedEvent event = mock(PullRequestReviewersUpdatedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestReviewersUpdatedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestReviewersUpdatedEventNotificationsOn() {
		PullRequestReviewersUpdatedEvent event = mock(PullRequestReviewersUpdatedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestReviewersUpdatedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestUpdatedEventNotificationsOff() {
		PullRequestUpdatedEvent event = mock(PullRequestUpdatedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestUpdatedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestUpdatedEventNotificationsOn() {
		PullRequestUpdatedEvent event = mock(PullRequestUpdatedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestUpdatedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestReopenedEventNotificationsOff() {
		PullRequestReopenedEvent event = mock(PullRequestReopenedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestReopenedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestReopenedEventNotificationsOn() {
		PullRequestReopenedEvent event = mock(PullRequestReopenedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestReopenedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestRescopedEventNotificationsOff() {
		PullRequestRescopedEvent event = mock(PullRequestRescopedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestRescopedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestRescopedEventNotificationsOn() {
		PullRequestRescopedEvent event = mock(PullRequestRescopedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestRescopedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestParticipantStatusUpdatedEventNotificationsOff() {
		PullRequestParticipantStatusUpdatedEvent event = mock(PullRequestParticipantStatusUpdatedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestParticipantStatusUpdatedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestParticipantStatusUpdatedEventNotificationsOn() {
		PullRequestParticipantStatusUpdatedEvent event = mock(PullRequestParticipantStatusUpdatedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestParticipantStatusUpdatedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestMergedEventNotificationsOff() {
		PullRequestMergedEvent event = mock(PullRequestMergedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestMergedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestMergedEventNotificationsOn() {
		PullRequestMergedEvent event = mock(PullRequestMergedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestMergedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestOpenedEventNotificationsOff() {
		PullRequestOpenedEvent event = mock(PullRequestOpenedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestOpenedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestOpenedEventNotificationsOn() {
		PullRequestOpenedEvent event = mock(PullRequestOpenedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestOpenedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestDeclinedEventNotificationsOff() {
		PullRequestDeclinedEvent event = mock(PullRequestDeclinedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestDeclinedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestDeclinedEventNotificationsOn() {
		PullRequestDeclinedEvent event = mock(PullRequestDeclinedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestDeclinedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestCommentAddedEventNotificationsOff() {
		PullRequestCommentAddedEvent event = mock(PullRequestCommentAddedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentAddedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestCommentAddedEventNotificationsOn() {
		PullRequestCommentAddedEvent event = mock(PullRequestCommentAddedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentAddedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestCommentDeletedEventNotificationsOff() {
		PullRequestCommentDeletedEvent event = mock(PullRequestCommentDeletedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentDeletedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestCommentDeletedEventNotificationsOn() {
		PullRequestCommentDeletedEvent event = mock(PullRequestCommentDeletedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentDeletedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestCommentEditedEventNotificationsOff() {
		PullRequestCommentEditedEvent event = mock(PullRequestCommentEditedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentEditedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestCommentEditedEventNotificationsOn() {
		PullRequestCommentEditedEvent event = mock(PullRequestCommentEditedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentEditedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestCommentRepliedEventNotificationsOff() {
		PullRequestCommentRepliedEvent event = mock(PullRequestCommentRepliedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentRepliedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestCommentRepliedEventNotificationsOn() {
		PullRequestCommentRepliedEvent event = mock(PullRequestCommentRepliedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommentRepliedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestCommitCommentAddedEventNotificationsOff() {
		PullRequestCommitCommentAddedEvent event = mock(PullRequestCommitCommentAddedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommitCommentAddedOn(false).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

	@Test
	public void testPostPullRequestCommitCommentAddedEventNotificationsOn() {
		PullRequestCommitCommentAddedEvent event = mock(PullRequestCommitCommentAddedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").pullRequestCommitCommentAddedOn(true).build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(event, "http://localhost:8080/abc");
	}

	@Test
	public void testPostPullRequestEventNoSettings() {
		PullRequestCommitCommentAddedEvent event = mock(PullRequestCommitCommentAddedEvent.class, Mockito.RETURNS_DEEP_STUBS);
		when(event.getPullRequest().getToRef().getRepository()).thenReturn(repository);
		when(repositorySettingsService.getSettings(1)).thenReturn(null);

		pullRequestEventListener.postPullRequestEvent(event);

		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}

}
