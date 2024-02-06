insert into reactive.products(id, name)
SELECT id, md5(random()::text)
FROM generate_series(1,20) id;

insert into reactive.products(id, name)
SELECT id, md5(random()::text)
FROM generate_series(105,110) id;