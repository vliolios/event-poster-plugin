package com.vliolios.eventposter;

import com.atlassian.bitbucket.AuthorisationException;
import com.atlassian.bitbucket.permission.Permission;
import com.atlassian.bitbucket.permission.PermissionValidationService;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

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
	private final PermissionValidationService permissionValidationService;

	@Inject
	public RepositorySettingsServlet(@ComponentImport SoyTemplateRenderer soyTemplateRenderer, @ComponentImport RepositoryService repositoryService,
	                                 RepositorySettingsService repositorySettingsService, @ComponentImport PermissionValidationService permissionValidationService) {
		this.soyTemplateRenderer = soyTemplateRenderer;
		this.repositoryService = repositoryService;
		this.repositorySettingsService = repositorySettingsService;
		this.permissionValidationService = permissionValidationService;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Repository repository = loadRepository(request);
		try {
			permissionValidationService.validateForRepository(repository, Permission.REPO_ADMIN);
			RepositorySettings settings = repositorySettingsService.getSettings(repository.getId());
			renderSettingsView(request, response, repository, settings);
		} catch (AuthorisationException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Repository repository = loadRepository(request);
		try {
			permissionValidationService.validateForRepository(repository, Permission.REPO_ADMIN);
			RepositorySettings settings = repositorySettingsService.setSettings(repository.getId(), getSettingsFromRequest(request));
			renderSettingsView(request, response, repository, settings);
		} catch (AuthorisationException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
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
				.webhook(StringUtils.stripToEmpty(request.getParameter(RepositorySettings.Keys.WEBHOOK)))
				.pullRequestReviewersUpdatedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_REVIEWERS_UPDATED_ON)))
				.pullRequestUpdatedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_UPDATED_ON)))
				.pullRequestReopenedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_REOPENED_ON)))
				.pullRequestRescopedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_RESCOPED_ON)))
				.pullRequestParticipantStatusUpdatedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_PARTICIPANT_STATUS_UPDATED_ON)))
				.pullRequestMergedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_MERGED_ON)))
				.pullRequestOpenedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_OPENED_ON)))
				.pullRequestDeclinedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_DECLINED_ON)))
				.pullRequestCommentAddedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_COMMENT_ADDED_ON)))
				.pullRequestCommentDeletedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_COMMENT_DELETED_ON)))
				.pullRequestCommentEditedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_COMMENT_EDITED_ON)))
				.pullRequestCommentRepliedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_COMMENT_REPLIED_ON)))
				.pullRequestCommitCommentAddedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.PULL_REQUEST_COMMIT_COMMENT_ADDED_ON)))
				.taskCreatedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.TASK_CREATED_ON)))
				.taskDeletedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.TASK_DELETED_ON)))
				.taskUpdatedOn(BooleanUtils.toBoolean(request.getParameter(RepositorySettings.Keys.TASK_UPDATED_ON)))
				.build();
	}
}
