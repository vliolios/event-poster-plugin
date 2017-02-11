package com.vliolios.eventposter;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
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
import java.util.Map;
import java.util.StringTokenizer;

@Named
public class RepositorySettingsServlet extends HttpServlet {

	private final SoyTemplateRenderer soyTemplateRenderer;
	private final RepositoryService repositoryService;
	private final PluginSettings pluginSettings;

	@Inject
	public RepositorySettingsServlet(@ComponentImport SoyTemplateRenderer soyTemplateRenderer, @ComponentImport RepositoryService repositoryService,
	                                 @ComponentImport PluginSettingsFactory pluginSettingsFactory) {
		this.soyTemplateRenderer = soyTemplateRenderer;
		this.repositoryService = repositoryService;
		this.pluginSettings = pluginSettingsFactory.createSettingsForKey("com.vliolios.event-poster-plugin");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Repository repository = loadRepository(request);
		Map<String, String> settings = (Map<String, String>) pluginSettings.get(Integer.toString(repository.getId()));
		if (settings == null) {
			settings = ImmutableMap.<String, String>builder().put("webhook", "").build();
			pluginSettings.put(Integer.toString(repository.getId()), settings);
		}
		renderSettingsView(request, response, repository, settings);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String webhook = request.getParameter("webhook");
		Repository repository = loadRepository(request);
		Map<String, String> settings = ImmutableMap.<String, String>builder().put("webhook", webhook).build();
		pluginSettings.put(Integer.toString(repository.getId()), settings);
		renderSettingsView(request, response, repository, settings);
	}

	private Repository loadRepository(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();

		String[] pathParts = pathInfo.substring(1).split("/");

		String projectKey = pathParts[1];
		String repoSlug = pathParts[3];
		return repositoryService.getBySlug(projectKey, repoSlug);
	}

	private void renderSettingsView(HttpServletRequest request, HttpServletResponse response, Repository repository, Map<String, String> settings) throws IOException, ServletException {
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
}
