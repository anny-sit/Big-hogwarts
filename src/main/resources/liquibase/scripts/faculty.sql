-- liquibase formatted sql
-- changeset anny_sit:2
create index faculty_name_color_index on faculty (name, color)