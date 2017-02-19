package com.vliolios.eventposter;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.BooleanUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Named
public class RepositorySettingsServlet extends HttpServlet {

	private final SoyTemplateRenderer soyTemplateRenderer;
	private final RepositoryService repositoryService;
	private final RepositorySettingsService repositorySettingsService;

	@Inject
	public RepositorySettingsServlet(@ComponentImport SoyTemplateRenderer soyTemplateRenderer, @ComponentImport RepositoryService repositoryService,
	                                 RepositorySettingsService repositorySettingsService) {
		this.soyTemplateRenderer = soyTemplateRenderer;
		this.repositoryService = repositoryService;
		this.repositorySettingsService = repositorySettingsService;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Repository repository = loadRepository(request);
		RepositorySettings settings = repositorySettingsService.getSettings(repository.getId());
		renderSettingsView(request, response, repository, settings);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Repository repository = loadRepository(request);
		RepositorySettings settings = repositorySettingsService.setSettings(repository.getId(), getSettingsFromRequest(request));
		renderSettingsView(request, response, repository, settings);
	}

	private Repository loadRepository(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();

		String[] pathParts = pathInfo.substring(1).split("/");

		String projectKey = pathParts[1];
		String repoSlug = pathParts[3];
		return repositoryService.getBySlug(projectKey, repoSlug);
	}

	private void renderSettingsView(HttpServletRequest request, HttpServletResponse response, Repository repository, RepositorySettings settings) throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		try {


			Map<String, Object> model = ImmutableMap.<String, Object>builder().put("repository", repository)
					.put("eventPosterSettings", settings)
					.build();
			soyTemplateRenderer.render(response.getWriter(), "com.vliolios.event-poster-plugin:soy-templates", "bitbucketserver.page.eventposter.settings.repositoryEventPosterSettings", model);
		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}

	private RepositorySettings getSettingsFromRequest(HttpServletRequest request) {
		return new RepositorySettings.Builder()
				.webhook(request.getParameter("webhook"))
				.pullRequestReviewersUpdatedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestReviewersUpdatedOn")))
				.pullRequestUpdatedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestUpdatedOn")))
				.pullRequestReopenedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestReopenedOn")))
				.pullRequestRescopedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestRescopedOn")))
				.pullRequestParticipantStatusUpdatedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestParticipantStatusUpdatedOn")))
				.pullRequestMergedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestMergedOn")))
				.pullRequestOpenedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestOpenedOn")))
				.pullRequestDeclinedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestDeclinedOn")))
				.pullRequestCommentAddedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestCommentAddedOn")))
				.pullRequestCommentDeletedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestCommentDeletedOn")))
				.pullRequestCommentEditedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestCommentEditedOn")))
				.pullRequestCommentRepliedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestCommentRepliedOn")))
				.pullRequestCommitCommentAddedOn(BooleanUtils.toBoolean(request.getParameter("pullRequestCommitCommentAddedOn")))
				.taskCreatedOn(BooleanUtils.toBoolean(request.getParameter("taskCreatedOn")))
				.taskDeletedOn(BooleanUtils.toBoolean(request.getParameter("taskDeletedOn")))
				.taskUpdatedOn(BooleanUtils.toBoolean(request.getParameter("taskUpdatedOn")))
				.build();
	}
}
