const redis = require('redis');
const Post = require("../models/concurrent");

const REDISHOST = process.env.REDISHOST || 'localhost';
const REDISPORT = process.env.REDISPORT || 6379;

const client = redis.createClient(REDISPORT, REDISHOST);
client.on('error', (err) => console.error('ERR:REDIS:', err));

exports.get = async (req, res, next) => {
  try {
      const ssoid = req.params.id;
      const deviceId = req.params.deviceId;
      const timestamp = new Date().getTime();
      const key = `ssoid:${ssoid}:${deviceId}`;

      client.exists(key, (err, rexists) => {
        if (rexists == false) {
          console.log("not exists: ", key);
          client.incr(`count:${ssoid}`, (error, result) => {
            if (error) console.log(error);
            else console.log(result);
          });
        }
      });

      client.set(key, timestamp, 'EX', '60', (err, reply) => {
      if (err) {
        console.log(err);
        res.status(500).send(err.message);
        return;
      }

      res.send(`Visitor number: ${reply}\n`);
    });
  } catch (err) {
    next(err);
  }

};


