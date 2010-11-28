-- A simple schema and stub accounts


drop table if exists role, account_role, account, snippet,improtocol,experience;


create table role (
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

create table account (
        account_id          integer      not null auto_increment primary key,
        email               varchar(255) not null unique, 
        username            varchar(32)  not null unique,
        password            char(40)     not null,
        firstname           varchar(255) not null,
        lastname            varchar(255) not null, 
        primary_im_id       smallint     not null default 0,
        primary_im_name     varchar(128) not null default '',
        secondary_im_id     smallint     not null default 0,
        secondary_im_name   varchar(128) not null default '',
        writing_exp_id      smallint     not null default 0,
        reviewing_exp_id    smallint     not null default 0,
        enabled             boolean      not null default false,
        creation_date       datetime,
        last_modified_date    datetime,
        CONSTRAINT FOREIGN KEY (primary_im_id)    REFERENCES improtocol(im_id),
        CONSTRAINT FOREIGN KEY (secondary_im_id)  REFERENCES improtocol(im_id),
        CONSTRAINT FOREIGN KEY (writing_exp_id)   REFERENCES experience(experience_id),
        CONSTRAINT FOREIGN KEY (reviewing_exp_id) REFERENCES experience(experience_id)
);

create table experience (
        experience_id       smallint     not null auto_increment primary key,
        name                varchar(32)  not null unique
);
insert into experience(name) values('none');
insert into experience(name) values('novice');
insert into experience(name) values('minor');
insert into experience(name) values('intermediate');
insert into experience(name) values('deep');
insert into experience(name) values('professional');

create table improtocol (
        im_id               smallint     not null auto_increment primary key,
        im_protocol         varchar(32)  not null unique
);
insert into improtocol(im_protocol) values('none');
insert into improtocol(im_protocol) values('AIM');
insert into improtocol(im_protocol) values('ICQ');
insert into improtocol(im_protocol) values('GTalk');
insert into improtocol(im_protocol) values('Jabber');
insert into improtocol(im_protocol) values('Yahoo!');

create table snippet (
        snippet_id          integer      not null unique auto_increment primary key,
        title               varchar(255) not null, 
        account_id          integer      not null,
        published           boolean      default false,
        CONSTRAINT FOREIGN KEY (account_id) REFERENCES account(account_id),
        last_modified_date  timestamp    not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        creation_date       datetime,
        content             mediumtext   not null



);



insert into snippet(title,account_id,content ) values('Text test1',1,'first block of text');
insert into snippet(title,account_id,content ) values('Text test2',2,'second block of text');
insert into snippet(title,account_id,content ) values('Text test3',1,'third block of text');
insert into snippet(title,account_id,content ) values('Text test4',1,'block of text');
insert into snippet(title,account_id,content ) values('Text test5',3,'5 block of text');
insert into snippet(title,account_id,content ) values('Text test6',3,'6 block of text');
insert into snippet(title,account_id,content ) values('Text test7',2,'7 block of text');
insert into snippet(title,account_id,content ) values('Text test8',2,'8 block of text');
insert into snippet(title,account_id,content ) values('Text test9',1,'9 block of text');
insert into snippet(title,account_id,content ) values('Text test10',1,'10 block of text');
insert into snippet(title,account_id,content,published ) values('Text Sample 1',1,'A simple block of text for Sample 1',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 2',2,'A simple block of text for Sample 2',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 3',3,'A simple block of text for Sample 3',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 4',4,'A simple block of text for Sample 4',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 5',1,'A simple block of text for Sample 5',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 6',2,'A simple block of text for Sample 6',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 7',3,'A simple block of text for Sample 7',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 8',4,'A simple block of text for Sample 8',true);
insert into snippet(title,account_id,content,published ) values('Text Sample 9',2,'A simple block of text for Sample 9',true);
insert into snippet(title,account_id,content,published ) values('Text Sample10',2,'A simple block of text for Sample10',true);



insert into role(name) values('ROLE_REVIEWER');
insert into role(name) values('ROLE_AUTHOR');
insert into role(name) values('ROLE_MODERATOR');
insert into role(name) values('ROLE_ADMINISTRATOR');
insert into account(username,password,firstname,lastname,enabled,email,creation_date,last_modified_date,
        primary_im_id,primary_im_name,
        secondary_im_id,secondary_im_name,
        writing_exp_id,reviewing_exp_id) values('jmorgan', sha('hello'),'Jesse', 'Morgan',true,'jmorgan@example.com',now(),now(),3,'morgajel',5,'morgajel',2,4);
insert into account(username,password,firstname,lastname,enabled,email,creation_date,last_modified_date) values('zswift',  sha('hello'),'Ziggy', 'Swift', true,  'ziggy@example.com',now(),now());
insert into account(username,password,firstname,lastname,enabled,email,creation_date,last_modified_date) values('myjaxon', sha('hello'),'Jackie','Morgan',true,  'myjax@example.com',now(),now());
insert into account(username,password,firstname,lastname,enabled,email,creation_date,last_modified_date) values('grunn',   sha('hello'),'Grunn', 'yager', true,  'grunn@example.com',now(),now());
-- jmorgan the administrator --
insert into account_role(account_id,role_id) values('1','1'); 
insert into account_role(account_id,role_id) values('1','2'); 
insert into account_role(account_id,role_id) values('1','3');
insert into account_role(account_id,role_id) values('1','4');
-- ziggy the author --
insert into account_role(account_id,role_id) values('2','1');
insert into account_role(account_id,role_id) values('2','2');
-- myjaxon the moderator --
insert into account_role(account_id,role_id) values('3','1');
insert into account_role(account_id,role_id) values('3','3');
-- Grunn the Reviewer
insert into account_role(account_id,role_id) values('4','1');


select username,role.name as role_name
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



