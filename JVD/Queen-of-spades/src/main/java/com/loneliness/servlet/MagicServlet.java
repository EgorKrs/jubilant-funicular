package com.loneliness.servlet;

import com.loneliness.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Класс сервлета для обраблотки запросов
 * @author Egor Krasouski
 * @version 2.1+
 * 
 */
public class MagicServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (CommandException e) {
            logger.catching(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        }  catch (CommandException e) {
        logger.catching(e);
    }

    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {

        HttpSession session = request.getSession();
        String userCommand = request.getParameter("command");
        ServletDispatcher dispatcher = new ServletDispatcher();
        dispatcher.dispatch(session,userCommand,request,response);
    }

}
