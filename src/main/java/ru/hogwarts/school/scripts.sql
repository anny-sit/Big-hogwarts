select * from "public".student where age between 0 and 12;

select name from "public".student;

select * from "public".student where student.name like '%o%';

select * from "public".student where student.age < student.id;

select * from "public".student order by age asc;