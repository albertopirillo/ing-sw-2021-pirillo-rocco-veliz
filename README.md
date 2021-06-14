# Prova Finale Ingegneria del Software 2021
## Master of Renaissance
## Group GC44


- ### **Alberto Pirillo** ([@AlbertoPirillo](https://github.com/AlbertoPirillo))
- ### **Riccardo Marino Rocco** ([@FynePool](https://github.com/FynePool))
- ### **Roger Aldair Veliz Sedano** ([@aldairveliz](https://github.com/aldairveliz))

[comment]: <> (## Description)
[comment]: <> (_TODO_)

## Documentation
### JavaDoc
ZIP available [here](deliverables/javadoc.zip)

## Execution instructions
All the required files are available [here](deliverables/jars)

- It is possible to pass program arguments to every JAR (see below)

- If no program arguments were given, the JAR will automatically ask all the needed parameters

## Description of the JARs:

### server.jar
- Runs the server of the game, waiting for clients to connect
- The port of the server can be specified with "-port"
- Example: `java -jar server.jar -8080`

### client.jar
- Runs a client of the game, checking if a server is reachable
- Server's IP and port can be specified with "-ip:port"
- The usage of the GUI or the CLI can be force with "-gui" and "-cli"
- Example: `java -jar client.jar -gui -127.0.0.1:8080`

### local_game.jar
- Runs a client in Local Game mode, which doesn't need to connect to a server
- The usage of the GUI or the CLI can be force with "-gui" and "-cli"
- Example: `java -jar local_game.jar -gui`



### UML
- [High level](deliverables/UML%20pdf/high_level.pdf)
- [Model](deliverables/UML%20pdf/model.pdf)
- [Controller](deliverables/UML%20pdf/controller.pdf)
- [Network](deliverables/UML%20pdf/network.pdf)
- [Client and Server](deliverables/UML%20pdf/client%20and%20server.pdf)


## Test coverage

[comment]: <> (ZIP available [here]&#40;deliverables/coverage.zip&#41;)

![Coverage report](deliverables/coverage%20report.png)


## Software
- [Java RE 15.0.2](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html)
- [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/)
- [StarUML](https://staruml.io/)
- [Apache Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [GSON](https://github.com/google/gson)
- [JavaFX 16](https://openjfx.io/)