package com.vliolios.eventposter;

@FunctionalInterface
public interface RepositorySettingsChecker {
	boolean isEventNotificationOn(RepositorySettings repositorySettings);
}
