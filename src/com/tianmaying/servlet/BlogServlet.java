package com.tianmaying.servlet;

import com.tianmaying.model.Blog;
import com.tianmaying.model.BlogRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
       String id = request.getParameter("id");

        if (id != null) {
            try {
                Blog blog = BlogRepository.getDetailBlogById(Long.parseLong(id));
                if (blog != null) {
                    request.setAttribute("blog", blog);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/item.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            } catch (Exception e) {
                //
            }
            
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/404.jsp");
        dispatcher.forward(request, response);
        
    }
}
