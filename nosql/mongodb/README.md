# Mongodb replicaset

```
$ docker network create my-mongo-cluster

```

### start mongo1
```
$ docker run \
-p 30001:27017 \
--name mongo1 \
--net my-mongo-cluster \
mongo mongod --replSet my-mongo-set

```

### start mongo2 and mongo3
```
$ docker run \
-p 30002:27017 \
--name mongo2 \
--net my-mongo-cluster \
mongo mongod --replSet my-mongo-set

$ docker run \
-p 30003:27017 \
--name mongo3 \
--net my-mongo-cluster \
mongo mongod --replSet my-mongo-set

```

### connect to server using mongosh
```
$ mongosh mongodb://localhost:30001

test> config = {
     "_id" : "my-mongo-set",
     "members" : [
         {
               "_id" : 0,
               "host" : "mongo1:27017"
           },
         {
               "_id" : 1,
               "host" : "mongo2:27017"
           },
         {
               "_id" : 2,
               "host" : "mongo3:27017"
           }
     ]
 }

test> rs.initiate(config)
{
  ok: 1,
  '$clusterTime': {
    clusterTime: Timestamp({ t: 1636688601, i: 1 }),
    signature: {
      hash: Binary(Buffer.from("0000000000000000000000000000000000000000", "hex"), 0),
      keyId: Long("0")
    }
  },
  operationTime: Timestamp({ t: 1636688601, i: 1 })
}

my-mongo-set [direct: secondary] test> rs.config()
{
  _id: 'my-mongo-set',
  version: 1,
  term: 1,
  members: [
    {
      _id: 0,
      host: 'mongo1:27017',
      arbiterOnly: false,
      buildIndexes: true,
      hidden: false,
      priority: 1,
      tags: {},
      secondaryDelaySecs: Long("0"),
      votes: 1
    },
    {
      _id: 1,
      host: 'mongo2:27017',
      arbiterOnly: false,
      buildIndexes: true,
      hidden: false,
      priority: 1,
      tags: {},
      secondaryDelaySecs: Long("0"),
      votes: 1
    },
    {
      _id: 2,
      host: 'mongo3:27017',
      arbiterOnly: false,
      buildIndexes: true,
      hidden: false,
      priority: 1,
      tags: {},
      secondaryDelaySecs: Long("0"),
      votes: 1
    }
  ],
  protocolVersion: Long("1"),
  writeConcernMajorityJournalDefault: true,
  settings: {
    chainingAllowed: true,
    heartbeatIntervalMillis: 2000,
    heartbeatTimeoutSecs: 10,
    electionTimeoutMillis: 10000,
    catchUpTimeoutMillis: -1,
    catchUpTakeoverDelayMillis: 30000,
    getLastErrorModes: {},
    getLastErrorDefaults: { w: 1, wtimeout: 0 },
    replicaSetId: ObjectId("618de2d9682c1f9844c2d90c")
  }
}

my-mongo-set [direct: primary] test> rs.status()
...
```

### test insert data
```
my-mongo-set [direct: primary] test> db.mycollection.insert({name : 'sample'})

my-mongo-set [direct: primary] test> db.mycollection.find()
[
  { _id: ObjectId("618dedcc746f82e0c854a038"), name: 'sample' }
]
```