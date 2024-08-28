import { useState, useEffect } from 'react'
import '../../App.css'
import Backend from '../../util/Backend'
import QuestionsFile from '../../assets/questions.json'
import { redirect, useNavigate } from 'react-router';

let userNode = null;

function Survey() {

    useEffect(() => {
        if (!userNode) {
            //Get & set userNode
            Backend.getUser().then((v) =>{
              userNode = v;
            });
            
        }
    },[]);

  //Nav
  let nav = useNavigate();
  //For counter of current question 
  let [question, setQuestion] = useState(1);
  //To choose whether to show Yes/No controls or just Next Button (For difference between standard and followup questions)
  const [showYesNo, setShowYesNo] = useState(true)
  //For optional followups
  const [checkedState, setCheckedState] = useState(
    new Array(4).fill(false)
  );
  //Object for the Answers that the user enters 
  const [ans, setAns] = useState([]);


  const style = {
    button: {
        margin: "1rem",
        padding: "1rem 2rem"
    },
    options: {
        padding: "0.5rem",
        textAlign: "left",
    },
    checkbox:{
        width:"1.25rem",
        height: "1.25rem",
    }
  }


  return (
    <>
      <div>
        <h1>Cloud Native Maturity Matrix</h1>
        <h2>Question {question}</h2>

        {/*Normal Questions*/}
        {showYesNo && 
          <div>
            <p>{QuestionsFile[question].Question}</p>
        <div>
            <button style={style.button} onClick={() => {
                //Assign response of Yes to the answer object
                let tmp = ans;
                tmp[question-1] = {
                    "question" : QuestionsFile[question]["Question"],
                    "responses" : {0:"Yes"},
                }
                setAns(tmp);
                if(question < 9){
                    setQuestion(++question);
                }else{
                    //Post
                    Backend.storeResults(ans, userNode).then(() => {
                      nav("/result", {state : {userNode : userNode}})
                    });
                }
            }}>Yes</button>
            <button style={style.button} onClick={() => {
                setShowYesNo(false)
              }}>No</button>
        </div></div>}
        {/*Followup Questions*/}
        {!showYesNo &&
        <div>
          <ol>
          <p>{Object.values(QuestionsFile[question].No).map((q,index) =>{
            return (
              <li key={index} style={style.options}>
                  <input style={style.checkbox}
                    type="checkbox"
                    id={`custom-checkbox-${index}`}
                    name={name}
                    value={q}
                    checked={checkedState[index]}
                    onChange={() => {
                      const ncs = checkedState.map((item, ind) => ind === index? !item : item );
                      setCheckedState(ncs);
                    }}
                  />
                  <label htmlFor={`custom-checkbox-${index}`}>{q}</label>
              </li>
            )
          })}</p>
          </ol>
          <div>
        <button onClick={() => {
          setShowYesNo(true);
          //Temporary mutable object
          let tmp = ans;
          //Creates base object property
          tmp[question-1] = {
            "question" : QuestionsFile[question]["Question"],
            "responses" : {
              // ...choices
            }
          }
          //Adds checked box answers to the object
          Object.values(QuestionsFile[question].No).filter((val, ind) => {
            checkedState[ind] ? tmp[question-1].responses[ind] = val : null
          })
          //Updates State
          setAns(tmp);
          if(question < 9){
            setCheckedState(new Array(4).fill(false))
            setQuestion(++question);
          }else{
            //Post
            let userid = 1;
            let sess = Math.floor(Math.random()*1000);
            Backend.storeResults(ans, userNode);
            //Redirect
            nav("/result", {state : {userNode : userNode}});
          }
        }}>Next</button>
        </div>
        </div>
      }
        </div>
        <a href='/history'>Click here to look at previous results</a>
    </>
  )
}



export default Survey
