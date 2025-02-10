FROM tomee:latest
RUN rm -rf /usr/local/tomee/webapps/ROOT
COPY target/EEstack-1.0-SNAPSHOT.war /usr/local/tomee/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]