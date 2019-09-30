# php rest application using codeignator

## run
```
$ docker-compose up

```

open browser to `http://localhost:8000/api/customers`


## initialize database
```
$ docker exec -it my-php-app_mariadb_1 bash

MariaDB [(none)]> create database my_app;
MariaDB [(none)]> use my_app;
MariaDB [(none)]> CREATE TABLE IF NOT EXISTS customer (
    ->     id INT AUTO_INCREMENT PRIMARY KEY,
    ->     name VARCHAR(255) NOT NULL,
    ->     age INT,
    ->     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    -> )  ENGINE=INNODB;
Query OK, 0 rows affected (0.011 sec)

MariaDB [my_app]> INSERT INTO customer (name, age) values ("Johnny", 40);
MariaDB [my_app]> INSERT INTO customer (name, age) values ("Jane", 20);
```

## sql script
```sql
CREATE TABLE IF NOT EXISTS customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB;
```