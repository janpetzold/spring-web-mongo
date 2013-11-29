/**
 * Generate a "customers" collection with documents that have the following fields:
 * 
 * firstName, familyName, city, code
 * 
 * The document will be generated based on the data in the people and cities collection. 
 * It will be constructed randomly to produce a basically infinite number of "new" documents.
 */

var con = new Mongo("localhost:30000");
var db = con.getDB('app');

// How many entries shall be generated? Minimum: 100!
var numDocuments = 100000;

var cityCount = db.cities.count();
var peopleCount = db.people.count();

function readRandomData() {
	var firstName = "";
	var familyName = "";
	var cityName = "";

	var randPeople = Math.floor(_rand() * peopleCount) + 1;
	var randCities = Math.floor(_rand() * cityCount) + 1;

	db.people.find({}, {name : 1}).skip(randPeople).limit(-1).forEach(function(people) {
		firstName = people.name;
	});

	//print("Random first name is " + firstName);

	// get new number so that first and last name are not the same
	randPeople = Math.floor(_rand() * peopleCount) + 1;

	db.people.find({}, {surname : 1}).skip(randPeople).limit(-1).forEach(function(people) {
		familyName = people.surname;
	});

	//print("Random family name is " + firstName);

	db.cities.find({}, {accentcity : 1}).skip(randCities).limit(-1).forEach(function(city) {
		cityName = city.accentcity;
	});

	//print("Random city is " + cityName);

	return {
		"firstName" : firstName,
		"familyName" : familyName,
		"city" : cityName,
		"code" : new Date().getTime()
	};
}

// Drop old collection if it exists
db.customers.drop();

// Ensure indexes exist on the queried columns
db.cities.ensureIndex({accentcity: 1 });
db.people.ensureIndex({name: 1 });
db.people.ensureIndex({surname: 1 });

// We will insert the documents in bulks to speed up the process
var bulkCounter = 0;
var personArray = [];

// Track the time it takes to insert all documents
var bench = new Date().getTime();

for(var i = 0; i <= numDocuments; i++) {
	var person = readRandomData();

	bulkCounter = bulkCounter + 1;

	if(person.firstName && person.familyName && person.city && person.code) {
		personArray.push(person);
	}

	// Insert after every 100 entries
	if(bulkCounter >= 100) {
		db.customers.insert(personArray);
		bulkCounter = 0;
		personArray = [];
	}
}

print(max + " entries generated + persisted in " + (new Date().getTime() - bench) + " msec");
