var db = db.getSiblingDB('app');

// TODO: What about write consistency?

print("Creating index for the city names");
db.cities.ensureIndex({ city: 1 });
printjson(db.cities.getIndexes());

print("Creating index for the customers first - and familyName");
db.customers.ensureIndex({"firstName" : 1, "familyName" : 1});
printjson(db.customers.getIndexes());

print("Repairing database - caution: DO NOT USE THIS IN PRODUCTION WITH LIVE DATABASES!");
//db.repairDatabase();

print("Collection stats:");
printjson(db.cities.stats());
printjson(db.customers.stats());

print("Current configuration of replica set:");
printjson(rs.config());

print("Status of replica set:");
printjson(rs.status());

print("Moving collections to RAM");
db.runCommand({ touch: "cities", data: true, index: true });
db.runCommand({ touch: "customers", data: true, index: true });

