package com.vliolios.eventposter;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Named
public class RepositorySettingsServlet extends HttpServlet {

	private final SoyTemplateRenderer soyTemplateRenderer;

	@Inject
	public RepositorySettingsServlet(@ComponentImport SoyTemplateRenderer soyTemplateRenderer) {
		this.soyTemplateRenderer = soyTemplateRenderer;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			soyTemplateRenderer.render(response.getWriter(), "com.vliolios.event-poster-plugin:soy-templates", "bitbucketserver.page.eventposter.settings.repositoryEventPosterSettings", new HashMap<String, Object>());
		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}
}
