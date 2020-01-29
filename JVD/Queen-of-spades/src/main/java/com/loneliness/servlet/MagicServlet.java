package com.loneliness.servlet;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.User;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import com.loneliness.command.common_comand.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MagicServlet extends HttpServlet {
    Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        try {
            process(req, resp);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        try {
            process(req, resp);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {
        //response.sendRedirect(request.getContextPath() + "/userCRUD");
        HttpSession session = request.getSession();
        String userCommand=request.getParameter("command");

        Command command;
        User user;
        User.Builder builder = new User.Builder();
        if(userCommand!=null){

            switch (userCommand){
                case "delete":
                    command=new ReceiveByID(new <User>UserDAO());
                    user=builder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("user",command.execute(user));
                    request.getRequestDispatcher("deleteUser.jsp").forward(request, response);
                        break;
                case "update":
                    command=new ReceiveByID(new <User>UserDAO());
                    user=builder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("user",command.execute(user));
                    request.getRequestDispatcher("updateUser.jsp").forward(request, response);
                    break;
                case "create":
                    builder=new User.Builder();
                    builder.setType(convert(request.getParameter("type")));
                    builder.setPassword(request.getParameter("password"));
                    builder.setLogin(request.getParameter("login"));
                    command=new Create<User>(new UserDAO());
                    command.execute(builder.build());
                    command = new ReceiveAll<>(new <User>UserDAO());
                    request.setAttribute("users", command.execute(new User.Builder().build()));
                    request.getRequestDispatcher("userCRUD.jsp").forward(request, response);
                    break;
                case "deleteCurrentUser":
                    response.sendRedirect(request.getContextPath() + "/userCRUD");
                    command=new Delete<User>(new UserDAO());
                    command.execute(builder.setId(Integer.parseInt(request.getParameter("id"))).build());
                    break;
                case "updateCurrentUser":
                    command=new Update<User>(new UserDAO());
                    builder=new User.Builder();
                    builder.setId(Integer.parseInt(request.getParameter("id")));
                    builder.setLogin(request.getParameter("newLogin"));
                    builder.setType(convert(request.getParameter("newType")));
                    builder.setAvatarId(Integer.parseInt(request.getParameter("newAvatar")));
                    builder.setPassword(request.getParameter("newPassword"));
                    command.execute(builder.build());
                    request.getRequestDispatcher("userCRUD.jsp").forward(request, response);
                    break;
                case "startChat":
                    request.setCharacterEncoding("UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    request.setAttribute("login",session.getAttribute("login"));
                    request.getRequestDispatcher("message/chat.jsp").forward(request, response);
                    break;
            }
        }
        else {
            try {
                command = new ReceiveAll<>(new <User>UserDAO());
                request.setAttribute("users", command.execute(new User.Builder().build()));
            } catch (DAOException | ServiceException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("userCRUD.jsp").forward(request, response);
        }

    }
    private User.Type convert(String type){
        switch (type){
            case "ADMIN":
                return User.Type.ADMIN;
            case "USER":
                return User.Type.USER;
            default:return User.Type.UNKNOWN;
        }
    }
}
