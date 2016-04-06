INSERT INTO kirjat VALUES (DEFAULT, 'Rikos ja rangaistus', 'Dostojevski, Fjodor', 1866,
'Venäjä', 'Kuukasjärvi, Olli'), (DEFAULT, 'Neurovelho', 'Gibson, William', 1984, 'Englanti', 'Häilä, Arto');

INSERT INTO kayttajat VALUES ('karikriitikko', 'kari1234', 'kari.hamalainen@hotmail.com'), ('sepisepi', 'seppo1234', 'seppo.junkkari@yahoo.com');

INSERT INTO kommentit VALUES (DEFAULT, (SELECT id FROM kritiikit WHERE otsikko = 'Lorem ipsum'), (SELECT id FROM kayttajat WHERE id = 'karikriitikko'), 'Äärimmäisen älykäs kommentti');

INSERT INTO pisteet VALUES (DEFAULT, 9, (SELECT id FROM kirjat WHERE nimi = 'Neurovelho'), 'sepisepi');

INSERT INTO kritiikit VALUES (DEFAULT, (SELECT id FROM kirjat WHERE nimi = 'Rikos ja rangaistus'), 'sepisepi', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed posuere interdum sem. Quisque ligula eros ullamcorper quis, lacinia quis facilisis sed sapien. Mauris varius diam vitae arcu. Sed arcu lectus auctor vitae, consectetuer et venenatis eget velit. Sed augue orci, lacinia eu tincidunt et eleifend nec lacus. Donec ultricies nisl ut felis, suspendisse potenti. Lorem ipsum ligula ut hendrerit mollis, ipsum erat vehicula risus, eu suscipit sem libero nec erat. Aliquam erat volutpat. Sed congue augue vitae neque. Nulla consectetuer porttitor pede. Fusce purus morbi tortor magna condimentum vel, placerat id blandit sit amet tortor.

Mauris sed libero. Suspendisse facilisis nulla in lacinia laoreet, lorem velit accumsan velit vel mattis libero nisl et sem. Proin interdum maecenas massa turpis sagittis in, interdum non lobortis vitae massa. Quisque purus lectus, posuere eget imperdiet nec sodales id arcu. Vestibulum elit pede dictum eu, viverra non tincidunt eu ligula.

Nam molestie nec tortor. Donec placerat leo sit amet velit. Vestibulum id justo ut vitae massa. Proin in dolor mauris consequat aliquam. Donec ipsum, vestibulum ullamcorper venenatis augue. Aliquam tempus nisi in auctor vulputate, erat felis pellentesque augue nec, pellentesque lectus justo nec erat. Aliquam et nisl. Quisque sit amet dolor in justo pretium condimentum.

Vivamus placerat lacus vel vehicula scelerisque, dui enim adipiscing lacus sit amet sagittis, libero enim vitae mi. In neque magna posuere, euismod ac tincidunt tempor est. Ut suscipit nisi eu purus. Proin ut pede mauris eget ipsum. Integer vel quam nunc commodo consequat. Integer ac eros eu tellus dignissim viverra. Maecenas erat aliquam erat volutpat. Ut venenatis ipsum quis turpis. Integer cursus scelerisque lorem. Sed nec mauris id quam blandit consequat. Cras nibh mi hendrerit vitae, dapibus et aliquam et magna. Nulla vitae elit. Mauris consectetuer odio vitae augue.', 'Lorem ipsum');
