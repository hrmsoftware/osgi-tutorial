# "Getting Started" med OSGi, Vaadin, SQL och MyBatis

## Förberedelser
    
* Installera [Apache Maven](http://maven.apache.org). Detta borde de flesta redan hunnit göra.
* Se till att koden för Integrationsmotorn är utcheckad och att det går bygga den:

~~~~~~~
$> svn checkout http://172.20.10.66/svn/hrm2/trunk/integration-engine
$> cd [där koden blev utcheckad]
$> mvn clean install
~~~~~~~

## 1 : Bygg en väldigt enkel OSGi-modul

2. Templateprojektet (@ GitHub)
    * Visa hur Maven är uppsatt.
    * Visa hur bundlen ser ut.
3. Kör bundlen
    * Starta OSGi-containern.
    * Installera Bundlen (exploded path - mklink)
    * Installera plan-fil som enablar konsollen.

## 2 : Lägg till en Vaadin-applikation

4. Lägg till en vaadin-app till bundlen.
    * Lägg till dependency till bundlen.
    * Bygg en servlet (DS) - exponera den med whiteboard-patternet.
    * Installera vaadin-support i OSGI-containern + pax-web.
    * Bygg bundlen och verifiera att rätt gränssnitt visas i webbappen.
    * Rita en bild som beskriver hur bundlen interagerar med pax-web, pax-whiteboard och vaadin.

## 3 : Databasaccess

* Introduktion, DataSource. Introducera lösningen i integrationsmotorn (peka på Jira-issuen).
* Visa hur vi kan installera en H2-datakälla
* Visa hur vi accessar HRMs databas (JTDS är tillgänglig som default)
* Lägg till "sida" i vaadin-applikationen som listar innehållet i en tabell. Använd supporten som finns för att snabbt och enkelt skapa en "Table".

## 4 : MyBatis

* Introduktion.

## 5 : Liquibase

## x : Visa hur man kan lägga till fler bundlar till maven-projektet.
