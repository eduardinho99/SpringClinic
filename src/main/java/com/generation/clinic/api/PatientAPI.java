package com.generation.clinic.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.generation.clinic.model.entities.Patient;
import com.generation.clinic.repository.PatientRepository;

@RestController
public class PatientAPI 
{
	@Autowired
	PatientRepository patientrepo;
	
	/*
		GET /patients		=> elenco di tutti i pazienti
		GET /patients/1		=> scheda del paziente 1
		POST /patients (inviare JSON con i dati) => creazione paziente
		PUT /patients  (inviare JSON con i dati) => aggiornamento paziente 
		DELETE /patients/1						 => cancellazione paziente con id = 1
		
		GET /patients/bysurname/ro				=> tutti i pazienti con ro nel cognome
		GET /patients/bygender/f
		
		GET /patients/overage/40				=> tutti i quarantenni
	 */
	
	@GetMapping("/patients")
	public List<Patient> getPatients()
	{
		return patientrepo.findAll();
	}
	
	@GetMapping("/patients/byId/{id}")
	public Optional<Patient> getPatientById(@PathVariable("id") long id)
	{
		return patientrepo.findById(id);
		//Chiedere a ferdinando del perché di Optional<Patient>
	}
	
	@PostMapping("/patients")
	public Patient insertPatient(@RequestBody Patient newPatient)
	{
		return patientrepo.save(newPatient);
	}
	
	@PutMapping("/patients")
	public Patient updatePatient(@RequestBody Patient newPatient)
	{
		return patientrepo.save(newPatient);
	}
	
	@DeleteMapping("/patients/{id}")
	public void deletePatient(@PathVariable("id") long id)
	{
		patientrepo.deleteById(id);
	}
	
	@GetMapping("/patients/bysurname/{surname}")
	public List<Patient> findBySurname(@PathVariable("surname") String surname)
	{
		return patientrepo.findBySurnameContaining(surname);
	}
	
	@GetMapping("/patients/bygender/{gender}")
	public List<Patient> findByGender(@PathVariable("gender") String gender)
	{
		return patientrepo.findByGender(gender);
	}
	
	@GetMapping("/patients/overage/{age}")
	public List<Patient> findByAge(@PathVariable("age") int age)
	{
		return patientrepo.findByAgeGreaterThanEqual(age);
	}
	
}
