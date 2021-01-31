package com.test.els;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.test.els.business.SalariesBusinessCrud;
import com.test.els.entities.Salarie;
import com.test.els.rest.SalariesController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SalariesControllerTest {

	@InjectMocks
	private SalariesController salarieController;

	@Mock
	private SalariesBusinessCrud salairesBusinessCrud;

	TestRestTemplate restTest = new TestRestTemplate();
	@LocalServerPort
	int port;

	Calendar date = Calendar.getInstance();

	Salarie sal1 = new Salarie("1", "ahmed", "sofia", date.getTime(), "développeur", 2, "Nantes", 50000);
	Salarie sal2 = new Salarie("2", "philippe", "saliou", date.getTime(), "chef de projet", 5, "Brest", 70000);
	Salarie sal3 = new Salarie("Jean", "khteira", date.getTime(), "développeur", 5, "Paris", 70000);

	@Before
	public void setUp() {
	}

	@Test
	public void testGetAllSalaries() throws Exception {
		Mockito.when(salairesBusinessCrud.recupererTousLesSalaries())
				.thenReturn(Stream.of(sal1, sal2, sal3).collect(Collectors.toList()));
		assertEquals(3, salarieController.getAllSalaries().size());
	}

	@Test
	public void testSalarieApi() throws Exception {

//		//recuperer tout les éléments de la base de données (n =6 )
		ResponseEntity<Salarie[]> listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/tous",
				Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(6, listSalarie.getBody().length);
		// supprimer l'element avec id = 601411fcfda1d873b7c90f90
		restTest.delete("http://localhost:" + port + "/api/601411fcfda1d873b7c90f90");
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/tous", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(5, listSalarie.getBody().length);
		// insérer sal3 dans la base de données
		ResponseEntity<Salarie> postSalarie = restTest.postForEntity("http://localhost:" + port + "/api", sal3,
				Salarie.class);
		assertEquals(HttpStatus.OK, postSalarie.getStatusCode());
		// vérifier que l élément est bien insérer
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/tous", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(6, listSalarie.getBody().length);
	}

	@Test
	public void testSalariesCritere() {
		// retourne une liste d'éléments unique sur le champs prénom
		ResponseEntity<Salarie[]> listSalarie = restTest
				.getForEntity("http://localhost:" + port + "/api/critere/prénom", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(3, listSalarie.getBody().length);

		// retourne une liste d'éléments unique sur le champs salaire
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/critere/salaire", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(3, listSalarie.getBody().length);

		// retourne une liste d'éléments unique sur le champs adresse
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/critere/adresse", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(3, listSalarie.getBody().length);

		// retourne une liste d'éléments unique sur le champs date de naissance
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/critere/date de naissance",
				Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(4, listSalarie.getBody().length);

		// retourne une liste d'éléments unique sur le champs fonction
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/critere/fonction", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(2, listSalarie.getBody().length);

		// retourne une liste d'éléments unique sur le champs années expérience
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/critere/années expérience",
				Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(3, listSalarie.getBody().length);

		// retourne une liste vide
		listSalarie = restTest.getForEntity("http://localhost:" + port + "/api/critere/blabla", Salarie[].class);
		assertEquals(HttpStatus.OK, listSalarie.getStatusCode());
		assertEquals(0, listSalarie.getBody().length);
	}
}
