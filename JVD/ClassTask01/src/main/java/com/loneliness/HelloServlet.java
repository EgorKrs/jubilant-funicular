package com.loneliness;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloServlet extends HttpServlet {
    private Map<String,Writer> container=new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.process(request, response);
    }
    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session=request.getSession();


        if(session.isNew()){
            container.put(session.getId(),new Writer());
        }



        response.setStatus(200);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        request.setAttribute("status", container.get(session.getId()).status.get());
        System.out.println("session "+session.getId());
        System.out.println("Status "+container.get(session.getId()).status.get());
        dispatcher.forward(request, response);
        //PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        // Get the values of all request parameters
        Enumeration en = request.getParameterNames();
        while(en.hasMoreElements()) {
            // Get the name of the request parameter
            String name = (String)en.nextElement();
          //  out.println("working");

            // Get the value of the request parameter
            String value = request.getParameter(name);
            Thread thread=container.get(session.getId()).thread;
            if(!thread.isAlive()) {
                container.get(session.getId()).value=value;
                thread.start();
            }
            if(!container.get(session.getId()).value.equals(value)){
                container.get(session.getId()).value+=value;
            }


        }
        //out.close();
    }


    private class Writer implements Runnable{
        public Thread thread=new Thread(this);
        private AtomicInteger status=new AtomicInteger(0);
        public String value=" ";
        public Writer(String value) {
            this.value=value;
        }
        public Writer(){}

        @Override
        public void run() {
            write();
        }
        private void write(){
            try (FileWriter writer=new FileWriter("data/data.txt",true)){
                for (int i = 0; i < value.length(); i++) {
                    writer.append(value.charAt(i));
                    TimeUnit.SECONDS.sleep(1);
                    status.set(i*100/value.length());
                    System.out.println(status.get());
                }
                status.set(100);
                System.out.println(status.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
