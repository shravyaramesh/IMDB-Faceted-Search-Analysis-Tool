create table movie_countries(
    movie_id integer,
    country varchar(100)
);

create table movie_genres(
    movie_id integer,
    genre varchar(100)
);

create table movie_locations(
    movie_id integer,
    location1 varchar(100)
);

create table movie_locations_temp(
    movie_id integer,
    location1 varchar(100)
);

create table movie_tags(
    movie_id integer,
    tag_id integer,
    tag_weight integer
);

create table movies(
    movie_id integer,
    title varchar(200),
    movie_year integer,
    rt_all_critics_rating float,
    rt_all_critics_num_reviews integer
);

create table tags(
    tag_id integer,
    tag_value varchar(100)
);

create table user_tagged_movies(
    user_id integer,
    movie_id integer,
    tag_id integer,
    date_day integer,
    date_month integer,
    date_year integer,
    date_hour integer,
    date_minute integer,
    date_second integer
);

create index index_country on movie_countries(country);
create index index_genre on movie_genres(genre);
create index index_location1 on movie_locations(location1);
create index index_movie_id on movie_tags(movie_id);
create index user_tagged_movies on user_tagged_movies(movie_id);