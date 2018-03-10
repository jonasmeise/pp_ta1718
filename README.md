# pp_ta1718
Praxisprojekt Sprachanalysetools fürs Wintersemester 2017/18 Uni DuE

##Übersicht: Was war die Idee?

Dies ist das Praxisprojekt Sprachanalysetools für das Wintersemester 2017/2018 an der Universität Duisburg-Essen. Idee des Projektes war es, eine Webseite/Applikation zu schreiben, die es erlaubt, eine große Menge Rezensionen von Amazon.com auszulesen und prägnante Beschreibungen auszulesen. Das Programm ist für die englische Sprache und somit auch die englische Version von Amazon.com ausgelegt.

##Kurzes Tutorial: Wie startet man das Programm?

Das Programm lässt sich mit diesen Schritten ausführen:
1. Innerhalb eines Ordners müssen die Dateien [...standalone.jar], webpage.html, styles.css und NRC_SentimentLexicon.txt vorhanden sein. Diese Dateien lassen sich aus dem /target/-Ordner des Gits-Repository herunterladen. Das Hauptprogramm ist webpage.html
2. Die Webseite webpage.html sollte mit Internet Explorer ausgeführt werden, da andere Browser nicht die Skriptfunktionen unterstützen (die Ausführung des Programmes wird über ein DirectX-Plugin gehandhabt, dies muss beim Start der Webseite in einem Popup-Fenster zugelassen werden).
3. Die Webseite enthält ein Eingabefeld, in welches ein Link zu einem Produkt von www.amazon.com eingefügt werden soll. Mit einem Schieberegler lässt sich festlegen, wie viele Werte eingelesen werden sollen (150 ist der Standardwert). Der Konsolenlog kann nach Bedarf ausgeschaltet werden.
4. Nach einem Klick auf "Search" wird das Programm gestartet. Nach ungefähr ~30sec - 120sec~ sollte dieses fertig durchgelaufen sein (falls der Konsolenlog aktiviert war, verschwindet nun die Konsole). Anschließend werden in zwei Tabellen die Ergebnisse der Suche angezeigt.
5. Die Tabelle enthält folgende Informationen: 
   - [#] - die Anzahl der Attribute

**Alternativ** lässt sich das Programm über die Konsole ausführen: ```java -jar [...standalone.jar] [file,JSON]|amazonURL "UrlToFileOrAmazonProduct" maxAmountOfData emotion```

*...standalone.jar* = der Name der .jar file (probably de.unidue.langtech.teaching.pp.meise-0.0.1-SNAPSHOT-standalone.jar)
*[file,json]|amazonURL* = entweder file,JSON angeben (z.b. file,B0002E1G5C) oder nur amazonURL
*"UrlToFileOrAmazonProduct"* = gibt den Link für entweder .json Datei an (wenn file,JSON gewählt wurde), oder die URL zu dem Amazon-Product (falls amazonURL gewählt wurde)
*maxAmountOfData* = maximale Menge an Daten, die eingelesen werden sollen; je größer der Wert, desto länger dauert der Analyseprozess (150 ist ein guter Mittelwert).
emotion = gibt den Wert an, nachdem sortiert wird; im Augenblick ist nur *emotion* implementiert.

*Beispielaufruf über die Konsole*: ```java -jar de.unidue.langtech.teaching.pp.meise-0.0.1-SNAPSHOT-standalone.jar amazonURL "https://www.amazon.com/Reebok-Classic-Leather-Fashion-Sneaker/dp/B074D8ZL85?pd_rd_wg=sKJXT&pd_rd_r=f5a2b892-c7a4-4f8d-b84d-8f00b7e20581&pd_rd_w=FVpre&ref_=pd_gw_simh&pf_rd_r=QSGVAY4KAMR0F7RV77NK&pf_rd_p=4c5acc25-f4b0-5ad7-9004-0f2549f94c2f" 200 emotion```


###Weitere Informationen
Das Programm schreibt einen Output in 3 verschiedene Dateien: *output.html*, *negative.html*, *positive.html*
*positive.html* umfasst alle positiv gelesen Attribute in einer HTML-Webpage. Diese wird automatisch innerhalb *webpage.html* integriert, kann aber eventuell auch selber in einem beliebigen Browser geöffnet werden.
*negative.html* umfasst alle negativ gelesen Attribute in einer HTML-Webpage. Diese wird automatisch innerhalb *webpage.html* integriert, kann aber eventuell auch selber in einem beliebigen Browser geöffnet werden.
*output.html* umfasst die gesamten gelesenen Daten in Rohform; dies ist legidlich eine Supportdatei fürs Programm



####Testing: Wie gut funktioniert das Programm?
Testweise wurde eine kleine .json Datenbank erstellt, die folgende Datensätze als Rezension enthält:
1. *I bought this product for a very cheap price on this website. The condition is acceptable, and although there are some minor mistakes my wife likes. I'd recommend that product to my friends if they are looking for some high quality product!*
2. *So, many people have complained over the overpriced costs - but I can tell you, it's worth it! This is a great christmas present for the entire family (that was my present for my aunt), the high quality outweighs the steep price. Dunno what you expected for this price/quality ratio, but I'm more than pleased.*
3. *What a trashy item, why is this even listed as a topselling item on Amazon? Noone would pay 100$ for such a low-quality product, and I highly regret that buy decision. There is no way that this article is overcosted as hell, and seriously, I was heavily pissed off since my expectations were so high - don't make the same mistake I did, forget about this trash!*
4. *Fast delivery, highly flexible and beautiful shape - great article, I'd recommend it to my friends.*
5. *Meh, there is better stuff out there. The quality isn't convincing and I can't imagine how people rate this product with 5 stars. It isn't actually bad, but maybe I should have read the description of that lousy article before buying it. After all, the extreme price was promising an equally high quality product, but in the end it was rather frustrating. I got mixed feelings about it, 3/5 stars.*
6. So, my friend recommended this "awesome article" to him, telling me that this was his favourite item. So, trusting his opinion, I ordered the same thing here on Amazon and got ... quite a big disappointment delivered to my front door. Why? Why does it need to be so overpriced? I expected some really good handcrafted article, but the look and feel doesn't match that high expectation. So I'm honestly angry at my friend for telling me about this product, time to end the friendship I guess lol. Wouldn't tell anyone to buy that thing yourself, save your precious money for something more worthy!

In Bezug auf diese kurzen Rezensionen sieht der Gold-Standard der wichtigen Attribute folgend aus:
```Syntax: [Bezugswort] -> [beschreibendes Adjektiv]
