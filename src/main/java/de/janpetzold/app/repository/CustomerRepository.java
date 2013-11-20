package de.janpetzold.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.janpetzold.app.model.Customer;

public interface CustomerRepository  extends MongoRepository<Customer, String> {
	public List<Customer> findByCity(String city);
	
	public List<Customer> findByFirstNameAndFamilyName(String firstName, String familyname);
}
