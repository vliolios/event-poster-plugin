package com.vliolios.eventposter;

import com.atlassian.bitbucket.comment.Comment;
import com.atlassian.bitbucket.event.ApplicationEvent;
import com.atlassian.bitbucket.event.pull.PullRequestEvent;
import com.atlassian.bitbucket.pull.PullRequestParticipant;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.watcher.Watcher;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EventToJsonConverterTest {

	private final ObjectMapper objectMapper = mock(ObjectMapper.class);
	private final ObjectWriter objectWriter = mock(ObjectWriter.class);

	private EventToJsonConverter eventToJsonConverter;

	@Before
	public void setUp(){
		when(objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)).thenReturn(objectMapper);
		when(objectMapper.addMixIn(any(), any())).thenReturn(objectMapper);
		when(objectMapper.writer(any(FilterProvider.class))).thenReturn(objectWriter);
		eventToJsonConverter = new EventToJsonConverter() {
			@Override
			public ObjectMapper getObjectMapper() {
				return objectMapper;
			}
		};
	}

	@Test
	public void testConvertApplicationEventToJsonString() throws JsonProcessingException {
		ApplicationEvent applicationEvent = mock(ApplicationEvent.class);
		when(objectWriter.writeValueAsString(applicationEvent)).thenReturn("{\"id\": 1}");

		String json = eventToJsonConverter.convertToJsonString(applicationEvent);
		assertThat(json, equalTo("{\"id\": 1}"));
		verify(objectMapper, times(1)).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		verify(objectMapper, times(1)).addMixIn(PullRequestEvent.class, EventToJsonConverter.PullRequestEventMixIn.class);
		verify(objectMapper, times(1)).addMixIn(ApplicationUser.class, EventToJsonConverter.ApplicationUserMixIn.class);
		verify(objectMapper, times(1)).addMixIn(Watcher.class, EventToJsonConverter.WatcherMixIn.class);
		verify(objectMapper, times(1)).addMixIn(PullRequestParticipant.class, EventToJsonConverter.PullRequestParticipantMixIn.class);
		verify(objectMapper, times(1)).addMixIn(Comment.class, EventToJsonConverter.CommentMixIn.class);

		ArgumentCaptor<FilterProvider> filterProviderArgumentCaptor = ArgumentCaptor.forClass(FilterProvider.class);
		verify(objectMapper, times(1)).writer(filterProviderArgumentCaptor.capture());

		FilterProvider filterProvider = filterProviderArgumentCaptor.getValue();
		assertThat(filterProvider.findPropertyFilter("eventFilter", null), notNullValue());
		assertThat(filterProvider.findPropertyFilter("userFilter", null), notNullValue());
		assertThat(filterProvider.findPropertyFilter("watcherFilter", null), notNullValue());
		assertThat(filterProvider.findPropertyFilter("participantFilter", null), notNullValue());
		assertThat(filterProvider.findPropertyFilter("commentFilter", null), notNullValue());
	}

	@Test(expected = RuntimeException.class)
	public void testConvertApplicationEventToJsonStringIOExceptionThrown() throws JsonProcessingException {
		ApplicationEvent applicationEvent = mock(ApplicationEvent.class);
		when(objectWriter.writeValueAsString(applicationEvent)).thenThrow(new JsonParseException(null, "msg"));

		eventToJsonConverter.convertToJsonString(applicationEvent);
	}

	@Test
	public void testConvertJsonNodeToJsonString() throws JsonProcessingException {
		JsonNode jsonNode = mock(JsonNode.class);
		when(objectMapper.writeValueAsString(jsonNode)).thenReturn("{\"id\": 1}");

		String json = eventToJsonConverter.convertToJsonString(jsonNode);

		assertThat(json, equalTo("{\"id\": 1}"));
		verify(objectMapper, times(1)).writeValueAsString(jsonNode);

	}

	@Test(expected = RuntimeException.class)
	public void testConvertJsonNodeToJsonStringIOExceptionThrown() throws JsonProcessingException {
		JsonNode jsonNode = mock(JsonNode.class);
		when(objectMapper.writeValueAsString(jsonNode)).thenThrow(new JsonParseException(null, "msg"));

		eventToJsonConverter.convertToJsonString(jsonNode);
	}

	@Test
	public void testConvertApplicationEventToJsonNode() throws IOException {
		ApplicationEvent applicationEvent = mock(ApplicationEvent.class);
		when(objectWriter.writeValueAsString(applicationEvent)).thenReturn("{\"id\": 1}");
		JsonNode jsonNode = mock(JsonNode.class);
		when(objectMapper.readTree("{\"id\": 1}")).thenReturn(jsonNode);

		assertThat(eventToJsonConverter.convertToJsonNode(applicationEvent), equalTo(jsonNode));
	}

	@Test(expected = RuntimeException.class)
	public void testConvertApplicationEventToJsonNodeIOExceptionThrown() throws IOException {
		ApplicationEvent applicationEvent = mock(ApplicationEvent.class);
		when(objectWriter.writeValueAsString(applicationEvent)).thenReturn("{\"id\": 1}");
		JsonNode jsonNode = mock(JsonNode.class);
		when(objectMapper.readTree("{\"id\": 1}")).thenThrow(new JsonParseException(null, "msg"));

		eventToJsonConverter.convertToJsonNode(applicationEvent);
	}

}
