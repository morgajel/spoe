-- A simple schema and stub accounts


drop table if exists role, account_role, account;


create table role(
        role_id integer      not null auto_increment primary key, 
        name    varchar(255) not null unique
);

create table account_role (
        account_id  integer  not null,
        role_id     smallint not null,
        CONSTRAINT FOREIGN KEY (role_id)    REFERENCES role(role_id),
        CONSTRAINT FOREIGN KEY (account_id) REFERENCES account(account_id),
        primary key (account_id,role_id)
);

create table account(
        account_id          integer      not null auto_increment primary key,
        email               varchar(255) not null unique, 
        username            varchar(32)  not null unique,
        password            char(40)     not null,
        firstname           varchar(255) not null,
        lastname            varchar(255) not null, 
        enabled             boolean      not null default false,
        creation_date       datetime,
        last_access_date    datetime     
);

insert  into role(name) values('ROLE_REVIEWER');
insert  into role(name) values('ROLE_AUTHOR');
insert  into role(name) values('ROLE_MODERATOR');
insert  into role(name) values('ROLE_ADMINISTRATOR');
insert  into account(username,password,firstname,lastname,enabled,email,creation_date,last_access_date) values('jmorgan', sha('hello'),'Jesse', 'Morgan',true,'jmorgan@example.com',now(),now());
insert  into account(username,password,firstname,lastname,enabled,email,creation_date,last_access_date) values('zswift',  sha('hello'),'Ziggy', 'Swift', true,  'ziggy@example.com',now(),now());
insert  into account(username,password,firstname,lastname,enabled,email,creation_date,last_access_date) values('myjaxon', sha('hello'),'Jackie','Morgan',true,  'myjax@example.com',now(),now());
insert  into account(username,password,firstname,lastname,enabled,email,creation_date,last_access_date) values('grunn',   sha('hello'),'Grunn', 'yager', true,  'grunn@example.com',now(),now());
-- morgajel the administrator --
insert  into account_role(account_id,role_id) values('1','1'); 
insert  into account_role(account_id,role_id) values('1','2'); 
insert  into account_role(account_id,role_id) values('1','3');
insert  into account_role(account_id,role_id) values('1','4');
-- ziggy the author --
insert  into account_role(account_id,role_id) values('2','1');
insert  into account_role(account_id,role_id) values('2','2');
-- myjaxon the moderator --
insert  into account_role(account_id,role_id) values('3','1');
insert  into account_role(account_id,role_id) values('3','3');
-- Grunn the Reviewer
insert  into account_role(account_id,role_id) values('4','1');


select  username,role.name as role_name
        from account 
            inner join account_role on account.account_id=account_role.account_id 
            inner join role on account_role.role_id=role.role_id order by username,name;


DROP PROCEDURE IF EXISTS getChecksum;
DELIMITER //
  CREATE PROCEDURE getChecksum(IN username VARCHAR(255),  OUT checksum CHAR(40))
     BEGIN
        select  sha(concat(password,username)) as checksum into checksum
            from account 
                where account.username=username order by username;

    END //
DELIMITER ;


call getChecksum('morgajel',@checksum); select @checksum;



