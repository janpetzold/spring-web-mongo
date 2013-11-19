package de.janpetzold.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.janpetzold.app.model.City;
import de.janpetzold.app.repository.CityRepository;

@Service
public class CityService {
	private CityRepository repository;

	@Autowired
	public void setRepository(CityRepository repository) {
		this.repository = repository;
	}
	
	public List<City> listCities() {
		return repository.findAll();
    }
	
	public Page<City> findBiggestCities() {
		return repository.findAll(new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "population")));
	}
	
	public List<City> test() {
		return repository.test();
	}
	
	public City getRandom() {
		return repository.findRandomCity();
	}
	
	public City getCity(String name) {
		return repository.findOneByCity(name);
	}
	
	public List<City> getCitiesForCountry(String countryCode) {
		return repository.findByCountry(countryCode);
	}
	
	public City saveCity(City city) {
		City persistedCity = repository.save(city);
		return persistedCity;
	}
}
