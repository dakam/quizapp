package com.dakam.quiz.serverlets.quizap;

import com.dakam.quiz.quizarea.Thequiz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "Quiz", value = "/quiz")
public class Quiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object quiz = session.getAttribute("quiz");
        int marks=0;
        String nextqn="";
        int answered=0;

        if(quiz == null) {
            session.setAttribute("quiz", new Thequiz());


        }
        Thequiz myquiz = (Thequiz) session.getAttribute("quiz");

        String ans = request.getParameter("ans");
        String pseq = request.getParameter("pseq");


        //String nn = "2, 3, 5, 7, 11";
        //System.out.println(ans +" ans and seq="+nn);
       // System.out.println("hhh="+myquiz.getQuizMap().get(pseq.trim()));

        if(ans != null && !ans.equals("") && pseq !=null && !pseq.equals("")) {

            pseq = pseq.trim();

           int cans = (int) myquiz.getQuizMap().get(pseq);
            myquiz.addAnswered();
            myquiz.RecorddoneQuiz(pseq);
            System.out.println("my answer ="+ans+" correct="+cans);


            if(Integer.valueOf(ans) == cans) {

                myquiz.addMarks(1);

            } else {
                myquiz.addMarks(0);

           }

        }






            marks = myquiz.getMarks();
            answered = myquiz.getAnswered();

        session.setAttribute("quiz", myquiz);

            if(answered == 5) {

                String seq = nextqn;
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();

                out.println("<html>");
                out.println("<head>");
                out.println("<link href=\"css/styles.css\" type=\"text/css\" rel=\"stylesheet\" />");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"outer\">");
                out.println("<h1> The Number Quiz </h1> <br/>" +
                        "Your  Score is" +marks+" </span> <br/>"+
                        "You have completed the Number quiz, with score of <span class=\"sequence\"> "+marks+"/5</span> <br/>"
                        );

                out.println("</div>");
                out.println("</body></html>");

            } else {


                Iterator quiziterator = myquiz.getQuizMap().entrySet().iterator();
                while (quiziterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)quiziterator.next();
                    String qn =  (String) mapElement.getKey() ;
                    if(! myquiz.getDoneQuiz().contains(qn)) {

                        nextqn = qn;
                    }

                }



                String seq = nextqn;
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();

                out.println("<html>");
                out.println("<head>");
                out.println("<link href=\"css/styles.css\" type=\"text/css\" rel=\"stylesheet\" />");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"outer\">");
                out.println("<h1> The Number Quiz </h1> <br/>" +
                        "Your current Score is" +"<span class=\"answer\"> "+marks+" </span> <br/>"+
                        "Guess the next Number in the sequence <br/>" +
                        "<span class=\"sequence\"> "+seq+"</span> <br/>"+
                        "<form action=\"quiz\">" +
                        "<input type=\"hidden\" name=\"pseq\" value=\""+seq + " \" /> <br/>"+
                        "Your Answer <input name =\"ans\" type=\"text\" /> <br/>" +
                        "<input type=\"submit\" name=\"submit\" /> "+
                        "</form>");

                out.println("</div>");
                out.println("</body></html>");
            }







    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }
    }

