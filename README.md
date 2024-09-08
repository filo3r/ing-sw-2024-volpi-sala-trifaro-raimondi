# Codex Naturalis
The project involves the development of a software version of the board game _Codex Naturalis_ as part of the Final Exam of the Software Engineering course for the academic year 2023-2024.  
The implementation consists in the creation of a distributed system based on client-server architecture, in which the server is able to manage one or more games simultaneously. Each game involves two or more clients, each representing a player, who can participate in only one game at a time.  
[Codex Naturalis](https://www.craniocreations.it/prodotto/codex-naturalis)  

## Implementation Details
| Functionality  | Status |  
|:---------------|:------:|  
| Complete rules | ✅     |  
| TUI            | ✅     |  
| GUI            | ✅     |  
| RMI            | ✅     |  
| Socket         | ✅     |  
| Multiple games | ✅     |  
| Disconnections | ✅     |  
| Chat           | ✅     |  
| Persistence    | ❌     |  

[Requirements](deliverables/requirements/requirements.pdf)  
[Rulebook](deliverables/requirements/codexRulebookEN.pdf)  

## Download and Play
### Prerequisites
To download and play the game, you need to have `Java 21` or later version installed.  
To check the version of Java installed on your system, you can use the following command in the terminal:  
```bash
java -version
```
You can download the latest version of Java from the official [Oracle website](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).  
### Installation
1. Download the appropriate `.jar` file for your device from the [relase page](https://github.com/matteosalaa/ing-sw-2024-volpi-sala-trifaro-raimondi/releases/tag/CodexNaturalis-v1.0.0).
2. Run the `.jar` file using the command:
   ```bash
   java -jar file_name.jar
   ```
3. Follow the on-screen instructions to start playing.

## Dependencies
- [JUnit](https://junit.org/)  
- [Mockito](https://site.mockito.org/)  
- [Gson](https://github.com/google/gson)  
- [JavaFX](https://openjfx.io/)  


