package com.tianmaying.servlet;

import com.tianmaying.model.Blog;
import com.tianmaying.model.BlogAppException;
import com.tianmaying.model.BlogRepository;
import com.tianmaying.model.User;
import com.tianmaying.model.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (email == null || !isEmail(email) || username == null || username.length() < 6 ||
                password == null || password.length() < 8) {
            request.setAttribute("message", "输入信息有误(邮件格式正确，用户名不少于6位，密码不少于8位)");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        User user = new User(email, username, password);
        try {
            UserRepository.add(user);
         // TODO: your code here 为该用户创建一篇默认博客
            String title = "这是你在天码营的第一篇博客";
            String content = "天码营秉承让技术学习更加高效和便捷的使命，致力于打造新一代的技术学习服务平台，提供创新并且专业的内容、工具与服务，帮助学习者与从业者实现个人价值";
            Blog blog = new Blog(title, content);
            blog.setAuthor(user);
            BlogRepository.add(blog);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("currentUser", user);
        response.sendRedirect(request.getContextPath() + "/blogs?user=" +user.getId());

    }

    public static boolean isEmail(String email) {
        final String patternString = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(patternString);
        final Matcher mat = pattern.matcher(email);
        return mat.find();
    }
}
