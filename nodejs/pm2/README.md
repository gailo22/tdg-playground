# pm2 nodejs clustering app


## create project
```
$ npm init
$ npm install express
```

## start
```
$ pm2 start ecosystem.config.js --env dev
```

## testing
```
$ pm2 logs

$ siege http://localhost:3000
```
