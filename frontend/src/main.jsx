import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'

import Survey from './routes/survey/survey.jsx'
import Result from './routes/results/result.jsx'
import History from './routes/history/history.jsx'

const router = createBrowserRouter([
  {
    path:"/",
    element: <Survey/>,
  },
  {
    path:"/result",
    element: <Result/>
  },
  {
    path:"/history",
    element: <History/>
  }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
