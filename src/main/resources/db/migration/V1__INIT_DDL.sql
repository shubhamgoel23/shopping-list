drop table if exists item CASCADE;
drop table if exists shopping_list CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table item (
       id bigint not null,
        created_on bigint not null,
        updated_on bigint,
        version bigint,
        product_id varchar(255) not null,
        quantity integer not null,
        shopping_list_id bigint,
        primary key (id)
    );

create table shopping_list (
       id bigint not null,
        created_on bigint not null,
        updated_on bigint,
        version bigint,
        list_id varchar(255) not null,
        name varchar(255) not null,
        type varchar(255) not null,
        primary key (id)
    );

alter table shopping_list 
       add constraint UK_ln2l5iudbqoful3p6jwpp4esf unique (list_id);

alter table shopping_list 
       add constraint UK_6sh67ne8a70wkdcikf84g943a unique (name);
       
alter table item 
       add constraint FKqkk0snemlo1jwc1w76db8hn60 
       foreign key (shopping_list_id) 
       references shopping_list;
       
    