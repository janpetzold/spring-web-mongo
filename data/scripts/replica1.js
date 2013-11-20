/**
 * This script creates a simple replica set.
 * Before executing, start three mongod instances with the config files of this directory and the parameters listed below.
 * Feel free to exchange hist names and ports according to your configuration.

 * Example: mongod --config replica-master.conf
 */

// Set the initial config - members with a higher priority will become masters more likely
var config = {
	"_id" : "starship",
	"members" : [
		{"_id" : 0, "host" : "localhost:30000", "priority" : 2},
		{"_id" : 1, "host" : "localhost:30001", "priority" : 1},
		{"_id" : 2, "host" : "localhost:30002", "priority" : 1},
	]
};

// Initiate the configuration
rs.initiate(config);

// Status will not give us that many details yet - wait a while and execute again
printjson(rs.status());

// Now import data via mongoimport
print("Now execute mongoimport --host localhost:30000 --db app --type json --drop --collection cities < data.json");