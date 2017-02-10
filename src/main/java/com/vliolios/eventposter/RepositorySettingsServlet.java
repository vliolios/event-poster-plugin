package com.vliolios.eventposter;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.google.common.collect.ImmutableMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Named
public class RepositorySettingsServlet extends HttpServlet {

	private final SoyTemplateRenderer soyTemplateRenderer;
	private final RepositoryService repositoryService;

	@Inject
	public RepositorySettingsServlet(@ComponentImport SoyTemplateRenderer soyTemplateRenderer, @ComponentImport RepositoryService repositoryService) {
		this.soyTemplateRenderer = soyTemplateRenderer;
		this.repositoryService = repositoryService;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			String pathInfo = request.getPathInfo();

			String[] pathParts = pathInfo.substring(1).split("/");

			String projectKey = pathParts[1];
			String repoSlug = pathParts[3];

			Repository repository = repositoryService.getBySlug(projectKey, repoSlug);

			Map<String, Object> model = ImmutableMap.<String, Object>builder().put("repository", repository).build();
			soyTemplateRenderer.render(response.getWriter(), "com.vliolios.event-poster-plugin:soy-templates", "bitbucketserver.page.eventposter.settings.repositoryEventPosterSettings", model);
		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}
}
