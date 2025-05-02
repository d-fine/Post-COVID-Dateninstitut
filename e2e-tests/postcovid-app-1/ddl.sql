create table input.data
(
    id int not null,
    research_data text null
);

create table output.data
(
    id int not null primary key,
    research_data text null,
    security_column text not null
);

ALTER TABLE output.data ENABLE ROW LEVEL SECURITY;