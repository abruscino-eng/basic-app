create schema basic_app;

create table basic_app.example(
    id serial PRIMARY KEY,
    name varchar(50) NOT NULL
);

insert into basic_app.example(name)
values
    ('EXAMPLE_1'),
    ('EXAMPLE_2'),
    ('EXAMPLE_3');