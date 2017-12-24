package com.tianmaying.servlet;

import com.tianmaying.model.User;
import com.tianmaying.model.UserRepository;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        User user = UserRepository.getByUsername(username);
        if (user == null) {
            dispatchWithMessage(request, response, "该用户不存在");
            System.out.println("用户不存在");
            return;
        }

        if (password == null || !password.equals(user.getPassword())) {
            dispatchWithMessage(request, response, "密码不正确");
            return;
        }

        Cookie c = new Cookie("user", user.getUsername());
        response.addCookie(c);
        
        //保存用户信息,方便页面显示。
        request.getSession().setAttribute("currentUser", user);
        //response.sendRedirect(request.getContextPath() + "/blogs");
        response.sendRedirect(request.getContextPath() + "/blogs?user=" + user.getId());
    }

}
