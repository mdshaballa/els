package com.test.els;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.els.business.SalariesBusinessCrud;
import com.test.els.entities.Salarie;
import com.test.els.repository.SalariesRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
class SalariesBusinessTest {

	@Autowired
	private SalariesBusinessCrud salairesBusinessCrud;

	@MockBean
	private SalariesRepository repository;

	Calendar date = Calendar.getInstance();

	Salarie sal1 = new Salarie("1", "Ahmed", "Sofia", date.getTime(), "développeur", 2, "Nantes", 50000);
	Salarie sal2 = new Salarie("2", "philippe", "Sofia", date.getTime(), "chef de projet", 5, "Brest", 70000);
	Salarie sal3 = new Salarie("Jean", "khteira", date.getTime(), "développeur", 5, "Paris", 70000);

	@Before
	public void before() {

	}

	@Test
	void testRecupererTousSalariesTest() {
		Mockito.when(repository.findAll()).thenReturn(Stream.of(sal1, sal2, sal3).collect(Collectors.toList()));
		assertEquals(3, salairesBusinessCrud.recupererTousLesSalaries().size());
	}

	@Test
	public void enregistrerSalarieTest() {

		Mockito.when(repository.save(sal1)).thenReturn(sal1);
		salairesBusinessCrud.enregistrerSalarie(sal1);
		Mockito.verify(repository, Mockito.times(1)).save(sal1);

	}

	@Test
	public void supprimerSalarieTest() {

		salairesBusinessCrud.supprimerSalarie(sal2.getId());
		Mockito.verify(repository, Mockito.times(1)).deleteById(sal2.getId());

	}

	@Test
	public void testRecherche() {
		// recherche par prenom
		Mockito.when(repository.query("Sofia", null, null, null, null))
				.thenReturn(Stream.of(sal1, sal2).collect(Collectors.toList()));
		assertEquals(2, salairesBusinessCrud.recherche("Sofia", null, null, null, null).size());

		// recherche par prenom, salaire
		Mockito.when(repository.query("Sofia", null, null, 50000, null))
				.thenReturn(Stream.of(sal1).collect(Collectors.toList()));
		assertEquals(1, salairesBusinessCrud.recherche("Sofia", null, null, 50000, null).size());

		// recherche par prenom, fonction
		Mockito.when(repository.query("Sofia", null, "développeur", 50000, null))
				.thenReturn(Stream.of(sal1).collect(Collectors.toList()));
		assertEquals(1, salairesBusinessCrud.recherche("Sofia", null, null, 50000, null).size());

		// recherche par fonction
		Mockito.when(repository.query(null, null, "développeur", null, null))
				.thenReturn(Stream.of(sal1, sal3).collect(Collectors.toList()));
		assertEquals(2, salairesBusinessCrud.recherche(null, null, "développeur", null, null).size());

		// recherche par salaire
		Mockito.when(repository.query(null, null, null, 70000, null))
				.thenReturn(Stream.of(sal2, sal3).collect(Collectors.toList()));
		assertEquals(2, salairesBusinessCrud.recherche(null, null, null, 70000, null).size());

		// recherche par adresse
		Mockito.when(repository.query(null, "Brest", null, null, null))
				.thenReturn(Stream.of(sal2).collect(Collectors.toList()));
		assertEquals(1, salairesBusinessCrud.recherche(null, "Brest", null, null, null).size());

		// recherche par années d'expérience
		Mockito.when(repository.query(null, null, null, null, 5))
				.thenReturn(Stream.of(sal2, sal3).collect(Collectors.toList()));
		assertEquals(2, salairesBusinessCrud.recherche(null, null, null, null, 5).size());

		// recherche : resultat non trouver
		Mockito.when(repository.query("mohamed", "Noisiel", "développeur web", 1000000, 5))
				.thenReturn(Collections.emptyList());
		assertEquals(0, salairesBusinessCrud.recherche("mohamed", "Noisiel", "développeur web", 1000000, 5).size());
	}

	@Test
	public void getSalarieByIdTest() throws Exception {

		Mockito.when(repository.findById("1")).thenReturn(Optional.of(sal1));
		assertEquals(sal1, salairesBusinessCrud.recupererSalarieById(sal1.getId()));

		RuntimeException ex = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
				() -> salairesBusinessCrud.recupererSalarieById("2"));
		assertEquals("salarie nexist pas", ex.getMessage());
	}

}
