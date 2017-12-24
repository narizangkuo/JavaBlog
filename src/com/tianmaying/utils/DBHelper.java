package com.tianmaying.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tianmaying.model.Blog;
import com.tianmaying.model.Project;
import com.tianmaying.model.User;


public class DBHelper {
    
    public static String driver = "com.mysql.jdbc.Driver";
    public static String connectionString = "jdbc:mysql://127.0.0.1:3306/tianmayingblog";
    public static String username = "root";
    public static String password = "d5000";
    
    //此处做过优化
    //让JVM加载数据库驱动类到内存中，只需要执行一次即可。因此可以做一个小优化，把这一行代码放到DBHelper的静态代码块中。
    
    static {
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }        
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(connectionString, username, password);
    }
    public static void clear(ResultSet resultSet, Statement statement, Connection connection) {
    	try {
    		if(resultSet !=null) {
    			resultSet.close();
    		}
    		
    		if(statement != null) {
    			statement.close();
    		}
    		
    		if(connection !=null) {
    			connection.close();
    		}
    	}catch (SQLException ignored) {
    		
    	}
    }
    
    public static Object queryObject(String sql, RowMapper rowMapper) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DBHelper.getConnection();
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.first()) {//定位到第一条记录
				return rowMapper.map(resultSet);//单个的话，返回的是第一份。多个相当于把每份分别封装到list里面，然后返回。
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			clear(null, statement, connection);
		}
    	
    }
    
    public static int queryCount(String sql) {
    	Connection connection = null;
    	PreparedStatement statement = null;
    	
    	try {
    		connection = DBHelper.getConnection();
    		statement = connection.prepareStatement(sql);
    		ResultSet resultSet = statement.executeQuery();
    		if(resultSet.first()) {
    			return resultSet.getInt(1);
    		}
    		return 0;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return 0;
    	} finally {
    		clear(null, statement, connection);
    	}
    }
    
    public static int executeUpdate(String sql) {
    	Connection connection = null;
    	PreparedStatement statement = null;
    	
    	try {
    		connection = DBHelper.getConnection();
    		statement = connection.prepareStatement(sql);
    		return statement.executeUpdate();
    	} catch(Exception e) {
    		e.printStackTrace();
    		return 0;
    	} finally {
    		clear(null, statement, connection);
    	}
    }
    
    public static <T> List<T> queryList(String sql, RowMapper rowMapper, Class<T> clazz){
	   Connection connection = null;
	   PreparedStatement statement = null;
	   
	   try {
		   List<T> list = new ArrayList<T>();
		   connection = DBHelper.getConnection();
		   statement = connection.prepareStatement(sql);
		   //System.out.println("sql:   " + sql);
		   ResultSet resultSet = statement.executeQuery();
		   while(resultSet.next()) {
			   T object = (T) rowMapper.map(resultSet);
			   list.add(object);
		   }
		   return list;
	   } catch (Exception e) {
		   e.printStackTrace();
		   return null;
	   } finally {
		   clear(null, statement, connection);
	   }
    }

    public static long create(String sql) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();  
            if (resultSet.next()) {  
               return resultSet.getLong(1);  
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            clear(resultSet, statement, connection);
        }
    }
    
    public static int update(String sql) {
    	Connection connection = null;
    	PreparedStatement statement = null;
    	
    	try {
    		connection = getConnection();
    		statement = connection.prepareStatement(sql);
    		return statement.executeUpdate();
    	} catch(Exception e) {
    		e.printStackTrace();
    		return 0;
    	} finally {
    		clear(null, statement, connection);
    	}
    }
    

}
