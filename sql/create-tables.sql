CREATE TABLE kirjat
(
id SERIAL PRIMARY KEY,
nimi VARCHAR(300) NOT NULL,
kirjailija VARCHAR(300),
julkaisuvuosi INTEGER,
julkaisukieli VARCHAR(200),
suomentaja VARCHAR(300),
);

CREATE TABLE kayttajat
(
id varchar(100) PRIMARY KEY,
sposti varchar(300) NOT NULL
);

CREATE TABLE kritiikit
(
id SERIAL PRIMARY KEY,
kirjaId REFERENCES kirjat ON DELETE cascade
				  ON UPDATE cascade,
kirjoittaja REFERENCES kayttajat ON DELETE cascade
					 ON UPDATE cascade,
teksti VARCHAR(100000) NOT NULL,
otsikko VARCHAR(300) NOT NULL,
);

CREATE TABLE kommentit
(
id SERIAL PRIMARY KEY,
kritiikkiId REFERENCES kritiikki ON DELETE cascade
			  	         ON UPDATE cascade,
kayttajaId REFERENCES kayttajat ON DELETE cascade
					ON UPDATE cascade,
teksti varchar(10000) NOT NULL
);

CREATE TABLE pisteet
(
id SERIAL PRIMARY KEY,
pisteet INTEGER,
kirjaId REFERENCES kirjat ON DELETE cascade
				  ON UPDATE cascade,
kayttajaId REFERENCES kayttajat ON DELETE cascade
					ON UPDATE cascade
);
