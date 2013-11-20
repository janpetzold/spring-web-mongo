/**
 * Once the replica set is started and working, we can try a few things like reconfiguration
 or starting / stopping of nodes. See the examples here.
 */
var conMaster = new Mongo("localhost:30000");
var conSec1 = new Mongo("localhost:30001");
var conSec2 = new Mongo("localhost:30002");

var db = conMaster.getDB('app');
var db1 = conSec1.getDB('app');
var db2 = conSec2.getDB('app');

// Allow reading from secondaries
db1.getMongo().setSlaveOk();
db2.getMongo().setSlaveOk();

print("Master collection has " + db.cities.count() +  " entries. It takes " + db.cities.dataSize() + " bytes of memory (without indexes).");
print("Secondary1 collection has " + db1.cities.count() +  " entries.");
print("Secondary2 collection has " + db2.cities.count() +  " entries.");

// We could simply add and remove servers like this
//rs.add("localhost:30003");
//rs.remove("localhost:30003");

// This has to fail - we can't write on secondaries
print("");
print("Trying to write something on secondary...");
db2.cities.insert({"foo" : "bar"});
//printjson(db2.runCommand({"getLastError" : 1}));

// Let's make the third server hidden and add a slave delay of 60 seconds
var config = rs.config();
// We need to reduce priority and set the hidden flag so the secondary2 won't get queried
config.members[2].priority = 0;
config.members[2].hidden = 1;
// Set the delay of data insertion to 60 seconds
config.members[2].slaveDelay = 60;
// No need to have any indexes on that member
config.members[2].buildIndexes = false;
rs.reconfig(config);

printjson(rs.isMaster());
print("");

// Now insert a new city in the Master
print("Inserting new city");
//db.cities.insert({"country" : "de", "city" : "dummytown", "accentcity" : "Dummytown", "population" : 3});

print("Reading from secondary 1");
db1.cities.find({"city" : "dummytown"}).forEach(printjson);

print("Reading from secondary 2 - should not have the new town yet");
db2.cities.find({"city" : "dummytown"}).forEach(printjson);

// Shutting down Secondary2
print("Shutting down server2");
//db2.adminCommand({"shutdown": 1});

print("Now shutdown the master process and see if secondary1 became the new master. Use rs.status() for that.");