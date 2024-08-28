package com.backend.util;

import com.backend.entities.DataNode;

import java.util.List;
import java.util.Map;

public class ResultConversion {

    private List<DataNode> nodes;
    private String result;

    public ResultConversion(List<DataNode> nodes) {
        this.nodes = nodes;
        this.result = "";
    }

    public String calculateResults(){
        if (this.getNodes().isEmpty()){
            return "";
        }
        else if (this.getNodes().size() < 9){
            return "";
        }
        else {
            for (DataNode n : this.nodes) {
                Map<String, String> r = n.getResponses();
                if (r.containsKey("0")) {
                    this.setResult(this.getResult() + "4,");
                } else if (((r.containsKey("1") || r.containsKey("3")) &&
                        (r.containsKey("2") || r.containsKey("4"))) || r.isEmpty()) {
                    this.setResult(this.getResult() + "2.5,");
                } else if (r.containsKey("1") || r.containsKey("3")) {
                    this.setResult(this.getResult() + "2,");
                } else if (r.containsKey("2") || r.containsKey("4")) {
                    this.setResult(this.getResult() + "3,");
                }
            }
            this.setResult(this.getResult().substring(0, this.getResult().length() - 1));
            return this.getResult();
        }
    }

    public List<DataNode> getNodes() {
        return nodes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
