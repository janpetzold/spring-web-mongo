package de.janpetzold.app.repository;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import de.janpetzold.app.model.City;

/**
 * Implementation of the {@link AdvancedCityRepository} for complex queries.
 * 
 * @author Jan Petzold
 *
 */
public class CityRepositoryImpl implements AdvancedCityRepository {
	private static final String COLLECTION = "cities";
	
	private final MongoOperations operations;
	
	@Autowired
	public CityRepositoryImpl(MongoOperations operations) {
		Assert.notNull(operations, "MongoOperations must not be null!");
		this.operations = operations;
	}
	
	/**
	 * Finding a random document inside a collection is not straightforward. We need
	 * to generate a random number, skip the documents based on that and limit
	 * the output. I didn't find a way to do this via Query-DSL or with an annotation.
	 */
	@Override
	public City findRandomCity() {
		Query query = new Query();
		query.skip(this.getRandomNumber());
		query.limit(1);
		return operations.find(query, City.class, COLLECTION).get(0);
	}
	
	private int getRandomNumber() {
		Long range = operations.count(new Query(), COLLECTION);
		int max = range.intValue();
		return new Random().nextInt(max + 1);
	}

}
