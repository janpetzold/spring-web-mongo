package de.janpetzold.app.repository;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import de.janpetzold.app.model.City;

/**
 * The more complex queries that can't be just explained by the DSL or inside query annotations go here
 * 
 * @author jpetzold
 *
 */
public class CityRepositoryImpl implements CustomCityRepository {
	private static final String COLLECTION = "cities";
	
	private final MongoOperations operations;
	
	@Autowired
	public CityRepositoryImpl(MongoOperations operations) {
		Assert.notNull(operations, "MongoOperations must not be null!");
		this.operations = operations;
	}
	
	@Override
	public City findRandomCity() {
		Long range = operations.count(new Query(), COLLECTION);
		int max = range.intValue();
		int randomNum = new Random().nextInt(max + 1);
		
		Query query = new Query();
		query.skip(randomNum);
		query.limit(1);
		return operations.find(query, City.class, COLLECTION).get(0);
	}

}
