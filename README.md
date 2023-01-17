# CDIO-4
Gruppe 10
GitHub
Da gruppen har valgt at udarbejde vores projekt i GitHub, er der en hel række fordele, der følger med som konsekvens. F.eks. er vi sikret, at alle filerne er allokeret det samme sted. Når der bliver arbejdet på projektet af forskellige gruppemedlemmer, vil man altid være i stand til at finde de forskellige versioner der er blevet arbejdet på, når de er blevet committet. Dette er tilfældet, da alle skubber deres arbejde op i skyen, til det GitHub repository, som projektet er blevet lavet i. På den måde kan man også altid gå tilbage i historikken for at se hvad der er blevet ændret på, samt finde alle filerne i projektets GitHub repository.
I GitHub er det også muligt at se, om man er bagud ift. commits. GitHub sammenligner den version man besidder med den version nyeste version der er uploadet til projekts GitHub repository. På den måde kan man altid sammenligne sin egen version og den nyeste version, der er blevet publiceret.
Oprettelse af Maven projekt i IntelliJ
For at oprette et nyt Maven projekt i IntelliJ skal man første lave et nyt projekt. Dette kan gøres fra topmenuen, og File -> New -> Project. Herefter kan man vælge Maven Archetype under “Generators”, og give sit projekt et navn.


JUnit er blevet brugt til at teste vores applikation.
Måden gruppen har hentet JUnit har blot været ved at tilføje JUnit i IntelliJ’s bibliotek:
Inde i IntelliJ trykkes der blot på File -> Project Structure-> Libraries.

Afhentning af program i IntelliJ fra Git:
Først lokaliserer man hvor projektet ligger henne. Dette kan gøres ved at finde projektet på GitHub, og kopierer projektets url, ved at klikke på den grønne knap “Code”, og derefter kopiere url-adressen.
Herefter åbnes IntelliJ. Når man er i hovedmenuen, trykker man på knappen “Get from VCS”. Befinder man sig allerede i et åbent projekt, kan man enten lukke projektet ned ved at trykke på “File”, og herefter klikke “Close Project”, eller klikke på “VCS”, og derefter trykke på “Get from Version Control”.
I det felt hvor der står “URL:” ud for, kopierer man den url-adressen, fra tidligere, ind i felten. I feltet “Directory:” skal man så bestemme hvor henne på sin computer at projektet skal oprettes. Her er det vigtigt at der vælges en tom mappe, som projektet kan oprettes i.
Når ovenstående er gjort trykker man clone og henter derved projektet ned på sin computer.

Kørsel af program:
Vores program bliver kørt i java-klassen App. Har man projektet åbnet kan klassen App findes i “src/main/java/App.java”
Dvs. at man først skal klikke på mappen src, herefter mappen main, herefter mappen java, hvorefter man så direkte kan trykke på klassen App for at åbne den.

Når App er blevet åbnet, og man har vinduet åbnet i IntelliJ, går man op og trykker på Current File, hvorefter man så kører programmet.
Dette resulterer i, at den GUI vi har implementeret, bliver åbnet, hvor man så vil være i stand til at spille spillet.

