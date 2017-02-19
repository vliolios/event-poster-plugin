package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.task.TaskCreatedEvent;
import com.atlassian.bitbucket.event.task.TaskDeletedEvent;
import com.atlassian.bitbucket.event.task.TaskUpdatedEvent;
import com.atlassian.bitbucket.task.Task;
import com.atlassian.bitbucket.task.TaskState;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TaskEventListenerTest {

	private static final String EVENT_JSON = "{" +
			"  \"task\": {" +
			"    \"context\": {" +
			"      \"fromRef\": {" +
			"        \"repository\": {" +
			"          \"id\": 1" +
			"        }" +
			"      }" +
			"    }" +
			"  }" +
			"}";

	private static final String EVENT_MISSING_DETAILS_JSON = "{" +
			"  \"task\": {" +
			"    \"context\": {" +
			"    }" +
			"  }" +
			"}";

	private final RepositorySettingsService repositorySettingsService = mock(RepositorySettingsService.class);
	private final EventPoster eventPoster = mock(EventPoster.class);
	private final EventToJsonConverter eventToJsonConverter = mock(EventToJsonConverter.class);

	private TaskEventListener taskEventListener;

	@Before
	public void setUp() {
		taskEventListener = new TaskEventListener(repositorySettingsService, eventPoster, eventToJsonConverter);
	}

	@Test
	public void testPostTaskCreatedEventNotificationsOff() throws IOException {

		TaskCreatedEvent event = mock(TaskCreatedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskCreatedOn(false).build();
		JsonNode eventJsonNode = new ObjectMapper().readTree(EVENT_JSON);
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(eventJsonNode);
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}


	@Test
	public void testPostTaskCreatedEventNotificationsOn() throws IOException {
		TaskCreatedEvent event = mock(TaskCreatedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskCreatedOn(true).build();
		JsonNode eventJsonNode = new ObjectMapper().readTree(EVENT_JSON);
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(eventJsonNode);
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(eventJsonNode, "http://localhost:8080/abc");
	}

	@Test
	public void testPostTaskDeletedEventNotificationsOff() throws IOException {

		TaskDeletedEvent event = mock(TaskDeletedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskDeletedOn(false).build();
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(new ObjectMapper().readTree(EVENT_JSON));
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}


	@Test
	public void testPostTaskDeletedEventNotificationsOn() throws IOException {
		TaskDeletedEvent event = mock(TaskDeletedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskDeletedOn(true).build();
		JsonNode eventJsonNode = new ObjectMapper().readTree(EVENT_JSON);
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(eventJsonNode);
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(eventJsonNode, "http://localhost:8080/abc");
	}

	@Test
	public void testPostTaskUpdatedEventNotificationsOff() throws IOException {

		TaskUpdatedEvent event = mock(TaskUpdatedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskUpdatedOn(false).build();
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(new ObjectMapper().readTree(EVENT_JSON));
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(1);
		verifyZeroInteractions(eventPoster);
	}


	@Test
	public void testPostTaskUpdatedEventNotificationsOn() throws IOException {
		TaskUpdatedEvent event = mock(TaskUpdatedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskUpdatedOn(true).build();
		JsonNode eventJsonNode = new ObjectMapper().readTree(EVENT_JSON);
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(eventJsonNode);
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(1);
		verify(eventPoster, times(1)).postEvent(eventJsonNode, "http://localhost:8080/abc");
	}

	@Test
	public void testPostTaskCannotGetRepository() throws IOException {
		TaskCreatedEvent event = mock(TaskCreatedEvent.class);
		RepositorySettings settings = new RepositorySettings.Builder().webhook("http://localhost:8080/abc").taskCreatedOn(true).build();
		when(eventToJsonConverter.convertToJsonNode(event)).thenReturn(new ObjectMapper().readTree(EVENT_MISSING_DETAILS_JSON));
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		taskEventListener.postTaskEvent(event);

		verify(eventToJsonConverter, times(1)).convertToJsonNode(event);
		verify(repositorySettingsService, times(1)).getSettings(0);
		verifyZeroInteractions(eventPoster);
	}
}
