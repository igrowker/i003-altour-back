package com.igrowker.altour.service;

import java.util.Set;

public interface IUserPreferenceService {

	String addPreference(String email, String newPreference);

	String removePreference(String email, String preferenceToRemove);

	Set<String> getPreferencesByEmail(String email);
}
