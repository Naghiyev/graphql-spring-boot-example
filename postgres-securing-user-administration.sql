alter table userz add column user_role varchar(20);

update userz set user_role = 'ROLE_MEMBER';

update userz set user_role = 'ROLE_ADMIN'
 where username = 'jbrameg';
