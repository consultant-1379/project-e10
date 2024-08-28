package com.backend.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScriptPython {
    private Process process;
    private String scriptName;
    private String results;
    private String requestId;

    Logger logger = LoggerFactory.getLogger(ScriptPython.class);

    public ScriptPython(){
        this.scriptName = "";
        this.results = "";
        this.requestId = "";
    }

    public ScriptPython(String scriptName, String results, String requestId) {
        this.scriptName = scriptName;
        this.results = results;
        this.requestId = requestId;
    }

    public JSONObject runScript() throws IOException, JSONException {
        if (!this.getResults().equals("") && this.getResults().split(",").length >= 9
                && !this.getRequestId().equals("")) {
            String graphJSON = "";
            ProcessBuilder processBuilder = new ProcessBuilder("python",
                    "src/main/resources/pyscripts/" + this.getScriptName(), this.getResults(),
                    this.getRequestId());

            this.process = processBuilder.start();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(this.process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(this.process.getErrorStream()));

            String s = null;
            // Read the output from the command and return it as a JSON object
            logger.info("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                logger.info("PYTHON GENERATED KEY:\n" + s);
                graphJSON = s;
            }

            // Read any errors from the attempted command
            logger.info("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                logger.warn(s);
            }

            return new JSONObject(graphJSON);
        } else {
            return null;
        }
    }

    public String getScriptName() {
        return scriptName;
    }

    public String getResults() {
        return results;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
