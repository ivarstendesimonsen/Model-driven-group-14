# Kortspillet President og Boms. OOP prosjekt Vår 2021.
##Info
##Regler
Ganske standard president og boms regler:
3 eller flere spillere.
Alle kort deles ut. Går det ikke opp i forhold til antall spillere i runden, deles alle kortene ut slik at noen får ett kort mer enn de andre spillerne. Eventuelt byttes treerne (med unntak for kløver 3) ut med de kortene som er til overs.  
Etter kortutdeling, men før man begynner å legge ut kort, skjer en bytting av kort etter følgende regler:  
Presidenten gir to valgfrie kort til boms.  
Visepresidenten gir ett valgfritt kort til viseboms.  
Nøytral beholder de kortene vedkommende er utdelt.  
Viseboms gir sitt aller beste kort til visepresidenten.  
Boms gir sine to beste kort til presidenten.  
Kløver 3 er det beste kortet, deretter følger 2, ess, konge, dame, knekt, 10, 9, 8, 7, 6, 5, 4, 3.   
Kløver 3 er som nevnt ovenfor det beste kortet, og gir muligheten til å slå ut hele bunken, uavhengig om det spilles enkelt, dobbelt, trippelt eller fire like.  
Kløver 3 kan ikke spilles på åpent bord, eller som siste kort.   
bomsen starter hver runde.  
Første spiller velger å legge ut enkeltkort, dobbelt (par), trippel, eller 4 like. Alle andre må legge ut like mange kort, men i høyere eller samme verdi.  
Bunken med kort som er lagt ut går ut av spillet når alle andre enn den som sist la ut ikke ønsker å legge ut. Den som sist la ut kort starter da å legge ut på ny etter regelen i forrige punkt.  
Dersom den som sist la ut kort er ferdig med spillet, går kortet ut og så går turen til spilleren til venstre for spilleren som sist ble ferdig.  
Den som først legger ut alle sine kort har vunnet.  
Bortsett fra for kløver 3 har ikke farge/sort noen betydning. Kun tallverdien er vesentlig.  
Om man ikke kan legge ut et kort, må man melde pass. Da kan man ikke lenger legge ut et kort før bunken har gått ut.  
Om alle spillerne passer i en runde, går bunken ut og den neste personen starter den nye runden.    
Når det har blitt lagt ut fire antall av en verdi (for eksempel fire ess) går bunken ut   
###tilleggsregel.  
6ere kan brukes sammen med andre kort som alternativ til å spille flere like kort. 6ere alene vil fungere som en vanlig 6'er.
##Begrensninger
Appen tillater et valg av mellom 3 og 7 spillere.
Den tillater å lagre tilstanden midt i et spill, og tar vare på aktuell informasjon om spillerne, og spilltilstanden.
##Tilleggsinfo.
###filbehandling
Så litt seint at det skulle brukes eget grensesnitt og egen klasse til filbehandlingen.  
(Sånn type 1time før fristen, og jeg hadde annet å tenke på, derfor er filbehandlingsmetodene i gameklassen.)  
Ville ikke ha vært så mye ekstra arbeid, måtte bare lage interface, og flytte mye av samme filbehandlinglogikken fra Game til en dedikert filbehandlingsklasse som lytter til Games' tilstander.
Har allerede grensesnittet som listener, så da kunne det kanskje vært naturlig å laget et listener interface de kunne ha delt.
###Junit
Starta litt seint med å enhetsteste også, rakk derfor langt ifrå å teste alle scenarioer i programmet.  
 Junit-delen tester enkelte funksjoner, men den er ganske ufullstendig, og tester langt ifra alt.  
  Har tatt med meg den lærdommen at det kan være lurt å notere ned alle edgecases man kommer på mens man koder  
  så slipper man alt hodebryet på slutten.
###gui
 Gui-en begrenser sammen med spilllogikken i stor grad hva som kan gå galt. Knappen for å spille kort er disablet fram til spillogikken har validert de forberedte kortene.
 Kunne ha gjort mer på gui-en med tanke på å se hvem som ble president hver runde etc. Men tiden strakk ikke til.
###Bugs
 Rakk ikke å få sett på alle bugsene som oppstod. Det meste er relatert til samspillet mellom endTurn, endRally, Passfunksjonene, og funksjonen for å sette min tur.  
 Jeg rota det litt til ved å legge spillLogikk i PlayerKlassen der det ikke var helt nødvendig. Det gjorde det til en tidkrevende prosess å spinne opp igjen. (Og jeg hadde ikke så mye tid til overs.)
 Ene bugen jeg ikke fikk fiksa er ganske fatal, og kan føre til at det er ingen spillere som har turen sin. Da kommer ikke spillet seg videre, og man må starte på nytt.
 Hadde nok vært lettere å søke etter bugs, og oppdaga de underveis, om jeg hadde investert mer tid i å lage skikkelige testklasser fra starten av.