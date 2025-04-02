select s.name, s.age, f.name
from student s left join faculty f on s.faculty_id = f.faculty_id
where s.faculty_id is not null;

select a.student_id, s.* from avatar a left join student s on student_id = s.id;