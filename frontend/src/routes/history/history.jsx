import { useState } from 'react'
import '../../App.css'
import Backend from '../../util/Backend'
import QuestionsFile from '../../assets/questions.json'
import { redirect } from 'react-router';

var style = {
    input:{
        padding: "0.75rem"
    }
}

function History() {

    const [sess, setSess] = useState("");
    const [pin, setPin] = useState("");
    const [data, setData] = useState(null);
    const [wrong, setWrong] = useState(false);
    const [image, setImage] = useState(null);

    return (
      <>
        <div>
            <h1>History</h1>
        </div>
{data == null && typeof data != Promise &&
      <div>
        { wrong &&
          <p style={{color: "red"}}>Incorrect details</p>
        }
        <p><input type='text' style={style.input} onChange={(event) => {setSess(event.target.value)}} name='sessionId' placeholder='Session ID'></input></p>
        <p><input type='text' style={style.input} onChange={(event) =>{setPin(event.target.value)}} name="pin" placeholder='PIN'></input></p>
        <p><button onClick={() =>{
            Backend.retrieveRecord(sess, pin).then((v) => {
              if(v!=null || v!=undefined){
                setData(v);
                setWrong(false);
              }
              else
                setWrong(true);

                Backend.getGraph(sess).then((v) => {
                  if(v!=null)
                    setImage(v);
                  console.log(v);
                })
            });
        }}>Submit</button></p>
      </div>
}
{
  data != null && 
  <div>
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
      setData(null)
    }}>Done</button>
  </div>
}

            <a href='/'>New Here? Evaluate your company here</a>
      </>
    )
  }
  
  
  
  export default History
