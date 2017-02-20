package com.vliolios.eventposter;

import com.atlassian.bitbucket.AuthorisationException;
import com.atlassian.bitbucket.i18n.KeyedMessage;
import com.atlassian.bitbucket.permission.Permission;
import com.atlassian.bitbucket.permission.PermissionValidationService;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class RepositorySettingsServletTest {

	private final SoyTemplateRenderer soyTemplateRenderer = mock(SoyTemplateRenderer.class);
	private final RepositoryService repositoryService = mock(RepositoryService.class);
	private final RepositorySettingsService repositorySettingsService = mock(RepositorySettingsService.class);
	private final PermissionValidationService permissionValidationService = mock(PermissionValidationService.class);

	private RepositorySettingsServlet repositorySettingsServlet;

	@Before
	public void setUp() {
		repositorySettingsServlet = new RepositorySettingsServlet(soyTemplateRenderer, repositoryService, repositorySettingsService, permissionValidationService);
	}

	@Test
	public void testDoGet() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getPathInfo()).thenReturn("/projects/PROJECT_1/repos/rep_1");

		Repository repository = mock(Repository.class);
		when(repository.getId()).thenReturn(1);
		when(repositoryService.getBySlug("PROJECT_1", "rep_1")).thenReturn(repository);

		RepositorySettings settings = new RepositorySettings.Builder().build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		HttpServletResponse response = mock(HttpServletResponse.class);
		PrintWriter responseWriter = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(responseWriter);
		repositorySettingsServlet.doGet(request, response);

		verify(response, times(1)).setContentType("text/html;charset=UTF-8");
		verify(response, times(1)).getWriter();
		ArgumentCaptor<Map> modelMapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
		verify(soyTemplateRenderer, times(1)).render(eq(responseWriter), eq("com.vliolios.event-poster-plugin:soy-templates"), eq("bitbucketserver.page.eventposter.settings.repositoryEventPosterSettings"), modelMapArgumentCaptor.capture());
		Map<String, Object> modelMap = (Map<String, Object>) modelMapArgumentCaptor.getValue();
		assertThat(modelMap.get("repository"), equalTo(repository));
		assertThat(modelMap.get("eventPosterSettings"), equalTo(settings));
		verify(repositoryService, times(1)).getBySlug("PROJECT_1", "rep_1");
	}

	@Test
	public void testDoPost() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getPathInfo()).thenReturn("/projects/PROJECT_1/repos/rep_1");

		Repository repository = mock(Repository.class);
		when(repository.getId()).thenReturn(1);
		when(repositoryService.getBySlug("PROJECT_1", "rep_1")).thenReturn(repository);

		RepositorySettings settings = new RepositorySettings.Builder().build();
		when(repositorySettingsService.setSettings(eq(1), any(RepositorySettings.class))).thenReturn(settings);

		HttpServletResponse response = mock(HttpServletResponse.class);
		PrintWriter responseWriter = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(responseWriter);
		repositorySettingsServlet.doPost(request, response);

		verify(response, times(1)).setContentType("text/html;charset=UTF-8");
		verify(response, times(1)).getWriter();
		ArgumentCaptor<Map> modelMapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
		verify(soyTemplateRenderer, times(1)).render(eq(responseWriter), eq("com.vliolios.event-poster-plugin:soy-templates"), eq("bitbucketserver.page.eventposter.settings.repositoryEventPosterSettings"), modelMapArgumentCaptor.capture());
		Map<String, Object> modelMap = (Map<String, Object>) modelMapArgumentCaptor.getValue();
		assertThat(modelMap.get("repository"), equalTo(repository));
		assertThat(modelMap.get("eventPosterSettings"), equalTo(settings));
		verify(repositoryService, times(1)).getBySlug("PROJECT_1", "rep_1");

		ArgumentCaptor<RepositorySettings> repositorySettingsArgumentCaptor = ArgumentCaptor.forClass(RepositorySettings.class);
		verify(repositorySettingsService, times(1)).setSettings(eq(1), repositorySettingsArgumentCaptor.capture());
		RepositorySettings repositorySettings = repositorySettingsArgumentCaptor.getValue();
		assertThat(repositorySettings.getWebhook(), equalTo(""));
		assertThat(repositorySettings.isPullRequestReviewersUpdatedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestUpdatedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestReopenedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestRescopedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestParticipantStatusUpdatedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestMergedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestOpenedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestDeclinedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestCommentAddedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestCommentDeletedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestCommentEditedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestCommentRepliedOn(), equalTo(false));
		assertThat(repositorySettings.isPullRequestCommitCommentAddedOn(), equalTo(false));
		assertThat(repositorySettings.isTaskCreatedOn(), equalTo(false));
		assertThat(repositorySettings.isTaskDeletedOn(), equalTo(false));
		assertThat(repositorySettings.isTaskUpdatedOn(), equalTo(false));
	}

	@Test
	public void testDoGetPermissionDenied() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getPathInfo()).thenReturn("/projects/PROJECT_1/repos/rep_1");

		Repository repository = mock(Repository.class);
		when(repositoryService.getBySlug("PROJECT_1", "rep_1")).thenReturn(repository);

		doThrow(new AuthorisationException(new KeyedMessage("key", "msg", "msg"))).when(permissionValidationService).validateForRepository(repository, Permission.REPO_ADMIN);

		HttpServletResponse response = mock(HttpServletResponse.class);

		repositorySettingsServlet.doGet(request, response);

		verify(response, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN);
		verifyNoMoreInteractions(response);
		verifyZeroInteractions(soyTemplateRenderer, repositorySettingsService);
		verify(permissionValidationService, times(1)).validateForRepository(repository, Permission.REPO_ADMIN);
		verify(repositoryService, times(1)).getBySlug("PROJECT_1", "rep_1");
	}

	@Test
	public void testDoPostPermissionDenied() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getPathInfo()).thenReturn("/projects/PROJECT_1/repos/rep_1");

		Repository repository = mock(Repository.class);
		when(repositoryService.getBySlug("PROJECT_1", "rep_1")).thenReturn(repository);

		doThrow(new AuthorisationException(new KeyedMessage("key", "msg", "msg"))).when(permissionValidationService).validateForRepository(repository, Permission.REPO_ADMIN);

		HttpServletResponse response = mock(HttpServletResponse.class);

		repositorySettingsServlet.doPost(request, response);

		verify(response, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN);
		verifyNoMoreInteractions(response);
		verifyZeroInteractions(soyTemplateRenderer, repositorySettingsService);
		verify(permissionValidationService, times(1)).validateForRepository(repository, Permission.REPO_ADMIN);
		verify(repositoryService, times(1)).getBySlug("PROJECT_1", "rep_1");
	}

	@Test(expected = IOException.class)
	public void testDoGetSoyIOException() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getPathInfo()).thenReturn("/projects/PROJECT_1/repos/rep_1");

		Repository repository = mock(Repository.class);
		when(repository.getId()).thenReturn(1);
		when(repositoryService.getBySlug("PROJECT_1", "rep_1")).thenReturn(repository);

		RepositorySettings settings = new RepositorySettings.Builder().build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		HttpServletResponse response = mock(HttpServletResponse.class);
		PrintWriter responseWriter = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(responseWriter);

		doThrow(new SoyException(new IOException("msg"))).when(soyTemplateRenderer).render(any(PrintWriter.class), anyString(), anyString(), anyMap());
		repositorySettingsServlet.doGet(request, response);
	}

	@Test(expected = ServletException.class)
	public void testDoGetSoyServletException() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getPathInfo()).thenReturn("/projects/PROJECT_1/repos/rep_1");

		Repository repository = mock(Repository.class);
		when(repository.getId()).thenReturn(1);
		when(repositoryService.getBySlug("PROJECT_1", "rep_1")).thenReturn(repository);

		RepositorySettings settings = new RepositorySettings.Builder().build();
		when(repositorySettingsService.getSettings(1)).thenReturn(settings);

		HttpServletResponse response = mock(HttpServletResponse.class);
		PrintWriter responseWriter = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(responseWriter);

		doThrow(new SoyException(new RuntimeException("msg"))).when(soyTemplateRenderer).render(any(PrintWriter.class), anyString(), anyString(), anyMap());
		repositorySettingsServlet.doGet(request, response);
	}

}
