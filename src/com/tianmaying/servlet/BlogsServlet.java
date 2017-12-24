package com.tianmaying.servlet;

import com.tianmaying.model.Blog;
import com.tianmaying.model.BlogRepository;
import com.tianmaying.utils.Pager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/blogs")
public class BlogsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        
    	List<Blog> blogs;
    	int page = 1;
    	int size = 5;//默认显示5条记录
    	int count = 0;
    	String user = request.getParameter("user");
    	String pageString = request.getParameter("page");
    	
    	if(pageString !=null) {
    		try {
    			page = Integer.parseInt(pageString);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	if(user != null && !user.isEmpty()) {
    		try {
    			Long userId = Long.parseLong(user);
    			count = BlogRepository.countAll(userId);
    			blogs =BlogRepository.getBlogsByUserId(userId, page, size);
    		} catch(Exception e) {
    			e.printStackTrace();
    			blogs = BlogRepository.getAll(page, size);
    			count = BlogRepository.countAll();
    		}
    	}else {
    		//blogs = BlogRepository.getAll();
    		blogs = BlogRepository.getAll(page, size);
    		count = BlogRepository.countAll();
    	}
    	request.setAttribute("blogs", blogs);
    	request.setAttribute("user", user);
    	request.setAttribute("pager", new Pager(page, size, count));
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/WEB-INF/jsp/list.jsp");
        dispatcher.forward(request, response);
    }
}
