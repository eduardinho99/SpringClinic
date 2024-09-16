package com.generation.clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.clinic.model.entities.Patient;


public interface PatientRepository extends JpaRepository<Patient, Long>
{
	/*
	 *  GET /patients		=> elenco di tutti i pazienti
		GET /patients/1		=> scheda del paziente 1
		POST /patients (inviare JSON con i dati) => creazione paziente
		PUT /patients  (inviare JSON con i dati) => aggiornamento paziente 
		DELETE /patients/1						 => cancellazione paziente con id = 1
		
		GET /patients/bysurname/ro				=> tutti i pazienti con ro nel cognome
		GET /patients/bygender/f
		
		GET /patients/overage/40				=> tutti i quarantenni
	 */
	
	List<Patient> findBySurnameContaining(String n);
	List<Patient> findByGender(String gender);
	List<Patient> findByAgeGreaterThanEqual(int age);
}
