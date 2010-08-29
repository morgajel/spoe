-- this may or may not be useful in the future; committing it so I don't lose it.

create table roles(
        id integer not null auto_increment, 
        PRIMARY KEY (id), 
        name varchar(25)
);

create table users(
        id integer not null auto_increment,
        username varchar(32),
        password varchar(32),
        firstname varchar(32),
        lastname varchar(32), 
        enabled boolean default true,
        email varchar(50), 
        role_id integer not null default '2',
        PRIMARY KEY (id),
        FOREIGN KEY (role_id) REFERENCES roles(id)

);


insert  into roles(name) values('ROLE_ANONYMOUS');
insert  into roles(name) values('ROLE_REVIEWER');
insert  into roles(name) values('ROLE_AUTHOR');
insert  into roles(name) values('ROLE_MODERATOR');
insert  into roles(name) values('ROLE_ADMINISTRATOR');
insert  into users(username,password,firstname,lastname,enabled,email,role_id) values('jmorgan',md5('hello'),'Jesse','Morgan',true,'jmorgan@example.com',5);
insert  into users(username,password,firstname,lastname,enabled,email,role_id) values('zswift', md5('hello'),'Ziggy','Swift',true,'ziggy@example.com',3);





