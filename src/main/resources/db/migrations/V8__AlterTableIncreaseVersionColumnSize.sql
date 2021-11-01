ALTER TABLE answers
ALTER COLUMN version varchar(16) NOT NULL;

ALTER TABLE migrations
ALTER COLUMN version varchar(16) NOT NULL;

ALTER TABLE questions
ALTER COLUMN version varchar(16) NOT NULL;

ALTER TABLE recommendations
ALTER COLUMN version varchar(16) NOT NULL;

ALTER TABLE screenings
ALTER COLUMN version varchar(16) NOT NULL;


