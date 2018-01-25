# pp_ta1718
Praxisprojekt Sprachanalysetools fürs Wintersemester 2017/18 Uni DuE

Das Programm lässt sich mit diesen Schritten ausführen:
1. Im Ordner /target/ müssen die Dateien [...standalone.jar], webpage.html, styles.css und NRC_SentimentLexicon.txt vorhanden sein.
2. Die Webseite webpage.html sollte mit Internet Explorer ausgeführt werden, da andere Browser nicht die Skriptfunktionen unterstützen
3. Alternativ lässt sich das Programm über die Konsole ausführen: "java -jar [...standalone.jar] [file,JSON]|amazonURL "UrlToFileOrAmazonProduct" maxAmountOfData [emotion|quantity]"

...standalone.jar = the name of the .jar file (probably de.unidue.langtech.teaching.pp.meise-0.0.1-SNAPSHOT-standalone.jar)
[file,json]|amazonURL = entweder file,JSON angeben (z.b. file,B0002E1G5C) oder nur amazonURL
"UrlToFileOrAmazonProduct" = gibt den Link für entweder .json Datei an (wenn file,JSON gewählt wurde), oder die URL zu dem Amazon-Product (falls amazonURL gewählt wurde)
maxAmountOfData = maximale Cutoff-Data, es werden nicht mehr als diese Anzahl Werte eingelesen (empfohlen: ~200)
[emotion|quantity] = gibt Sortierpräferenz an.

Beispielauf: "java -jar de.unidue.langtech.teaching.pp.meise-0.0.1-SNAPSHOT-standalone.jar amazonURL "https://www.amazon.com/Reebok-Classic-Leather-Fashion-Sneaker/dp/B074D8ZL85?pd_rd_wg=sKJXT&pd_rd_r=f5a2b892-c7a4-4f8d-b84d-8f00b7e20581&pd_rd_w=FVpre&ref_=pd_gw_simh&pf_rd_r=QSGVAY4KAMR0F7RV77NK&pf_rd_p=4c5acc25-f4b0-5ad7-9004-0f2549f94c2f" 200 emotion
