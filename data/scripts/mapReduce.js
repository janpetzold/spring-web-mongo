/**
 * Two MapReduce example queries on the cities collection
*/


var db = db.getSiblingDB('app');

/**
 * This is a simple MapReduce query to sum up the city population for each country.
 */
var map = function() {
	emit(this.country, this.population);
};

var reduce = function(country, population) {
	return Array.sum(population);
};

// Truncate results collection
db.resultsTotalPopulation.remove();

var command = db.cities.mapReduce(map, reduce, {out: "resultsTotalPopulation"});
printjson(command);

/*
 * Find out how many towns with more than a million inhabitants a country has and 
 * save their names and population in descending order.
 */
map = function() {
	if(this.population >= 1000000) {
		// Store town details in an object since it will be saved later on
		var details = {
			"name" : this.city,
			"population" : this.population,
			"total" : 1
		};
		emit(this.country, details);
	}
};

reduce = function(country, details) {
	var total = 0;
	var cities = [];
	
	// Count the total number of towns for each country 
	for(var i in details) {
		total += details[i].total;
		cities.push({
			"name" : details[i].name,
			"population" : details[i].population
		});
	}

	// Sort the cities by population and descending
	cities.sort(function(a, b) {
		if(a.population <= b.population) {
			return 1;
		} else if(a.population > b.population)  {
			return -1;
		}
	});

	return {
		"cities": cities,
		"total" : total
	};
};

// Truncate results collection
db.resultsMillionCities.remove();

command = db.cities.mapReduce(map, reduce, {out : "resultsMillionCities",});
printjson(command);