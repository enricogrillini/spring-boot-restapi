CREATE ROLE cookbook WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION PASSWORD 'cookbook';
ALTER ROLE cookbook SET search_path TO cookbook;
ALTER ROLE cookbook IN DATABASE postgres SET search_path TO cookbook;
CREATE SCHEMA cookbook AUTHORIZATION cookbook;

set schema 'cookbook';

Create Sequence Seq_IdDocument;

-- Create Table Label
Create Table Document
      (Id integer Not Null,
       code VARCHAR(10) Not Null,
       name VARCHAR(100) Not Null,
       description VARCHAR(100) Not Null
      );

-- Primary Key Document
Alter Table Document Add Constraint Document_PK Primary Key (id);


Insert into Document values (nextval('seq_iddocument'), 'doc-1', 'Contratto', 'Contratto vita');
Insert into Document values (nextval('seq_iddocument'), 'doc-2', 'Allegato-1', 'Allegato 1 - Contratto vita');
Insert into Document values (nextval('seq_iddocument'), 'doc-3', 'Allegato-3', 'Allegato 3 - Contratto vita');