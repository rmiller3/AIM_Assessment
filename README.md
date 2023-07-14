AIM Assessment - 07/12/2023

See the problem statement and relevent discussions with recruiter inside the 'docs' folder.

Since this problem was a web server and my mac laptop is acting up, I tried getting it working using java on windows as practice.

Install Dependencies  

Install maven (https://maven.apache.org/install.html)
Install the java 20 JDK (https://www.oracle.com/java/technologies/downloads/)
Install apache tomcat 10 (https://tomcat.apache.org/download-10.cgi)

Build the Package

'mvn clean install' 
should create a WAR file that can run at tomcat.  Since I was working with a new install, see the common errors section if it doesn't build properly.

Alternatively you can run the junit tests with your method of choice (I ran them in the IntelliJ IDE) independently of maven.  

Test that tomcat works by setting the JAVA_HOME environment variable to the path you installed the JDK to.  THen navigate your browser to localhost:8080 and ensure you see a tomcat homepage.

Deploy the Package

Copy elevator.war from the target directory to %TomcatInstallDir%/bin/webapps
SET JAVA_HOME=c:\Users\rmill\.jdks\openjdk-20.0.1
SET CATALINA_HOME=c:\apache-tomcat-10.1.11-windows-x64

Start the server with:
c:\apache-tomcat-10.1.11-windows-x64\bin\startup.bat

Test the deployment by navigating to 
http://localhost:8080/elevator

Shutdown the server:  c:\apache-tomcat-10.1.11-windows-x64\bin\shutdown.bat


Building from the command line:
SET JAVA_HOME=c:\Users\rmill\.jdks\openjdk-20.0.1
c:\apache-maven-3.9.3-bin\apache-maven-3.9.3\bin\mvn clean install

Common errors:
1.  "Cannot access defaults field of Properties".  The default maven web application seems to rely on the WAR plugin.  There is some inconsistancy with the configuration in the pom.

2.  Annotations don't work properly, specifically @VisibleForTesting. This seemed to be related to the version of the compiler in the pom.

