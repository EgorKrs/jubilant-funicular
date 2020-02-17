package com.loneliness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.command.CommandProvider;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.*;
import com.loneliness.service.ServiceException;
import com.loneliness.service.game.Game;
import com.loneliness.service.game.GameHandler;
import com.loneliness.service.game.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
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
        ServletDispatcher dispatcher=new ServletDispatcher();
        dispatcher.dispatch(session,userCommand,request,response);
    }

}
