package com.test.els.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.els.entities.Salarie;

@Repository
public interface SalariesRepository extends MongoRepository<Salarie, String> {

	@Query("{ $or: [ { 'prenom' : ?0 }, { 'adresse' : ?1 }, { 'fonction' : ?2 }, { 'salaire' : ?3 }, { 'anneesExperience' : ?4 }]}")
	public List<Salarie> query(String prenom, String adresse, String fonction, Integer salaire,
			Integer anneesExperience);
}
