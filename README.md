**Kritiikki-kirjatietokanta**

[Kritiikki-sovellus](http://t-tiikkala.users.cs.helsinki.fi/Kritiikki) on esimerkiksi kirjallisuudesta kiinnostuneen kaveriporukan käyttöön suunniteltu sovellus, jonka avulla on helppo jakaa tietoa siitä, mitä kukin on lukenut ja pitänyt lukemastaan. 

Sovellus on toteutett web-sovelluksena Helsingin yliopiston tietojenkäsittelytieteen laitoksen users-palvelimella Apache Tomcatin avulla. Sovellus toteutetaan Javalla ja tietokantakielenä käytetään PostgreSQL:ää.

- [Dokumentaatio](doc/dokumentaatio.pdf)

- [Sovelluksen kirjautumissivu](http://t-tiikkala.users.cs.helsinki.fi/Kritiikki)

- Testitunnus: sepisepi
- Salasana: seppo1234

- [ConnectionTest-testisovellus](http://t-tiikkala.users.cs.helsinki.fi/ConnectionTest/)

**Bugit ja kehityskohteet**

- Käyttäjien poistaminen käyttäjälistasta ei onnistu.
- Kirjan lisäämisen yhteydessä ei ole vielä mahdollista lisätä kritiikkiä, vaan tämä täytyy käydä lisäämärrä erikseen kirjan tietosivulta.
- Uutissivu on vielä toteuttamatta. Sivulla on tarkoitus näyttää, mitä kirjoja tietokantaan on hiljattain lisätty ja mihin kirjoihin kirjoitettu kritiikkejä.
