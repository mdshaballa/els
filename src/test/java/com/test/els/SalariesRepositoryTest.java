package com.test.els;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.els.business.SalariesBusinessCrud;
import com.test.els.entities.Salarie;
import com.test.els.repository.SalariesRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
class SalariesRepositoryTest {

	@Autowired
	private SalariesRepository salariesrepository;

	@Autowired
	private SalariesBusinessCrud salairesBusinessCrud;

	@Mock
	private SalariesRepository repository;

	Calendar date = Calendar.getInstance();

	Salarie sal1 = new Salarie("1", "Ahmed", "sofia", date.getTime(), "développeur", 2, "Nantes", 50000);
	Salarie sal2 = new Salarie("2", "philippe", "sofia", date.getTime(), "chef de projet", 5, "Brest", 70000);
	Salarie sal3 = new Salarie("Jean", "khteira", date.getTime(), "développeur", 5, "Paris", 70000);

	@Before
	public void before() {

	}

	@Test
	void test() {
		// test méthode getAll() salaries
		List<Salarie> listSalarie = salariesrepository.findAll();
		List<Salarie> listBusiness = salairesBusinessCrud.recupererTousLesSalaries();
		assertEquals(listBusiness.size(), listSalarie.size());

		// assurez vous que l'id existe bien en base de données
		Salarie salarieRepo = salariesrepository.findById("6015d1da49a3612e18af4f12").orElse(null);
		Salarie salaireCrud = salairesBusinessCrud.recupererSalarieById("6015d1da49a3612e18af4f12");
		assertEquals(salarieRepo.getAdresse(), salaireCrud.getAdresse());

		// test le cas ou id n'existe pas en base de données
		salarieRepo = salariesrepository.findById("id").orElse(null);
		assertEquals(null, salarieRepo);
	}

	@Test
	void testQuery() {

		// recherche par prenom
		Mockito.when(repository.query("Sofia", null, null, null, null))
				.thenReturn(Stream.of(sal1, sal2).collect(Collectors.toList()));
		assertEquals(2, repository.query("Sofia", null, null, null, null).size());

		// recherche par prenom, salaire
		Mockito.when(repository.query("Sofia", null, null, 50000, null))
				.thenReturn(Stream.of(sal1).collect(Collectors.toList()));
		assertEquals(1, repository.query("Sofia", null, null, 50000, null).size());

		// recherche par prenom, fonction
		Mockito.when(repository.query("Sofia", null, "développeur", 50000, null))
				.thenReturn(Stream.of(sal1).collect(Collectors.toList()));
		assertEquals(1, repository.query("Sofia", null, null, 50000, null).size());

		// recherche par fonction
		Mockito.when(repository.query(null, null, "développeur", null, null))
				.thenReturn(Stream.of(sal1, sal3).collect(Collectors.toList()));
		assertEquals(2, repository.query(null, null, "développeur", null, null).size());

		// recherche par salaire
		Mockito.when(repository.query(null, null, null, 70000, null))
				.thenReturn(Stream.of(sal2, sal3).collect(Collectors.toList()));
		assertEquals(2, repository.query(null, null, null, 70000, null).size());

		// recherche par adresse
		Mockito.when(repository.query(null, "Brest", null, null, null))
				.thenReturn(Stream.of(sal2).collect(Collectors.toList()));
		assertEquals(1, repository.query(null, "Brest", null, null, null).size());

		// recherche par années d'expérience
		Mockito.when(repository.query(null, null, null, null, 5))
				.thenReturn(Stream.of(sal2, sal3).collect(Collectors.toList()));
		assertEquals(2, repository.query(null, null, null, null, 5).size());

		// recherche : resultat non trouver
		Mockito.when(repository.query("mohamed", "Noisiel", "développeur web", 1000000, 5))
				.thenReturn(Collections.emptyList());
		assertEquals(0, repository.query("mohamed", "Noisiel", "développeur web", 1000000, 5).size());

	}

}
