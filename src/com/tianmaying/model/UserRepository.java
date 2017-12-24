package com.tianmaying.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.tianmaying.utils.DBHelper;
import com.tianmaying.utils.RowMapper;


class UserMapper implements RowMapper {
	public Object map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setEmail(resultSet.getString("email"));
		user.setDescription(resultSet.getString("description"));
		
		return user;
	}
}

public class UserRepository {
    //private UserMapper userMapper;

    public static User getByUserId(Long userId) {
        return (User)DBHelper.queryObject("select id, username, password, email, description from user where id=" + userId, new UserMapper());
    }
    
    public  static List<User> getAll() {
        // TODO：your code here 基于DBHelper实现该方法
    	return DBHelper.queryList("select id, username, password, email, description from `user`", new UserMapper(), User.class);
    }
    
    public static User getByUsername(String username) {
        // TODO：your code here 基于DBHelper实现该方法
        return (User)DBHelper.queryObject("select id, username, password, email, description from `user` where username='" + username + "'", new UserMapper());
    }
    
    public static void add(User user) throws BlogAppException {
        // 留空即可
    	String sql = String.format("insert into user(username, password, email, description, avatar) values ('%s', '%s', '%s', '%s', '%s') ",
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getDescription(),
                user.getAvatar()
                );
    	System.out.println(sql);
        long id = DBHelper.create(sql);
        user.setId(id);
    }

}