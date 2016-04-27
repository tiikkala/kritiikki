CREATE TABLE kirjat
(
id SERIAL PRIMARY KEY,
nimi VARCHAR(300) NOT NULL,
kirjailija VARCHAR(300),
julkaisuvuosi SMALLINT,
julkaisukieli VARCHAR(200),
suomentaja VARCHAR(300)
);

CREATE TABLE kayttajat
(
id varchar(100) PRIMARY KEY,
salasana varchar(20) NOT NULL,
sposti varchar(300) NOT NULL
);

CREATE TABLE kritiikit
(
id SERIAL PRIMARY KEY,
kirjaId INTEGER REFERENCES kirjat ON DELETE cascade
				  ON UPDATE cascade,
kirjoittaja VARCHAR(100) REFERENCES kayttajat ON DELETE cascade
					      ON UPDATE cascade,
teksti VARCHAR(100000) NOT NULL,
otsikko VARCHAR(300) NOT NULL,
paivays DATE
);

CREATE TABLE kommentit
(
id SERIAL PRIMARY KEY,
kritiikkiId INTEGER REFERENCES kritiikit ON DELETE cascade
			  	         ON UPDATE cascade,
kirjoittaja VARCHAR(100) REFERENCES kayttajat ON DELETE cascade
					     ON UPDATE cascade,
teksti VARCHAR(10000) NOT NULL,
paivays TIMESTAMP(0)
);

CREATE TABLE pisteet
(
id SERIAL PRIMARY KEY,
pisteet INTEGER,
kirjaId INTEGER REFERENCES kirjat ON DELETE cascade
				  ON UPDATE cascade,
kayttaja VARCHAR(100) REFERENCES kayttajat ON DELETE cascade
					     ON UPDATE cascade
);
