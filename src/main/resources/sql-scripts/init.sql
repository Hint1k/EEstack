/* PostgreSQL script: */

\connect postgres

drop database if exists eestack with (force);
create database eestack;

\connect eestack

drop schema if exists eestack;
create schema eestack;

set search_path = eestack, public;

drop table if exists courier;
create table courier
(
    id         bigserial primary key,
    first_name varchar(25) not null,
    last_name  varchar(45) not null,
    phone      varchar(16) not null
);

drop table if exists address;
create table address
(
    id           bigserial primary key,
    country_name varchar(25) not null,
    city_name    varchar(25) not null,
    street_name  varchar(25) not null,
    house_number varchar(25) not null,
    courier_id   bigint      not null,
    foreign key (courier_id) references courier (id)
);

insert into courier (id, first_name, last_name, phone)
values (1, 'Alex', 'Thompson', '+1234567890');

insert into address (id, country_name, city_name, street_name, house_number, courier_id)
values (1, 'USA', 'New York', '10th Avenue', '10', 1);

SELECT setval('eestack.courier_id_seq', (SELECT COALESCE(MAX(id), 0) FROM courier) + 1, false);
SELECT setval('eestack.address_id_seq', (SELECT COALESCE(MAX(id), 0) FROM address) + 1, false);