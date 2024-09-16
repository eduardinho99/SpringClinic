package com.generation.clinic.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.clinic.model.entities.Gender;
import com.generation.clinic.model.entities.Patient;
import com.generation.clinic.repository.PatientRepository;

@RestController
@RequestMapping("/patients")
//aggiungo un prefisso per ogni richiesta
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
	
	
	/*
	 * HARDENING DEL CODICE
	 * rendere il codice più sicuro mettendo dei controlli
	 * per eventuali errori dell'Utente
	 */
	
	@GetMapping("")
	public ResponseEntity<List<Patient>> getPatients()
	{
		List<Patient> patient = patientrepo.findAll();
		return 		patient.isEmpty() ? 
					new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
					new ResponseEntity<List<Patient>>(patient, HttpStatus.OK);
	}
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable("id") long id)
	{
		Optional<Patient> patient = patientrepo.findById(id);
		return 		patient.isEmpty() ? 
					new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
					new ResponseEntity<Patient>(patient.get(), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<Patient> insertPatient(@RequestBody Patient newPatient)
	{
		Optional<Patient> patient = patientrepo.findById((long)newPatient.getId());
		
		if(patient.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try 
		{
			newPatient = patientrepo.save(newPatient);
			return new ResponseEntity<Patient>(newPatient, HttpStatus.CREATED);
			
		} catch 
		(Exception e) 
		{
			e.printStackTrace(); //il messaggio viene stampato solo in console, l'utente non vedrà nulla
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);			
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient oldPatient)
	{
		Optional<Patient> patient = patientrepo.findById((long)oldPatient.getId());
		
		if(patient.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try 
		{
			oldPatient = patientrepo.save(oldPatient);
			return new ResponseEntity<Patient>(oldPatient, HttpStatus.CREATED);
			
		} catch 
		(Exception e) 
		{
			e.printStackTrace(); //il messaggio viene stampato solo in console, l'utente non vedrà nulla
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);			
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePatient(@PathVariable("id") long id)
	{
		Optional<Patient> patient = patientrepo.findById(id);
		
		if(patient.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try 
		{
			patientrepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch 
		(Exception e) 
		{
			e.printStackTrace(); //il messaggio viene stampato solo in console, l'utente non vedrà nulla
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);			
		}
	}
	
	@GetMapping("/bysurname/{surname}")
	public ResponseEntity<List<Patient>> findBySurname(@PathVariable("surname") String surname)
	{
		List<Patient> patient = patientrepo.findBySurnameContaining(surname);
		return 		patient.isEmpty() ? 
					new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
					new ResponseEntity<List<Patient>>(patient, HttpStatus.OK);
	}
	
	@GetMapping("/bygender/{gender}")
	public ResponseEntity<List<Patient>> findByGender(@PathVariable("gender") String gender)
	{
		List<Patient> patient = patientrepo.findByGender(gender);
		return 		patient.isEmpty() ? 
					new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
					new ResponseEntity<List<Patient>>(patient, HttpStatus.OK);
	}
	
	@GetMapping("/overage/{age}")
	public ResponseEntity<List<Patient>> findByAge(@PathVariable("age") int age)
	{
		List<Patient> patient = patientrepo.findByAgeGreaterThanEqual(age);
		return 		patient.isEmpty() ? 
					new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
					new ResponseEntity<List<Patient>>(patient, HttpStatus.OK);
	}
	
}
