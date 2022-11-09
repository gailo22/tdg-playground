USE eventuate;
DROP table IF EXISTS events;
DROP table IF EXISTS entities;
DROP table IF EXISTS snapshots;
DROP table IF EXISTS message;
DROP table IF EXISTS cdc_monitoring;

create table events (
    event_id varchar(1000) PRIMARY KEY,
    event_type varchar(1000),
    event_data varchar(1000) NOT NULL,
    entity_type VARCHAR(1000) NOT NULL,
    entity_id VARCHAR(1000) NOT NULL,
    triggering_event VARCHAR(1000),
    metadata VARCHAR(1000),
    published TINYINT DEFAULT 0
);

CREATE INDEX events_idx ON events(entity_type, entity_id, event_id);
CREATE INDEX events_published_idx ON events(published, event_id);

create table entities (
    entity_type VARCHAR(1000),
    entity_id VARCHAR(1000),
    entity_version VARCHAR(1000) NOT NULL,
    PRIMARY KEY(entity_type, entity_id)
);
CREATE INDEX entities_idx ON events(entity_type, entity_id);

create table snapshots (
    entity_type VARCHAR(1000),
    entity_id VARCHAR(1000),
    entity_version VARCHAR(1000),
    snapshot_type VARCHAR(1000) NOT NULL,
    snapshot_json VARCHAR(1000) NOT NULL,
    triggering_events VARCHAR(1000),
    PRIMARY KEY(entity_type, entity_id, entity_version)
);
CREATE TABLE message (
    id varchar(767) NOT NULL,
    destination varchar(1000) NOT NULL,
    headers varchar(1000) NOT NULL,
    payload varchar(1000) NOT NULL,
    published smallint(6) DEFAULT '0',
    creation_time bigint(20) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY message_published_idx (published,id)
);
create table received_messages (
    consumer_id varchar(767) NOT NULL,
    message_id varchar(767) NOT NULL,
    creation_time bigint(20) DEFAULT NULL,
    PRIMARY KEY (consumer_id, message_id)
);
create table cdc_monitoring (
    reader_id VARCHAR(1000) PRIMARY KEY,
    last_time BIGINT
);