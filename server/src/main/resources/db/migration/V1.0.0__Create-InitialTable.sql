create table todo(
    id serial primary key ,
    task varchar not null,
    is_done boolean not null
)