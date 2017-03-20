select * from employee
select * from employee_info
select * from department
select * from guest
select * from payment
select * from rent
select * from room
select * from check_out
select * from check_in

insert into payment values(1,2,202,675,'08-DEC-12','01/DEC/12 - 31/DEC/12')
insert into payment values(2,4,406,1500,'15-OCT-12','01/OCT/12 - 31/OCT/12')
insert into payment values(3,4,406,570,'20-MAR-13','15/MAR/13 - 15/APR/13')

insert into payment values(7,2,402,800,'08-DEC-12','01/DEC/12 - 31/DEC/12')
1. --create a maaterialized view to show the all payments that guests due.
drop materialized view mview_guest_payment;
create materialized view mview_guest_payment
refresh force 
on demand
start with SysDate Next SysDate 
with primary key
as
SELECT Last_name, First_name, Room_id, Rent, due_date
FROM Guest,rent
WHERE Guest.Guest_id = Rent.Guest_id;

select * from mview_guest_payment;
--insert into rent values (6, 201, '13-JAN-13', 800, '01-JAN-13', '31-JAN-13');
delete from rent 
where guest_id = 6 and due_date = '13-JAN-13';

select * from rent;
select * from mview_guest_payment;
execute DBMS_MVIEW.REFRESH('mview_guest_payment', 'f');
execute DBMS_MVIEW.REFRESH('mview_guest_payment', 'c');
select * from mview_guest_payment;

--runs when using refresh fast
drop materialized view log on rent;
drop materialized view log on guest;
create materialized view log on rent with rowid;
create materialized view log on guest with rowid;
select * from MLOG$_rent;
select * from MLOG$_guest;

----------------------------------------------------------------------

drop materialized view mview_guest;
create materialized view mview_guest
refresh force 
on demand
start with SysDate Next SysDate + 2
with primary key
as
SELECT Last_name, First_name, GENDER, DATE_OF_BIRTH, ADDRESS_1
FROM Guest

select * from mview_guest;
select * from guest
--insert into guest values (8, '10-SEP-85', '3252 Broad Wagon Pines, Waldheim, District of Columbia', 812, '317-785-5454', 'Jaychou@gmail.com', 'JAY', 'Chou', 'Male');
delete from guest where guest_id = 8;

drop materialized view log on guest;
create materialized view log on guest with rowid; 
select * from MLOG$_guest;
execute DBMS_MVIEW.REFRESH('mview_guest', 'c');
select * from mview_guest;
-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------

2. --Create a trigger to keep track of the insertion and update on the rent table
drop table RENT__LOG
create table RENT__LOG (
GUEST_ID INT NOT NULL,
ROOM_ID VARCHAR2(20),
DUE_DATE VARCHAR2(20),
RENT VARCHAR2(20),
BEGIN_DATE VARCHAR2(20),
END_DATE VARCHAR2(20),
PREV_ROOM_ID VARCHAR2(20),
PREV_DUE_DATE VARCHAR2(20),
PREV_RENT VARCHAR2(20),
PREV_BEGIN_DATE VARCHAR2(20),
PRE_END_DATE VARCHAR2(20),
MOD_USER VARCHAR2(20),
MOD_TIMESTAMP VARCHAR2(20)
)

drop trigger RENT_TRIGGER
create or replace trigger RENT_TRIGGER 
after insert or update on rent
for each row
begin
  if INSERTING then
    insert into RENT__LOG(GUEST_ID,MOD_USER,MOD_TIMESTAMP)
    values(:new.GUEST_ID,ora_dict_obj_name,Sysdate);
  else
    insert into RENT__LOG
      (GUEST_ID, PREV_ROOM_ID, PREV_DUE_DATE, PREV_RENT, PREV_BEGIN_DATE, PRE_END_DATE, MOD_USER, MOD_TIMESTAMP)
    values(:old.GUEST_ID,:old.ROOM_ID, :old.DUE_DATE, :old.RENT, :old.BEGIN_DATE, :old.end_date, ora_dict_obj_name, Sysdate);
      end if;
end;

select * from rent
order by guest_id;

insert into rent 
values (9, 204, '07-MAR-13', 500, '05-FEB-13', '07-MAR-13');

update rent
set rent = 400
where guest_id = 9;



delete from rent where guest_id = 9

select * from RENT__LOG
----------------------------------------------------------------------------------------------------
--create a trigger to keep track of the rent table when deleting.

