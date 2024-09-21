package com.igrowker.altour.service;

import java.util.Set;

public interface IUserPreferenceService {

	void addPreference(String username, String newPreference);

	void removePreference(String username, String preferenceToRemove);

	Set<String> getPreferences(String username);
}
