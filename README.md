# Prova Finale Ingegneria del Software 2021

## Group GC44

- ### **Alberto Pirillo** ([@AlbertoPirillo](https://github.com/AlbertoPirillo))
- ### **Riccardo Marino Rocco** ([@FynePool](https://github.com/FynePool))
- ### **Roger Aldair Veliz Sedano** ([@aldairveliz](https://github.com/aldairveliz))

## Master of Renaissance

## Description
The project's goal is to implement Cranio Creations' Master of Renaissance board game using Object-Oriented programming and the MVC architectural pattern.

The network is managed via TCP Sockets, created between clients and server to send and receive specific type of messages.

The game can be played using either a CLI or a GUI.


## Implemented requirements
### Basic functionalities
- Basic rules
- Complete rules
- Socket
- CLI
- GUI

### Advanced functionalities
- Multiple Games
- Local Game

<p>&nbsp;</p>

# Execution instructions
All the required files are available [here](deliverables/jars)

- It is possible to pass program arguments to every JAR (see below)

- If no program arguments were given, the JAR will automatically ask all the needed parameters


## Description of the JARs

To run the JARs, Java RE 15.0.2 is required

### **server.jar**
- Runs the server of the game, waiting for clients to connect
- The port of the server can be specified with "--port"
- Example: `java -jar server.jar --8080`

### **client.jar**
- Runs a client of the game, checking if a server is reachable
- Server's IP and port can be specified with "--ip:port"
- The usage of the CLI or the GUI can be forced with "--cli" and "--gui"
- Example: `java -jar client.jar --gui --127.0.0.1:8080`

### **local_game.jar**
- Runs a client in Local Game mode, which doesn't need to connect to a server
- The usage of the CLI or the GUI can be forced with "--cli" and "--gui"
- Example: `java -jar local_game.jar --gui`

## Master_of_Renaissance.zip
- The ZIP contains the game with the required JRE, built with [Launch4j](http://launch4j.sourceforge.net/)
- Can be downloaded from the Release section
- Java is not required to run, double-clicking .exe files is enough
- The required parameters will be asked automatically
- Works only on Windows

<p>&nbsp;</p>

# Documentation
## JavaDoc
ZIP available [here](deliverables/javadoc.zip)


## UML
- [High level](deliverables/UML%20pdf/high%20level.pdf)
- [Model](deliverables/UML%20pdf/model.pdf)
- [Controller](deliverables/UML%20pdf/controller.pdf)
- [Network](deliverables/UML%20pdf/network.pdf)
- [Client and Server](deliverables/UML%20pdf/client%20and%20server.pdf)


## Test coverage

ZIP available [here](deliverables/coverage.zip)

![Coverage report](deliverables/coverage%20report.png)

<p>&nbsp;</p>

## Software
- [Java RE 15.0.2](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html)
- [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/)
- [StarUML](https://staruml.io/)
- [Apache Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [GSON](https://github.com/google/gson)
- [JavaFX 16](https://openjfx.io/)


## License & Copyright
Master of Renaissance board game is copyrighted by [Cranio Creations](https://craniointernational.com/products/masters-of-renaissance/)
Licensed under [MIT License](LICENSE) 