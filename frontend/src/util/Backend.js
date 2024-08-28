export default class Backend{

    static baseUrl =  "http://localhost:8080/form_results/"

    static async storeResults(json, userNode){
        console.log(userNode)
        json.forEach(element => {
            element.userId = userNode.userId
            element.sessionId = userNode.sessionId
            element.sessionPin = userNode.sessionPin
        });
        const response = await fetch(Backend.baseUrl + "list", {
            method:"POST",
            headers: {
                "Content-Type":"application/json",
            },
            body: JSON.stringify(json)
        })
    }

    static async retrieveRecord(sess, pin){
        const response = await fetch(Backend.baseUrl + `by-session?sessionId=${sess}&sessionPin=${pin}`, {
            method: "GET",
        })
        if(response.status != 200){
            return null;
        }
        else{
            return response.json();
        }
    }

    static async getUser(){
        const response = await fetch(Backend.baseUrl + "GenerateToken",{
            method: "GET"
        })
        return response.json();
        
    }

    static async getGraph(sess){
        const response = await fetch(Backend.baseUrl + `graph?sessionId=${sess}`, {
            method: "GET",
        })
        if(response.status != 200){
            return null;
        }
        else{
            console.log(response);
            return response.text();
            
        }
    }
}
