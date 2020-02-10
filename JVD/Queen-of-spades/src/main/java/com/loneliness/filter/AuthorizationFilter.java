package com.loneliness.filter;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.command.CommandProvider;
import com.loneliness.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthorizationFilter implements Filter {
    private Logger logger = LogManager.getLogger();
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)

            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;


        final HttpSession session = req.getSession();

        if (isNull(session.getAttribute("userId"))) {
            try {
                final String login = req.getParameter("login");
                final String password = req.getParameter("password");
                CommandProvider commandProvider = CommandProvider.getInstance();
                //Command<User,User,User> userCommand= new Authorization();
                Command<User, User, User> userCommand = commandProvider.authorization();
                User user=userCommand.execute(new User.Builder().setLogin(login).setPassword(password).build());
                if (user.getId()>0) {
                    req.getSession().setAttribute("userId", user.getId());
                    req.getSession().setAttribute("login", user.getLogin());
                    req.getSession().setAttribute("type", user.getType());

                    moveToMenu(req, res, user.getType());

                } else {
                    moveToMenu(req, res, User.Type.UNKNOWN);
                }

            } catch (CommandException e) {
                logger.catching(e);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final User.Type type)
            throws ServletException, IOException {

        if (type.equals(User.Type.ADMIN)) {

            req.getRequestDispatcher("user/admin.jsp").forward(req, res);

        } else if (type.equals(User.Type.USER)) {

            req.getRequestDispatcher("user/user.jsp").forward(req, res);

        } else {

            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }


    @Override
    public void destroy() {
    }
}
