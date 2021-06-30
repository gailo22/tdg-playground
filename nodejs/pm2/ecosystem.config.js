module.exports = {
  apps : [{
    name   : "app1",
    script : "./app.js",
    instances : "max",
    exec_mode : "cluster",
    env_production: {
      NODE_ENV: "prod",
    },
    env_development: {
        NODE_ENV: "dev"
    }
  }]
}