drop trigger RENT_DELETED
Create or replace trigger RENT_DELETED
before delete on RENT
for each row
begin
Insert into RENT__LOG(GUEST_ID, ROOM_ID, DUE_DATE, RENT, BEGIN_DATE, END_DATE, MOD_USER, MOD_TIMESTAMP) 
values (:old.GUEST_ID, :old.ROOM_ID, :old.DUE_DATE, :old.RENT, :old.BEGIN_DATE, :old.END_DATE, ora_dict_obj_name, Sysdate);
end;

select * from RENT__LOG

delete from rent 
where guest_id = 9

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------

3.--create a function to calculate the age of the input guest.
select * from guest

create or replace function FN_COUNT_AGE(A_FIRST_NAME IN VARCHAR, A_LAST_NAME IN VARCHAR)
return number
is
GUEST_AGE number(3,0); 
begin
  select trunc((sysdate - DATE_OF_BIRTH)/365, 0) 
  into GUEST_AGE 
  from guest
  where FIRST_NAME = A_FIRST_NAME and LAST_NAME = A_LAST_NAME;
RETURN (GUEST_AGE);
end;

select  FN_COUNT_AGE('Ericka', 'Hilderbrand') from dual
----------------------------------------------------------------------------------------------------
--create a function to calculate the total due of the input guest
select * from rent

create or replace function FN_COUNT_Total_due(A_FIRST_NAME IN VARCHAR, A_LAST_NAME IN VARCHAR)
return number
is
TOTAL_DUE number(8,0); 
begin
  select sum(rent) 
  into TOTAL_DUE
  from rent
  where guest_id = (select guest_id 
                    from guest
                    where FIRST_NAME = A_FIRST_NAME and LAST_NAME = A_LAST_NAME)
  group by guest_id;
RETURN (TOTAL_DUE);
end;
 

--insert into rent values (6,201, '07-MAR-13',100,'05-FEB-13','05-FEB-13')
 
select  FN_COUNT_Total_due('Ericka', 'Hilderbrand') from dual
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------

4. --creaate a procedure to show all the user status who has due more than x dollers.
drop table user__status
create table user__status (
first_name VARCHAR2(16),
last_name VARCHAR2(16),
age int,
rent int,
room_id int
)

select * from user_status
select * from rent
select * from guest

CREATE OR REPLACE PROCEDURE SP_USERS_STATUS(amount in int)
AS
sp_first_name VARCHAR2(16);
sp_last_name VARCHAR2(16);
SP_age int;
SP_rent int;
SP_room_id int;
CURSOR  RENT_CURSOR IS
  SELECT * FROM RENT;
  RENT_VAL RENT_CURSOR%ROWTYPE;
BEGIN
  EXECUTE IMMEDIATE 'TRUNCATE TABLE user__status';
    FOR RENT_VAL IN RENT_CURSOR
    LOOP
      if RENT_VAL.rent < amount then
        continue;
      end if;
      select first_name, last_name into sp_first_name, sp_last_name
        from guest 
        where guest_id = RENT_VAL.guest_id;
      SP_age        := FN_COUNT_AGE(sp_first_name, sp_last_name);
      SP_rent       := FN_COUNT_Total_due(sp_first_name, sp_last_name);
      SP_room_id    := RENT_VAL.ROOM_ID;
      INSERT INTO user__status
        VALUES (sp_first_name,sp_last_name,SP_age,SP_rent,SP_room_id);
    END LOOP;    
END;


EXECUTE SP_USERS_STATUS(100);
select distinct * from user__status

-------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------

5. 
--create a package to combine all the functions and procedure defined above.
--Package header
DROP PACKAGE PACK_USER_STATUS
CREATE OR REPLACE PACKAGE PACK_USER_STATUS
AS
  FUNCTION FN_COUNT_AGE(A_FIRST_NAME IN VARCHAR, A_LAST_NAME IN VARCHAR) return number;
  FUNCTION FN_COUNT_Total_due(A_FIRST_NAME IN VARCHAR, A_LAST_NAME IN VARCHAR) return number;
  PROCEDURE SP_USERS_STATUS(amount in int);
END PACK_USER_STATUS;

--Package Body
CREATE OR REPLACE PACKAGE BODY PACK_USER_STATUS
AS
--function      FN_COUNT_AGE
function FN_COUNT_AGE(A_FIRST_NAME IN VARCHAR, A_LAST_NAME IN VARCHAR)
return number
is
GUEST_AGE number(3,0); 
begin
  select trunc((sysdate - DATE_OF_BIRTH)/365, 0) 
  into GUEST_AGE 
  from guest
  where FIRST_NAME = A_FIRST_NAME and LAST_NAME = A_LAST_NAME;
