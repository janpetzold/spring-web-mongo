package de.janpetzold.app.repository;

import org.springframework.data.mongodb.core.query.Query;

import de.janpetzold.app.model.City;

/**
 * Some queries can't be described in the query DSL or with the {@link Query} annotation. 
 * These ones will be implemented in the dedicated {@link CityRepositoryImpl}.
 *  
 * @author Jan Petzold
 *
 */
public interface AdvancedCityRepository {
	/**
	 * Find a random city in the collection.
	 * @return {@link City}
	 */
	City findRandomCity();
}
