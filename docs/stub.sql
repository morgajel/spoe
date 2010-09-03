-- this may or may not be useful in the future; committing it so I don't lose it.

create table role(
        role_id integer not null auto_increment primary key, 
        name varchar(255) not null unique
);

create table account_role (
        account_id integer not null,
        role_id smallint not null,
        CONSTRAINT FOREIGN KEY (role_id) REFERENCES role(role_id),
        CONSTRAINT FOREIGN KEY (account_id) REFERENCES account(account_id),
        primary key (account_id,role_id)
);

create table account(
        account_id integer not null auto_increment primary key,
        email varchar(255) not null unique, 
        username varchar(32) not null unique,
        password char(32) not null,
        enabled boolean default true,
        firstname varchar(255),
        lastname varchar(255), 
        last_access_date        datetime,
        creation_date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        role_id integer not null default 2
);

insert  into role(name) values('ROLE_REVIEWER');
insert  into role(name) values('ROLE_AUTHOR');
insert  into role(name) values('ROLE_MODERATOR');
insert  into role(name) values('ROLE_ADMINISTRATOR');
insert  into account(username,password,firstname,lastname,enabled,email) values('morgajel','hello','Jesse','Morgan',true,'myjaxon@example.com');
insert  into account(username,password,firstname,lastname,enabled,email) values('zswift',  'hello','Ziggy','Swift',true,'ziggy@example.com');
insert  into account(username,password,firstname,lastname,enabled,email) values('myjaxon', 'hello','Jackie','Morgan',true,'myjax@example.com');
insert  into account(username,password,firstname,lastname,enabled,email) values('grunn',   'hello','Grunn','yager',true,'grunn@example.com');
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

select account.username, role.name 
        from account 
            inner join account_role on account.account_id=account_role.account_id 
            inner join role on account_role.role_id=role.role_id order by username,name


