package com.tianmaying.servlet;

import com.tianmaying.model.User;
import com.tianmaying.service.UserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    
    private void dispatchWithMessage(HttpServletRequest request,
                                     HttpServletResponse response,
                                     String message) throws ServletException, IOException {
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        request.setAttribute("message", message);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        dispatchWithMessage(request, response, null);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().length() == 0) {
            dispatchWithMessage(request, response, "用户名不能为空");
            return;
        }

        if (!userService.exist(username)) {
            dispatchWithMessage(request, response, "该用户不存在");
            return;
        }
        
        User user = userService.login(username, password);
        
        if (user == null) {
            dispatchWithMessage(request, response, "密码不正确");
            return;
        }
        request.getSession().setAttribute("currentUser", user);
        response.sendRedirect(request.getContextPath() + "/blogs?user=" + user.getId());
    }

}
