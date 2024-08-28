import { useState, useEffect } from 'react'
import '../../App.css'
import Backend from '../../util/Backend'
import QuestionsFile from '../../assets/questions.json'
import { redirect, useLocation, useNavigate } from 'react-router';


function Result() {
  const [sess, setSess] = useState("");
  const [pin, setPin] = useState("");
  const [data, setData] = useState(null);
  const {state} = useLocation();
  const { userNode } = state || { userNode: {sessionId: "", sessionPin: ""}};
  const nav = useNavigate();
  const [image, setImage] = useState(null);


  useEffect(() => {
        //Get & set userNode
          Backend.retrieveRecord(userNode.sessionId, userNode.sessionPin).then((v) =>{
            setData(v)
          }).then((v) => {
            Backend.getGraph(userNode.sessionId).then((v) => {
              setImage(v);
            })
          });
},[]);

  return (
    <>
      <div>
          <h1>Results</h1>
      </div>
{
data != null && 
<div>
  <h3>Revisit it later</h3>
  <h4>Session Id : {userNode.sessionId}</h4>
  <h4>Session Pin : {userNode.sessionPin}</h4>
{Object.values(data).map((d, index) => {
  return(
    <div>
  <table style={{marginBottom: "1rem", margin: "auto", width: "100%", borderBlock: "2px solid white"}}>
    <tr>
      <th>{d.question}</th>
    </tr>
    {/* Print all the responses in pretty format */}
    <tr>
      <td>
        {Object.values(d.responses).map((res, index) => {
          return(
          <p>{res}</p>
          )
        })}
      </td>
    </tr>
  </table>
  <br></br>
  </div>
  )
})}
{image !=null &&
<div>
  <img style={{width: "80%"}} src={`data:image/png;base64, ${image}`}  alt="Red dot" />
    </div>
}
<button style={{margin:"1rem"}} onClick={() => {
    nav("/")
  }}>Done</button>
</div>
}

          {/* <a href='/'>New Here? Evaluate your company here</a> */}
    </>
  )
  }
  
  
  
  export default Result
