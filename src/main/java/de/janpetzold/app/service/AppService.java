package de.janpetzold.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.janpetzold.app.model.City;
import de.janpetzold.app.model.Customer;
import de.janpetzold.app.repository.CityRepository;
import de.janpetzold.app.repository.CustomerRepository;
import de.janpetzold.app.utility.SimpleBenchmark;

@Service
public class AppService {
	private CityRepository cityRepository;
	private CustomerRepository customerRepository;

	@Autowired
	public void setRepository(CityRepository repository) {
		this.cityRepository = repository;
	}
	
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public List<City> listCities() {
		return cityRepository.findAll();
    }
	
	public Page<City> findBiggestCities() {
		return cityRepository.findAll(new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "population")));
	}
	
	public City findBiggestCityByName(String name) {
		List<City> cities = cityRepository.findByCity(name.toLowerCase(), new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "population"))).getContent();
		
		if(cities.size() == 1) {
			return cities.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<City> test() {
		return cityRepository.test();
	}
	
	public City getRandom() {
		return cityRepository.findRandomCity();
	}
	
	public City getCity(String name) {
		return cityRepository.findOneByCity(name);
	}
	
	public List<City> getCitiesForCountry(String countryCode) {
		return cityRepository.findByCountry(countryCode);
	}
	
	public City saveCity(City city) {
		City persistedCity = cityRepository.save(city);
		return persistedCity;
	}
	
	public List<Customer> getCustomersForCity(String city) {
		return customerRepository.findByCity(city);
	}

	/**
	 * This would be a classic join on a RDBMS. However, in Mongo we
	 * have to split that into two queries:
	 * 1. Get all cities where there is a user with that name
	 * 2. Determine latitude / longitude of these cities
	 * 
	 * Of course the result has to be returned in a {@link Collection}.
	 * 
	 * Make sure that the following indexes are set (reduces query time from ~300ms to 15 msec):
	 * db.cities.ensureIndex({"city" : 1})
	 * db.customers.ensureIndex({"firstName" : 1, "familyName" : 1})
	 * 
	 * @param userName
	 * @return
	 */
	public Collection<Map<String, Object>> getLocationForUser(String userName) {	
		// Benchmark this query since it is the most expesive one in this app
		SimpleBenchmark benchmark = new SimpleBenchmark(); 
		
		// This will be our result - will be converted into JSON by the controller
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		// User name has to be split
		String firstName = userName.split(" ")[0];
		String familyName = userName.split(" ")[1];
		
		// Find all customers with that name
		List<Customer> customers = customerRepository.findByFirstNameAndFamilyName(firstName, familyName);
		
		// Find the cities of these customers
		if(customers.size() > 0) {
			for(Customer customer : customers) {
				City city = this.findBiggestCityByName(customer.getCity());
				
				// Add customer data to result
				if(city != null) {
					result.add(this.createNode(customer, city));
				}
			}
		}
		
		benchmark.stop("LocationForUser " + firstName + " " + familyName);
		return result;
	}
	
	private Map<String, Object> createNode(Customer customer, City city){
		Map<String, Object> node = new HashMap<String, Object>();
		node.put("familyName", customer.getFamilyName());
		node.put("firstName", customer.getFirstName());
		node.put("city", city.getAccentCity());
		node.put("location", city.getLocation());
		
		return node;
	}
}
