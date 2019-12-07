create table deal(
	id           serial not null,
	title        varchar(100) not null,
	text         text not null,
	create_date  date not null,
	publish_date date not null,
	end_date     date not null,
	total_sold   integer not null,
	type         integer not null,
	url          varchar(100),
	primary key (id)
);
