## AIM Assessment - 07/12/2023

See the problem statement, relevant discussions, FAQ, and next steps inside the 'docs' folder. <p>
This file describes how to run the code / endpoints

### Running the code
Since this problem was a web server and my mac laptop was acting up, I tried getting it working using java on a new windows machine as practice.  Unfortunately, while I got the web server stood up, it isn't resolving my REST endpoints yet so the best means of demonstrating the code is with unit tests until that is working.  <p>
This file also contains information on how to run the web server.

### Running the unit tests
While there may be a way to run JUnit tests from the command line, they were tested using intelliJ by clicking the arrow next to the test class name and saying "run all tests".

### Running the web server


#### Install Dependencies  

1. Install maven (https://maven.apache.org/install.html)
2. Install the java 20 JDK (https://www.oracle.com/java/technologies/downloads/)
3. Install apache tomcat 10 (https://tomcat.apache.org/download-10.cgi)

#### Configure the environment
1.  ```SET JAVA_HOME={JDK_INSTALLATION_PATH}```
2.  ```SET CATALINA_HOME={TOMCAT_INSTALLATION_PATH```

#### Start the server with:
```{tomcat installation folder}\bin\catalina.bat run```
Ensure it's running by navigating a browser to 
```http://localhost:8080```
A generic tomcat page should be visible.

#### Build the Package

```{MAVEN_INSTALLATION_PATH}\bin\mvn clean install``` 
should create a WAR file that can run at tomcat.  Since I was working with a new install, see the common errors section if it doesn't build properly.

#### Deploy the Package

1.  Copy elevator.war from the target directory to {tomcat installation folder}/bin/webapps
2.  Restart the server (shut it down with ```{tomcat installation folder}\bin\catalina.bat run``` or by hitting ctrl+c on the command window you previously started it with and then rerun the catalina run command.
3.  Test the deployment by navigating to 
```http://localhost:8080/elevator```
A Hello World page should be visible.  
4.  If the web server was working properly the APIs should work such as 
```http://localhost:8080/elevator/getNextDestination/0```
Postman could also be used which provides a convenient UI for the POST calls.

#### Common errors:
1.  "Cannot access defaults field of Properties".  The default maven web application seems to rely on the WAR plugin.  There is some inconsistancy with the configuration in the pom.
2.  Annotations don't work properly, specifically @VisibleForTesting. This seemed to be related to the version of the compiler in the pom.

