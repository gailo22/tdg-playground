# Cassandra

### Commands
```
$ docker-compose up -d n1

$ docker-compose exec n1 nodetool status

$ docker-compose exec n1 nodetool ring

$ cat scripts/data.cql | docker-compose exec -T n1 cqlsh

$ docker-compose exec n1 cqlsh
Connected to Test Cluster at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 3.11.10 | CQL spec 3.4.4 | Native protocol v4]
Use HELP for help.
cqlsh> consistency;
Current consistency level is ONE.
cqlsh> SELECT * FROM store.shopping_cart;

```

### Cleanup
```
$ docker-compose down
```

### Other commands
```
cqsql> create keyspace sample with replication = {'class':'SimpleStrategy', 'replication_factor':3};

$ docker-compose exec n1 nodetool describering sample

cqlsh> use sample;
cqlsh> create table courses (id varchar primary key);

cqlsh> drop table courses;
cqlsh> create table courses (
        id varchar primary key,
        name varchar,
        author varchar,
        audience int,
        duration int,
        cc boolean,
        released timestamp
        ) with comment = 'A table of courses';

cqlsh> desc table courses;

cqlsh> create table course_page_views (
        bucket_id varchar,
        course_id varchar,
        last_view_id timeuuid static,
        view_id timeuuid,
        primary key ((bucket_id, course_id), view_id)
    ) with clustering order by (view_id desc);

cqlsh> alter table users add last_login map<varchar,frozen<tuple<timestamp,inet>>>;
cqlsh> update users set last_login = last_login + {'3383cc0867cd2':('2019-10-30 09:02:24','98.203.153.64')} where id = 'john-doe';
cqlsh> select last_login from users where id = 'john-doe';


cqlsh> create materialized view users_by_company as
    select * from users
    where company is not null
    primary key (company, id);

cqlsh> create index on users(company);
cqlsh> select * from users where company = 'Acme Corp';

````