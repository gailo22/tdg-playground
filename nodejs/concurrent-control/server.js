const redis = require('redis');
const path = require("path")
const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const concurrentRoutes = require('./routes/concurrent')

const errorHandler = require('./middlewares/errorHandler');

const app = express();

app.use(cors());

const REDISHOST = process.env.REDISHOST || 'localhost';
const REDISPORT = process.env.REDISPORT || 6379;

const client = redis.createClient(REDISPORT, REDISHOST);
const client2 = redis.createClient(REDISPORT, REDISHOST);

client.on('error', (err) => console.error('ERR:REDIS:', err));
client2.on('error', (err) => console.error('ERR:REDIS:', err));

app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, "public")));

app.use('/api/concurrent', concurrentRoutes);

app.use(errorHandler);

app.listen(8080, () => {
    console.log("Listening on 8080");
});

client.config("set", "notify-keyspace-events", "KEA");

client.subscribe("__keyevent@0__:expired", "__keyevent@0__:del");
client.on("message", (channel, key) => {
  const namespace = key.split(':')[0];
  const ssoid = key.split(':')[1];
  if (namespace == 'ssoid') {
      client2.decr(`count:${ssoid}`, (error, result) => {
        if (error) console.log(error);
        else console.log(result);
      });
      console.log("expired/delete: ", key);
  }
});