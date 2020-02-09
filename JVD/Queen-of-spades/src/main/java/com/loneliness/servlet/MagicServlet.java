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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

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
        String userCommand = request.getParameter("command");
        Command command;
        User user;
        News news;
        User.Builder userBuilder = new User.Builder();
        News.Builder newsBuilder = new News.Builder();
        CommandProvider commandProvider = CommandProvider.getInstance();
        if (userCommand != null) {
            switch (userCommand) {
                case "delete":
                    command = commandProvider.receiveByID(User.class);
                    user = userBuilder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("user", command.execute(user));
                    request.getRequestDispatcher("user/deleteUser.jsp").forward(request, response);
                    break;
                case "update":
                    command = commandProvider.receiveByID(User.class);
                    user = userBuilder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("user", command.execute(user));
                    request.getRequestDispatcher("user/updateUser.jsp").forward(request, response);
                    break;
                case "create":
                    userBuilder = new User.Builder();
                    userBuilder.setType(convert(request.getParameter("type")));
                    userBuilder.setPassword(request.getParameter("password"));
                    userBuilder.setLogin(request.getParameter("login"));
                    command = commandProvider.create(User.class);
                    command.execute(userBuilder.build());
                    command = commandProvider.receiveAll(User.class);
                    request.setAttribute("users", command.execute(new User.Builder().build()));
                    request.getRequestDispatcher("user/userCRUD.jsp").forward(request, response);
                    break;
                case "deleteCurrentUser":
                    response.sendRedirect(request.getContextPath() + "/userCRUD");
                    command = commandProvider.delete(User.class);
                    command.execute(userBuilder.setId(Integer.parseInt(request.getParameter("id"))).build());
                    command = commandProvider.receiveAll(User.class);
                    request.setAttribute("users", command.execute(new User.Builder().build()));
                    request.getRequestDispatcher("user/userCRUD.jsp").forward(request, response);
                    break;
                case "updateCurrentUser":
                    command = commandProvider.update(User.class);
                    userBuilder = new User.Builder();
                    userBuilder.setId(Integer.parseInt(request.getParameter("id")));
                    userBuilder.setLogin(request.getParameter("newLogin"));
                    userBuilder.setType(convert(request.getParameter("newType")));
                    userBuilder.setAvatarId(Integer.parseInt(request.getParameter("newAvatar")));
                    userBuilder.setPassword(request.getParameter("newPassword"));
                    command.execute(userBuilder.build());
                    command = commandProvider.receiveAll(User.class);
                    request.setAttribute("users", command.execute(new User.Builder().build()));
                    request.getRequestDispatcher("user/userCRUD.jsp").forward(request, response);
                    break;
                case "startChat":
                    request.setCharacterEncoding("UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    request.setAttribute("login", session.getAttribute("login"));
                    command = commandProvider.receiveInLimit(Message.class);
                    request.setAttribute("messages", command.execute(new Integer[]{0, 50}));
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
                    } catch (Throwable e) {
                        logger.catching(e);
                    }
                    break;
                case "logout":
                    request.getSession().removeAttribute("userId");
                    request.getSession().removeAttribute("login");
                    request.getSession().removeAttribute("type");
                    request.getRequestDispatcher("../login.jsp").forward(request, response);
                    break;
                case "userCRUD":
                    command = commandProvider.receiveAll(User.class);
                    request.setAttribute("users", command.execute(new User.Builder().build()));
                    request.getRequestDispatcher("user/userCRUD.jsp").forward(request, response);
                    break;
                case "finishGame":
                    GameHandler.getInstance().finishGame(session.getId());
                    request.getRequestDispatcher("../game/game.jsp").forward(request, response);
                    break;
                case "newsCRUD":
                    command = commandProvider.receiveAll(News.class);
                    request.setAttribute("news", command.execute(new News.Builder().build()));
                    request.getRequestDispatcher("news/workWithNews.jsp").forward(request, response);
                    break;
                case "createNews":
                    newsBuilder = new News.Builder();
                    newsBuilder.setText(request.getParameter("text"));
                    command = commandProvider.create(News.class);
                    command.execute(newsBuilder.build());
                    command = commandProvider.receiveAll(News.class);
                    request.setAttribute("news", command.execute(new News.Builder().build()));
                    request.getRequestDispatcher("news/workWithNews.jsp").forward(request, response);
                    break;
                case "updateNews":
                    command = commandProvider.update(News.class);
                    news = newsBuilder.setId(Integer.parseInt(request.getParameter("id"))).
                            setText(request.getParameter("text")).build();
                    command.execute(news);
                    command = commandProvider.receiveAll(News.class);
                    request.setAttribute("news", command.execute(news));
                    request.getRequestDispatcher("news/workWithNews.jsp").forward(request, response);
                    break;
                case "deleteNews":
                    command = commandProvider.delete(News.class);
                    command.execute(newsBuilder.setId(Integer.parseInt(request.getParameter("id"))).build());
                    command = commandProvider.receiveAll(News.class);
                    news = newsBuilder.setId(Integer.parseInt(request.getParameter("id"))).build();
                    request.setAttribute("news", command.execute(news));
                    request.getRequestDispatcher("news/workWithNews.jsp").forward(request, response);
                    break;
                case "showScore":
                    command = commandProvider.receiveByUserId(Account.class);
                    Account account = (Account) command.execute(Integer.parseInt((String) session.getAttribute("userId")));
                    request.setAttribute("score", account.getSumOfMoney());
                    request.getRequestDispatcher("user/score.jsp").forward(request, response);
                    break;
                case "addScore":
                    command = commandProvider.addScoreCommand();
                    request.setAttribute("status", command.execute(Integer.parseInt(request.getParameter("sumOfMoney"))));
                    break;


            }
        } else {
            Enumeration names = request.getParameterNames();
            String name, value;
            while (names.hasMoreElements()) {
                name = (String) names.nextElement();
                value = request.getParameterValues(name)[0];
                System.out.println("name " + name + "   value " + value);
                GameHandler.getInstance().finishGame(session.getId());
                request.getRequestDispatcher("../game/game.jsp").forward(request, response);
            }
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
