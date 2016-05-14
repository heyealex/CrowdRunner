
drop database crowdrunner;
create database crowdrunner;

drop user 'user'@'%';
create user 'user'@'%' identified by '497CrowdRunner';

use crowdrunner;

create table Crowd (
       id int NOT NULL auto_increment,
       name varchar(64) NOT NULL,
       join_date date NOT NULL,
       admin_id varchar(64) NOT NULL,
       
       primary key(id)
);

create table User (
       email varchar(64) NOT NULL,
       name varchar(64) NOT NULL,
       birth_date date NOT NULL,
       join_date date NOT NULL,
       crowd1_id int,
       crowd2_id int,
       crowd3_id int,

       primary key(email),
       foreign key(crowd1_id) references Crowd(id),
       foreign key(crowd2_id) references Crowd(id),
       foreign key(crowd3_id) references Crowd(id)
);

alter table Crowd add foreign key (admin_id) references User(email);

create table Activity (
       id int NOT NULL auto_increment,
       user_email varchar(64) NOT NULL,
       start_date datetime NOT NULL,
       finish_date datetime NOT NULL,
       distance int NOT NULL,
       activity_type varchar(32) NOT NULL,

       primary key(id),
       foreign key(user_email) references User(email)
);

create table UserChallenge (
       id int NOT NULL auto_increment,
       user_email varchar(64) NOT NULL,
       title varchar(64) NOT NULL,
       start_date date NOT NULL,
       end_date date NOT NULL,
       completed_distance int NOT NULL,
       total_distance int NOT NULL,

       primary key(id),
       foreign key(user_email) references User(email)
);

create table CrowdChallenge (
       id int NOT NULL auto_increment,
       crowd_id int NOT NULL,
       title varchar(64) NOT NULL,
       start_date date NOT NULL,
       end_date date NOT NULL,
       completed_distance int NOT NULL,
       total_distance int NOT NULL,

       primary key(id),
       foreign key(crowd_id) references Crowd(id)
);

grant select, update, insert, delete on crowdrunner.* to 'user'@'%';
