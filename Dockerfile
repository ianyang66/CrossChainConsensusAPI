
FROM tomcat:8.0.53-jre8

COPY consensus.war /usr/local/tomcat/webapps/
COPY a.properties /usr/local/tomcat/webapps/
COPY contractaddr.txt /usr/local/tomcat/webapps/
COPY UTC--2022-06-08T07-09-14.151095565Z--5de4abbe713178e6c02ce484d4cff8d14549b7ca /usr/local/tomcat/webapps/