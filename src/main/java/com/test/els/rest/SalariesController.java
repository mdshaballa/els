package com.test.els.rest;

import com.test.els.business.SalariesBusinessCrud;
import com.test.els.entities.Salarie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class SalariesController {

	private SalariesBusinessCrud salariesBusinessCrud;

	@Autowired
	public SalariesController(SalariesBusinessCrud salariesBusinessCrud) {
		this.salariesBusinessCrud = salariesBusinessCrud;
	}

	@GetMapping(value = "/tous")
	public List<Salarie> getAllSalaries() {
		return salariesBusinessCrud.recupererTousLesSalaries();
	}

	@GetMapping(value = "/id/{id}")
	public Salarie recupererSalariesById(@PathVariable String id) {
		return salariesBusinessCrud.recupererSalarieById(id);
	}

	@GetMapping(value = "/recherche")
	public List<Salarie> recherche(@RequestParam(required = false) String prenom,
			@RequestParam(required = false) String adresse, @RequestParam(required = false) String fonction,
			@RequestParam(required = false) Integer salaire, @RequestParam(required = false) Integer anneesExperience) {
		return salariesBusinessCrud.recherche(prenom, adresse, fonction, salaire, anneesExperience);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable String id) {
		salariesBusinessCrud.supprimerSalarie(id);
	}

	@PostMapping()
	public Salarie saveOrUpdateSalarie(@RequestBody Salarie salarie) {
		return salariesBusinessCrud.enregistrerSalarie(salarie);
	}

	@GetMapping(value = "/critere/{critere}")
	public List<Salarie> salariesCritere(@PathVariable String critere) {
		if (critere.equalsIgnoreCase("prénom"))
			return salariesBusinessCrud.recupererTousLesSalaries().stream().filter(critere(Salarie::getPrenom))
					.collect(Collectors.toList());
		if (critere.equalsIgnoreCase("salaire"))
			return salariesBusinessCrud.recupererTousLesSalaries().stream().filter(critere(Salarie::getSalaire))
					.collect(Collectors.toList());
		if (critere.equalsIgnoreCase("adresse"))
			return salariesBusinessCrud.recupererTousLesSalaries().stream().filter(critere(Salarie::getAdresse))
					.collect(Collectors.toList());
		if (critere.equalsIgnoreCase("date de naissance"))
			return salariesBusinessCrud.recupererTousLesSalaries().stream().filter(critere(Salarie::getDateNaissance))
					.collect(Collectors.toList());
		if (critere.equalsIgnoreCase("fonction"))
			return salariesBusinessCrud.recupererTousLesSalaries().stream().filter(critere(Salarie::getFonction))
					.collect(Collectors.toList());
		if (critere.equalsIgnoreCase("années expérience"))
			return salariesBusinessCrud.recupererTousLesSalaries().stream()
					.filter(critere(Salarie::getAnneesExperience)).collect(Collectors.toList());
		else
			return Collections.emptyList();
	}

	public static <T> Predicate<T> critere(Function<? super T, ?> cle) {
		Set<Object> trouver = ConcurrentHashMap.newKeySet();
		return t -> trouver.add(cle.apply(t));
	}

}
