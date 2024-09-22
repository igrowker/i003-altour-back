package com.igrowker.altour.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.igrowker.altour.exceptions.NotFoundException;
import com.igrowker.altour.service.IUserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.UserPreference;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.persistence.repository.IUserPreferenceRepository;

@Service
public class UserPreferenceServiceImpl implements IUserPreferenceService {

	@Autowired
	private IUserPreferenceRepository userPreferenceRepository;

	@Autowired
	private ICustomUserRepository customUserRepository;


	@Override
	public Set<String> getPreferencesByEmail(String email) {
		Optional<CustomUser> user = customUserRepository.findByEmail(email);

		if (user.isEmpty())  throw new NotFoundException("No se encontro usuario!"); // TODO esta excep no deberia salir nunca ya que si viene un jwt deberia ser de un usuario.. ANALIZAR Y LUEGO BORRAR

		CustomUser userPresent = user.get();
		// TODO ESTE METODO LO PODEMOS REFACTORIZAR PARA QUE BUSQUE LAS PREFERENCIAS POR USERNAME EVITANDO ASI BUSCAR PRIMERO AL USUARIO EN LA BD, YA QUE ASUMIMOS QUE EXISTE DEBIDO A QUE TIENE UN JWT VALIDO
		Set<UserPreference> preferences = userPreferenceRepository.findByUser(userPresent);
		return preferences.stream().map(UserPreference::getPreference).collect(Collectors.toSet());

	}

	@Override
	public String addPreference(String email, String newPreference) {
		Optional<CustomUser> user = customUserRepository.findByEmail(email);

		if (user.isEmpty())  throw new NotFoundException("No se encontro usuario!"); // TODO esta excep no deberia salir nunca ya que si viene un jwt deberia ser de un usuario.. ANALIZAR Y LUEGO BORRAR

		// TODO ACA DEBERIAMOS VALIDAR QUE SEA UNICO EL VALOR.. Deberiamos almacenar la preferencia en una tabla con valores unicos y que la preferencia del usuario este en su misma tabla con un fk que referencia a la preferencia en si.. de esta forma envitaremos que mil usuarios tengan la preferencia "museo" en la misma tabla..
		UserPreference preference = new UserPreference();
		preference.setUser(user.get());
		preference.setPreference(newPreference);
		userPreferenceRepository.save(preference);
		return "Preferencia agregada!";

	}

	@Override
	public String removePreference(String email, String preferenceToRemove) {

		// todo VERIFICAR METODO => LANZA EXCEP No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call"


		Optional<CustomUser> user = customUserRepository.findByEmail(email);
		if (user.isEmpty())  throw new NotFoundException("No se encontro usuario!"); // TODO esta excep no deberia salir nunca ya que si viene un jwt deberia ser de un usuario.. ANALIZAR Y LUEGO BORRAR



		CustomUser userPresent = user.get();
		userPreferenceRepository.deleteByUserAndPreference(userPresent, preferenceToRemove);
		return "Preference removed successfully";
	}
}
