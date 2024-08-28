const scanner = require("sonarqube-scanner");
scanner(
  {
    serverUrl: "https://localhost:9000",
    options: {
      "sonar.projectName": "frontend",
    },
  }
)
