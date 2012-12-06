# VS-2012-WS-A3-PDTTDLFH
Verteilte Systeme - Wintersemester 1012 - Aufgabe 3
# Kompilieren
`javac ./**/*.java`
# Ausführen
## Namensdienst
`java nameservice.Starter <port>`

Beispiel: `java nameservice.Starter 1337`

## Bank
`java -cp <dir_path_to_jar>:<jar_name> bank.Bank <ns-host> <ns-port> <bank-name>`

Beispiel: `java -cp .:bank.jar bank/Bank localhost 1337 diebank`

## Filiale
`java -cp <dir_path_to_jar>:<jar_name> filiale.Filiale <ns-host> <ns-port> [<bank-name>]`

Beispiel: `java -cp .:filiale.jar filiale.Filiale localhost 1337 diebank`

## Geldautomat
`java -cp <dir_path_to_jar>:<jar_name> geldautomat.Geldautomat <ns-host> <ns-port>`

Beispiel: `java -cp .:geldautomat.jar geldautomat.Geldautomat localhost 1337`