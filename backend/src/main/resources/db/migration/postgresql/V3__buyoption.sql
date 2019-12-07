create table buy_option(
	id                  serial,
	deal                integer,
	title               varchar(100),
	normal_price        numeric(18,2),
	percentage_discount numeric(18,2),
	sale_price          numeric(18,2),
	quantity_cupom      integer,
	quantity_sold       integer,
	start_date          date,
	end_date            date,
	primary key(id),
	foreign key(deal) references deal(id)
);
