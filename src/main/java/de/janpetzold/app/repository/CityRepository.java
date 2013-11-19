package de.janpetzold.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import de.janpetzold.app.model.City;

/**
 * The interface makes use of the {@link Query}-DSL and can be used for all simple operations 
 * where there is no need for in implementation. For more complex quiries, use the .
 * 
 * @author Jan Petzold
 *
 */
@Repository
public interface CityRepository extends MongoRepository<City, String>, CustomCityRepository {
	public City findOneByCity(String city);
	
	public List<City> findByCountry(String country);
	
	public Page<City> findAll(Pageable pageAble);

	@Query("{'city' : 'berlin'}")
	public List<City> test();
	
	//@Query("select u from User u where u.emailAddress = ?1")
	//User findByEmailAddress(String emailAddress);
}
