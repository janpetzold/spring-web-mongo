// TODO: Create replica set

var db = db.getSiblingDB('app');

print("Creating index for the city names");
db.cities.ensureIndex({ city: 1 });
printjson(db.cities.getIndexes());