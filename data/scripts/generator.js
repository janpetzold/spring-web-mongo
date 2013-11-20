var con = new Mongo("localhost:30000");
var db = con.getDB('app');

// How many entries shall be generated? Minimum: 100!
var max = 100000;

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

db.customers.drop();

// ensure indexes on the queried columns
db.cities.ensureIndex({accentcity: 1 });
db.people.ensureIndex({name: 1 });
db.people.ensureIndex({surname: 1 });

// insert data in bulks to speed up the process
var bulkCounter = 0;
var personArray = [];

// Track the time it takes to insert all documents
var bench = new Date().getTime();

for(var i = 0; i <= max; i++) {
	var person = readRandomData();

	bulkCounter = bulkCounter + 1;

	if(person.firstName && person.familyName && person.city && person.code) {
		personArray.push(person);
	}

	if(bulkCounter >= 100) {
		db.customers.insert(personArray);
		bulkCounter = 0;
		personArray = [];
	}
}

print(max + " entries generated + persisted in " + (new Date().getTime() - bench) + " msec");
