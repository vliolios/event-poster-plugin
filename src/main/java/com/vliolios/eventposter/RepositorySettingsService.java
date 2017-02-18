package com.vliolios.eventposter;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.google.common.collect.ImmutableMap;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class RepositorySettingsService {

	private PluginSettings pluginSettings;

	@Inject
	public RepositorySettingsService(@ComponentImport PluginSettingsFactory pluginSettingsFactory) {
		this.pluginSettings = pluginSettingsFactory.createSettingsForKey("com.vliolios.event-poster-plugin");
	}

	public Map<String, String> getSettings(int repositoryId) {
		String repositoryIdString = Integer.toString(repositoryId);
		Map<String, String> settings = (Map<String, String>) pluginSettings.get(repositoryIdString);
		if (settings == null) {
			settings = ImmutableMap.<String, String>builder().put("webhook", "").build();
			pluginSettings.put(repositoryIdString, settings);
		}
		return settings;
	}

	public Map<String, String> setSettings(int repositoryId, String webhook) {
		Map<String, String> settings = ImmutableMap.<String, String>builder().put("webhook", webhook).build();
		pluginSettings.put(Integer.toString(repositoryId), settings);
		return settings;
	}
}
