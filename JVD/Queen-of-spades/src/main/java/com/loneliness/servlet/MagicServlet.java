package com.loneliness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.command.common_comand.*;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.MessageDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Card;
import com.loneliness.entity.Message;
import com.loneliness.entity.User;
import com.loneliness.service.ServiceException;
import com.loneliness.service.common_service.*;
import com.loneliness.service.game.Game;
import com.loneliness.service.game.GameHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class MagicServlet extends HttpServlet {
    Logger logger = LogManager.getLogger();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        try {
            process(req, resp);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ServiceException | CommandException e) {
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
        } catch (ServiceException | CommandException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException, CommandException {
        //response.sendRedirect(request.getContextPath() + "/userCRUD");
        HttpSession session = request.getSession();
        String userCommand=request.getParameter("command");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Command command;
        User user;
        User.Builder builder = new User.Builder();
        if(userCommand!=null){
            switch (userCommand){
                case "delete":
                    command = new ReceiveByID(new ReceiveByIdService(new <User>UserDAO()));
                    user=builder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("user",command.execute(user));
                    request.getRequestDispatcher("deleteUser.jsp").forward(request, response);
                        break;
                case "update":
                    command = new ReceiveByID(new UpdateService(new <User>UserDAO()));
                    user=builder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("user",command.execute(user));
                    request.getRequestDispatcher("updateUser.jsp").forward(request, response);
                    break;
                case "create":
                    builder=new User.Builder();
                    builder.setType(convert(request.getParameter("type")));
                    builder.setPassword(request.getParameter("password"));
                    builder.setLogin(request.getParameter("login"));
                    command = new Create<User>(new CreateService<>(new UserDAO()));
                    command.execute(builder.build());
                    command = new ReceiveAll<User>(new ReceiveAllService<>(new <User>UserDAO()));
                    request.setAttribute("users", command.execute(new User.Builder().build()));
                    request.getRequestDispatcher("userCRUD.jsp").forward(request, response);
                    break;
                case "deleteCurrentUser":
                    response.sendRedirect(request.getContextPath() + "/userCRUD");
                    command = new Delete<User>(new DeleteService<>(new UserDAO()));
                    command.execute(builder.setId(Integer.parseInt(request.getParameter("id"))).build());
                    break;
                case "updateCurrentUser":
                    command = new Update<User>(new UpdateService<>(new UserDAO()));
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
                    command = new ReceiveInLimit<Message>(new ReceiveInLimitService<>(new MessageDAO()));
                    Collection<Message> messages = (Collection<Message>) command.execute(new Integer[]{0, 50});
                    request.setAttribute("messages", messages);
                    request.getRequestDispatcher("message/chat.jsp").forward(request, response);
                    break;
                case "startGame":
                    try {
                        Game game = GameHandler.getInstance().getGame(session.getId());
                        if (game != null && game.getMainCard() != null) {
                            request.setAttribute("mainCard", mapper.writeValueAsString(game.getMainCard()));
                            request.setAttribute("forehead", mapper.writeValueAsString(game.getForehead()));
                            request.setAttribute("sonic", mapper.writeValueAsString(game.getSonic()));
                            request.setAttribute("win", mapper.writeValueAsString(game.getGameData().isGamerWon()));
                        } else {
                            Card mainCard = mapper.readValue(request.getParameter("card"), Card.class);

                            game = GameHandler.getInstance().playGame(session.getId(),
                                    (Integer) session.getAttribute("userId"), request.getParameter("jackpot"),
                                    mainCard);
                            request.setAttribute("mainCard", mapper.writeValueAsString(mainCard));
                            request.setAttribute("forehead", mapper.writeValueAsString(game.getSonic()));
                            request.setAttribute("sonic", mapper.writeValueAsString(game.getForehead()));
                            request.setAttribute("win", mapper.writeValueAsString(game.getGameData().isGamerWon()));

                        }
                        request.getRequestDispatcher("../game/game.jsp").forward(request, response);
                        break;
                    } catch (Throwable e) {
                        logger.catching(e);
                    }
            }
        }
        else {
            try {
                command = new ReceiveAll<User>(new ReceiveAllService<>(new <User>UserDAO()));
                request.setAttribute("users", command.execute(new User.Builder().build()));
            } catch (DAOException e) {
                logger.catching(e);
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
