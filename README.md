# Fuzzy Logic System for video resolutions

## Como ejecutar

El proyecto usa maven para la compilación del proyecto de Java. Estuvimos usando el JRE versión 21. Para ejecutar el programa se puede usar el comando de maven correspondiente o desde el IDE.

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="fuzzy.app.Main"
```

O directamente desde el package con el Java run enviroment que tenga instalado, luego del build.

```bash
mvn clean install
mvn package
cd target
java -jar Fuzzy-1.0-SNAPSHOT.jar
```
