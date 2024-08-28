# Run Sonarqube for Cloud Native Maturity Matrix
Also Refer to https://docs.sonarsource.com/sonarqube/8.9/analyzing-source-code/scanners/sonarscanner-for-maven/?gads_campaign=SQ-Hroi-PMax&gads_ad_group=Global&gads_keyword=&gclid=Cj0KCQjwxuCnBhDLARIsAB-cq1qr91tvq4HUou3KXvF2dOTZ7q4V4y2P1euHyBvZUvj9DtW-QU8c12caAspbEALw_wcB

## Running Sonarqube for Spring Boot Maven Backend

- Start Sonarqube container: `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube`
- Visit `https://localhost:9000` and login as `admin:admin`, then update the password.
- In your `${HOME}/.m2` `settings.xml` file(example: `wrapper/dists/apache-maven-3.9.4-bin/32a55694/apache-maven-3.9.4/conf/settings.xml`), update it to include a sonar plugin and profile:

```xml
<settings>
    <pluginGroups>
        <pluginGroup>org.sonarsource.scanner.maven</pluginGroup>
    </pluginGroups>
    <profiles>
        <profile>
            <id>sonar</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <!-- Optional URL to server. Default value is http://localhost:9000 -->
                <sonar.host.url>
                  http://myserver:9000
                </sonar.host.url>
            </properties>
        </profile>
     </profiles>
</settings>
```

- Generate an [authentication token](https://docs.sonarsource.com/sonarqube/8.9/user-guide/user-account/generating-and-using-tokens/?_gl=1*15ujo9d*_up*MQ..&gclid=Cj0KCQjwxuCnBhDLARIsAB-cq1qr91tvq4HUou3KXvF2dOTZ7q4V4y2P1euHyBvZUvj9DtW-QU8c12caAspbEALw_wcB) for your local sonarqube
- Run: `cd backend && mvn clean verify sonar:sonar -Dsonar.login=${SONAR_TOKEN}` to verify the connection to sonarqube is working from the spring boot app
- Run: `mvn sonar:sonar -Dsonar.login=squ_d70f57fafb10b118ac1af9dfd4f291a255628b74` to run a sonar scan against the backend. 
- Visit `localhost:9000` after its complete the results should be there indicating any bugs, vulnerabilities, code smells and test coverage percentage.

## Running Sonarqube for React Frontend

- Start Sonarqube container: `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube`
- Visit `https://localhost:9000` and login as `admin:admin`, then update the password.
- Generate an [authentication token](https://docs.sonarsource.com/sonarqube/8.9/user-guide/user-account/generating-and-using-tokens/?_gl=1*15ujo9d*_up*MQ..&gclid=Cj0KCQjwxuCnBhDLARIsAB-cq1qr91tvq4HUou3KXvF2dOTZ7q4V4y2P1euHyBvZUvj9DtW-QU8c12caAspbEALw_wcB) for your local sonarqube
- Install sonarqube-scanner CLI tool globally(may need `sudo`): `npm install sonarqube-scanner -g`
- Run: `cd frontend && sonar-scanner -Dsonar.login=${SONAR_TOKEN}`
- If you receive an error related to not being able to find a SonarScanner binary, then install it manually instead of using the `npm` one: https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/. This can be a bit convoluted, ensure you use the PATH method to use the binary in the unziped directory or manually call it like: `cd frontend &&  ~/applications/sonar-scanner-5.0.1.3006-linux/bin/sonar-scanner -Dsonar.login=${SONAR_TOKEN}`
- Visit `localhost:9000` after its complete the results should be there indicating any bugs, vulnerabilities, code smells and test coverage percentage.
