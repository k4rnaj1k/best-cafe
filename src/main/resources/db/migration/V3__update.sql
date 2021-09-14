alter table users
    add constraint username_unique unique (username);
alter table roles
    add constraint rolename_unique unique (name);