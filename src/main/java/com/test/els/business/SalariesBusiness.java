package com.test.els.business;

import java.util.List;

import com.test.els.entities.Salarie;

public interface SalariesBusiness {

	public Salarie recupererSalarieById(String id);

	public List<Salarie> recupererTousLesSalaries();

	public Salarie enregistrerSalarie(Salarie salarie);

	public void supprimerSalarie(String id);

	public List<Salarie> recherche(String prenom, String adresse, String fonction, Integer salaire,
			Integer anneesExperience);

}
