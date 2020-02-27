package com.loneliness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.command.CommandProvider;
import com.loneliness.entity.*;
import com.loneliness.service.game.Game;
import com.loneliness.service.game.GameHandler;
import com.loneliness.service.game.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * класс для обработки команд от клиента
 *
 * @author Egor Krasouski
 */
public class ServletDispatcher {
    private Logger logger = LogManager.getLogger();
    private ObjectMapper mapper = new ObjectMapper();
    private User.Builder userBuilder = new User.Builder();
    private News.Builder newsBuilder = new News.Builder();
    private Profile.Builder profileBuilder = new Profile.Builder();
    private CommandProvider commandProvider = CommandProvider.getInstance();

    void dispatch(HttpSession session, String userCommand, HttpServletRequest request, HttpServletResponse response) throws CommandException, ServletException, IOException {
        if (userCommand != null) {
            userCommand = userCommand.trim();
            try {
                switch (userCommand) {
                    case "delete":

                        Command command = commandProvider.receiveByID(User.class);
                        User user = userBuilder.setId(Integer.parseInt(request.getParameter("id"))).build();
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
                        try {
                            command = commandProvider.create(User.class);
                            command.execute(userBuilder.build());
                            command = commandProvider.receiveAll(User.class);
                            request.setAttribute("users", command.execute(new User.Builder().build()));
                            request.getRequestDispatcher("user/userCRUD.jsp").forward(request, response);
                        } catch (Throwable e) {
                            sendToErrorPage(e, request, response);
                        }

                        break;
                    case "deleteCurrentUser":

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
                        request.setAttribute("login", session.getAttribute("login"));
                        command = commandProvider.receiveInLimit(Message.class);
                        request.setAttribute("messages", command.execute(new Integer[]{0, 50}));

                        request.getRequestDispatcher("message/chat.jsp").forward(request, response);
                        break;
                    case "prepareGame":

                        request.getRequestDispatcher("game/game.jsp").forward(request, response);
                        break;
                    case "startGame":
                        try {
                            Game game = GameHandler.getInstance().getGame(session.getId());
                            if (game != null && game.getMainCard() != null) {
                                if (game.getStage() != Stage.NOT_ENOUGH_MONEY) {
                                    request.setAttribute("mainCard", mapper.writeValueAsString(game.getMainCard()));
                                    request.setAttribute("forehead", mapper.writeValueAsString(game.getForehead()));
                                    request.setAttribute("sonic", mapper.writeValueAsString(game.getSonic()));
                                    request.setAttribute("win", mapper.writeValueAsString(game.getGameData().isGamerWon()));
                                } else {
                                    request.setAttribute("error", "NOT_ENOUGH_MONEY");
                                }
                            } else {
                                Card mainCard = mapper.readValue(request.getParameter("card"), Card.class);

                                game = GameHandler.getInstance().playGame(session.getId(),
                                        (Integer) session.getAttribute("userId"), request.getParameter("jackpot"),
                                        mainCard);
                                if (game.getStage() != Stage.NOT_ENOUGH_MONEY) {
                                    request.setAttribute("mainCard", mapper.writeValueAsString(mainCard));
                                    request.setAttribute("forehead", mapper.writeValueAsString(game.getForehead()));
                                    request.setAttribute("sonic", mapper.writeValueAsString(game.getSonic()));
                                    request.setAttribute("win", mapper.writeValueAsString(game.getGameData().isGamerWon()));
                                } else {
                                    request.setAttribute("error", "NOT_ENOUGH_MONEY");
                                }

                            }
                            request.getRequestDispatcher("/game/game.jsp").forward(request, response);
                        } catch (Throwable e) {
                            logger.catching(e);
                        }
                        break;
                    case "logout":
                        request.getSession().removeAttribute("userId");
                        request.getSession().removeAttribute("login");
                        request.getSession().removeAttribute("type");
                        response.sendRedirect("/");
                        break;
                    case "userCRUD":
                        command = commandProvider.receiveAll(User.class);
                        request.setAttribute("users", command.execute(new User.Builder().build()));
                        request.getRequestDispatcher("user/userCRUD.jsp").forward(request, response);
                        break;
                    case "finishGame":
                        GameHandler.getInstance().finishGame(session.getId());
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
                        News news = newsBuilder.setId(Integer.parseInt(request.getParameter("id"))).
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
                        BigDecimal money = new BigDecimal(request.getParameter("sumOfMoney"));
                        if (money.compareTo(new BigDecimal("0")) > 0) {
                            command = commandProvider.addScoreCommand();
                            Integer st = (Integer) command.execute(new Object[]{session.getAttribute("userId"), money});
                            request.setAttribute("status", st);
                        } else {
                            request.setAttribute("status", "SUM_OF_MONEY_MUST_BE_POSITIVE");
                        }
                        request.getRequestDispatcher("user/addScore.jsp").forward(request, response);
                        break;
                    case "newsRead":
                        command = commandProvider.receiveAll(News.class);
                        request.setAttribute("news", command.execute(new News.Builder().build()));
                        request.getRequestDispatcher("news/readNews.jsp").forward(request, response);
                        break;
                    case "setLanguage_RU":
                        request.getSession().setAttribute("language", "ru");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                    case "setLanguage_EN":
                        request.getSession().setAttribute("language", "en");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                    case "createNewUser":
                        userBuilder = new User.Builder();
                        userBuilder.setType(User.Type.USER);
                        userBuilder.setPassword(request.getParameter("password"));
                        userBuilder.setLogin(request.getParameter("login"));
                        command = commandProvider.create(User.class);
                        int id = (Integer) command.execute(userBuilder.build());
                        if (id > 0) {
                            profileBuilder.setUserID(id);
                            profileBuilder.setScore(600);
                            command = commandProvider.create(Profile.class);
                            command.execute(profileBuilder.build());
                            request.getRequestDispatcher("user/user.jsp").forward(request, response);
                        } else {
                            session.setAttribute("error", "CAN_NOT_CREATE_SUCH_USER");
                            response.sendRedirect("/");
                        }

                        break;
                    case "goToAddScore":
                        request.getRequestDispatcher("user/addScore.jsp").forward(request, response);
                        break;
                }
            } catch (Throwable e) {
                sendToErrorPage(e, request, response);
            }
        } else {
            response.sendRedirect("/");

        }
    }

    private User.Type convert(String type) {
        switch (type) {
            case "ADMIN":
                return User.Type.ADMIN;
            case "USER":
                return User.Type.USER;
            default:
                return User.Type.UNKNOWN;
        }
    }

    private void sendToErrorPage(Throwable e, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        switch (e.getMessage()) {
            case "ERROR_IN_CREATE":
                request.setAttribute("exception", "ERROR_IN_CREATE");
                break;

            case "ERROR_IN_UPDATE":
                request.setAttribute("exception", "ERROR_IN_UPDATE");
                break;

            case "ERROR_IN_DELETE":
                request.setAttribute("exception", "ERROR_IN_DELETE");
                break;

            case "ERROR_IN_RECEIVE":
                request.setAttribute("exception", "ERROR_IN_RECEIVE");
                break;
            case "ERROR_IN_RECEIVE_BY_USER_ID":
            case "ERROR_IN_RECEIVE_ALL":
            case "ERROR_IN_RECEIVE_ALL_IN_LIMIT":
                request.setAttribute("exception", "ERROR_IN_RECEIVE_ALL");
                break;
        }
        request.getRequestDispatcher("error.jsp").forward(request, response);

    }
}
