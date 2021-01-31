package com.test.els.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Document(collection = "salaries")
public class Salarie implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "_id")
	private String id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	@Column(name = "dateNaissance")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateNaissance;

	@Column(name = "fonction")
	private String fonction;

	@Column(name = "anneesExperience")
	private int anneesExperience;

	@Column(name = "adresse")
	private String adresse;

	@Column(name = "salaire")
	private double salaire;

	public Salarie() {
	}

	public Salarie(String id, String nom, String prenom, Date dateNaissance, String fonction, int anneesExperience,
			String adresse, double salaire) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.fonction = fonction;
		this.anneesExperience = anneesExperience;
		this.adresse = adresse;
		this.salaire = salaire;
	}

	public Salarie(String nom, String prenom, Date dateNaissance, String fonction, int anneesExperience, String adresse,
			double salaire) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.fonction = fonction;
		this.anneesExperience = anneesExperience;
		this.adresse = adresse;
		this.salaire = salaire;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public int getAnneesExperience() {
		return anneesExperience;
	}

	public void setAnneesExperience(int anneesExperience) {
		this.anneesExperience = anneesExperience;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public double getSalaire() {
		return salaire;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}

	@Override
	public String toString() {
		return "Salarie [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance
				+ ", fonction=" + fonction + ", anneesExperience=" + anneesExperience + ", adresse=" + adresse
				+ ", salaire=" + salaire + "]";
	}

}