RETURN (GUEST_AGE);
end FN_COUNT_AGE;

--function      FN_COUNT_Total_due
function FN_COUNT_Total_due(A_FIRST_NAME IN VARCHAR, A_LAST_NAME IN VARCHAR)
return number
is
TOTAL_DUE number(8,0); 
begin
  select sum(rent) 
  into TOTAL_DUE
  from rent
  where guest_id = (select guest_id 
                    from guest
                    where FIRST_NAME = A_FIRST_NAME and LAST_NAME = A_LAST_NAME)
  group by guest_id;
RETURN (TOTAL_DUE);
end FN_COUNT_Total_due;

--PROCEDURE      SP_USERS_STATUS
PROCEDURE SP_USERS_STATUS(amount in int)
AS
sp_first_name VARCHAR2(16);
sp_last_name VARCHAR2(16);
SP_age int;
SP_rent int;
SP_room_id int;
CURSOR  RENT_CURSOR IS
  SELECT * FROM RENT;
  RENT_VAL RENT_CURSOR%ROWTYPE;
BEGIN
  EXECUTE IMMEDIATE 'TRUNCATE TABLE user__status';
    FOR RENT_VAL IN RENT_CURSOR
    LOOP
      if RENT_VAL.rent < amount then
        continue;
      end if;
      select first_name, last_name into sp_first_name, sp_last_name
        from guest 
        where guest_id = RENT_VAL.guest_id;
      SP_age        := FN_COUNT_AGE(sp_first_name, sp_last_name);
      SP_rent       := FN_COUNT_Total_due(sp_first_name, sp_last_name);
      SP_room_id    := RENT_VAL.ROOM_ID;
      INSERT INTO user__status
        VALUES (sp_first_name,sp_last_name,SP_age,SP_rent,SP_room_id);
    END LOOP;    
END SP_USERS_STATUS;
END PACK_USER_STATUS;

EXECUTE PACK_USER_STATUS.SP_USERS_STATUS(100);

select distinct * from user__status

------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------

7. 
--object
--create a object type inside another object type to show the information of a guest. 


insert into payment values(1,2,202,675,'08-DEC-12','01/DEC/12 - 31/DEC/12')
insert into payment values(2,4,406,1500,'15-OCT-12','01/OCT/12 - 31/OCT/12')
insert into payment values(3,4,406,570,'20-MAR-13','15/MAR/13 - 15/APR/13')
drop table Guest_info;
drop type GUEST_TY;
drop type GUEST_INFO_TY;
create type GUEST_INFO_TY as object
(
GENDER  VARCHAR2(10),
DATE_OF_BIRTH DATE,
ADDRESS_1 VARCHAR2(80),
ADDRESS_2 VARCHAR2(25),
CELLPHONE VARCHAR2(25),
E_MAIL VARCHAR2(25));

drop type GUEST_TY;
create type GUEST_TY as object(
FIRST_NAME VARCHAR2(25),
LAST_NAME VARCHAR2(25),
INFORMATION GUEST_INFO_TY);

drop table Guest_info;
create table Guest_info(
guest_id number,
GUEST GUEST_TY);


insert into Guest_info values
(1, GUEST_TY('JAMIE', 'Morrone', GUEST_INFO_TY('Male', '05-DEC-78', '1951 Broad Wagon Pines, Waldheim, District of Columbia', '432', '317-432-5322', 'jamie@gmail.com')));

select * from Guest_info;

---------------------------------------------------------------------------------------------------------------------------------------------------------------
--Varying arrays
--It put all the guests who were checked by the same employee into one row
select EMPLOYEE_ID, GUEST_ID from payment

drop table employee_guest;
drop type guest_VA;
create type guest_VA as varray(10) of VARCHAR2(200);

drop table employee_guest;
create table employee_guest
(EMPLOYEE_ID VARCHAR2(25),
GUEST_ID guest_VA,
constraint employee_guest_PK primary key (EMPLOYEE_ID));

insert into employee_guest values
('NEIL ROCCHIO', guest_VA('JAMIE MORRONE', 'ERICKA HILDERBRAND'));
insert into employee_guest values
('KURT DEWALT', guest_VA('MATHEW INCE', 'CLAYTON REINDL', 'CARMELLA BOEDING'));

SELECT * FROM employee_guest
-----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------