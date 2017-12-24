package com.tianmaying.servlet;

import com.tianmaying.model.Blog;
import com.tianmaying.model.BlogAppException;
import com.tianmaying.model.BlogRepository;
import com.tianmaying.model.User;
import com.tianmaying.model.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/create")
public class CreateBlogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/create.jsp");
        dispatcher.forward(request, response);
    } 
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String message;
        
        if (title == null || title.trim().length() == 0) {
            message = "博客标题不能为空";
        } else if (content == null || content.trim().length() == 0) {
            message = "博客内容不能为空";
        } else  {
            
            Blog blog = new Blog(title, content);
            
            //获取用户信息
            User user = (User) request.getSession().getAttribute("currentUser");
            
            //如果取用户失败
            if (user == null) {
    			Cookie[] cookies = request.getCookies();
    			
    			if (cookies != null) {
    				for (Cookie cookie : cookies) {
    					
    					if (cookie.getName().equals("user")) {
    							String username = cookie.getValue();
    						if (username != null) {
    							User author = UserRepository.getByUsername(username);   
    				            blog.setAuthor(author);
    				            System.out.println("username:" + username);
    							request.setAttribute("currentUser", UserRepository.getByUsername(username));
    							break;
    						}
    					}
    				}
    			}    
            }
            try {
            	
                BlogRepository.add(blog);
            } catch (BlogAppException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/blog?id=" + blog.getId());
            return;
        }
       
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/create.jsp");
        dispatcher.forward(request, response);
    }
}
