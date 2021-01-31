package com.test.els.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.els.entities.Salarie;
import com.test.els.repository.SalariesRepository;

@Service
public class SalariesBusinessCrud implements SalariesBusiness {

	private SalariesRepository salariesRepository;

	@Autowired
	public SalariesBusinessCrud(SalariesRepository salariesRepository) {
		this.salariesRepository = salariesRepository;
	}

	@Override
	public List<Salarie> recupererTousLesSalaries() {
		return salariesRepository.findAll();
	}

	@Override
	public Salarie recupererSalarieById(String id) {
		if (salariesRepository.findById(id).isPresent()) {
			return salariesRepository.findById(id).get();
		}
		throw new RuntimeException("salarie nexist pas");

	}

	@Override
	public List<Salarie> recherche(String prenom, String adresse, String fonction, Integer salaire,
			Integer anneesExperience) {
		return salariesRepository.query(prenom, adresse, fonction, salaire, anneesExperience);
	}

	@Override
	public Salarie enregistrerSalarie(Salarie salarie) {
		return salariesRepository.save(salarie);
	}

	@Override
	public void supprimerSalarie(String id) {
		salariesRepository.deleteById(id);
		;
	}

}
