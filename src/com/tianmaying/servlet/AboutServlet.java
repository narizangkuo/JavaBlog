package com.tianmaying.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianmaying.model.Project;
import com.tianmaying.utils.DBHelper;

@WebServlet("/about")
public class AboutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 这里通过Servlet的init()方法为你了准备了一些测试数据，如果你不太清楚init的方法，请访问：
     * https://course.tianmaying.com/servlet-and-jsp+servlet
     */
    public void init() throws ServletException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            List<Project> projects = new ArrayList<>();
            projects.add(new Project("Onboard", "Onboard是一个使用Java和OSGi技术开发的新一代敏捷软件开发协作平台", "http://onboard.cn", "./img/onboard.png"));
            projects.add(new Project("天码营", "descript致力于打造新一代的技术学习服务平台，提供创新并且专业的内容、工具与服务，帮助学习者实现个人价值", "http://tianmaying.com", "./img/tmy.png"));
            projects.add(new Project("OSGi中文社区", "OSGi中文社区是国内最具影响力的Java模块化开发技术社区,有数万OSGi从业人员在网站上学习和交流", "http://osgi.com.cn", "./img/catty.jpeg"));

            connection = DBHelper.getConnection();
            statement = connection.prepareStatement("insert into project(name, description, url, logo) values(?,?,?,?)");

            for (Project project : projects) {
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                statement.setString(3, project.getUrl());
                statement.setString(4, project.getLogo());
                statement.addBatch(); // 增加多条插入语句
            }

            statement.executeBatch(); // 执行批处理，这里即一次性插入多条记录
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ignored) {
            
            }
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null; 
        try {
            List<Project> projects = new ArrayList<>();
            
            // TODO：your code here 从数据库中获取project表的所有记录
            connection = DBHelper.getConnection();
            statement = connection.prepareStatement("SELECT name, description, url, logo from project");
            resultSet = statement.executeQuery();
            
            //下装数据
            Project project;
         // resultSet.next()返回的是一个布尔类型的值，而且将指针指向下一条记录 
            while(resultSet.next()) {
            	//System.out.println(resultSet.getString("name") + resultSet.getString("description") + resultSet.getString("url") + resultSet.getString("logo"));
            	project = new Project(resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("url"), resultSet.getString("logo"));
            	projects.add(project);
            }       
            request.setAttribute("projects", projects);
            request.getRequestDispatcher("/WEB-INF/jsp/about.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                
                if (statement != null) {
                    statement.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ignored) {
            
            }
        }
    }
}
