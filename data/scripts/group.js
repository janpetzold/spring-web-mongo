/**
 * This is a simple aggregation that finds the biggest town for each country
 */
var db = db.getSiblingDB('app');

// First, let's see how many countries we have
var command = db.runCommand({"distinct" : "cities", "key" : "country"});
print("We have " + command.values.length + " countries in the db");

// Now list the biggest town of each country with its population
command = db.cities.group(
	{
		key : {
			"country" : true
		},
		initial : {
			"population" : 0
		},
		reduce : function(doc, prev) {
			if(doc.population > prev.population) {
				prev.accentcity = doc.accentcity;
				prev.population = doc.population;
			}
		}
	}
);
printjson(command);