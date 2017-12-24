package com.tianmaying.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.tianmaying.utils.DBHelper;
import com.tianmaying.utils.RowMapper;

class BlogMapper implements RowMapper {
    
    public Object map(ResultSet resultSet) throws SQLException {
        Blog blog = new Blog();
        blog.setId(resultSet.getLong("id"));
        blog.setCreatedTime(resultSet.getDate("createdTime"));
        blog.setTitle(resultSet.getString("title"));
        blog.setContent(resultSet.getString("content"));
        
        User user = UserRepository.getByUserId(resultSet.getLong("author"));
        blog.setAuthor(user);
        
        return blog;
    }
}

class DetailBlogMapper implements RowMapper {
    
    public Object map(ResultSet resultSet) throws SQLException {
        Blog blog = new Blog();
        blog.setId(resultSet.getLong("id"));
        blog.setCreatedTime(resultSet.getDate("createdTime"));
        blog.setTitle(resultSet.getString("title"));
        blog.setContent(resultSet.getString("content"));
        
        User author = new User();
        author.setId(resultSet.getLong("author"));
        author.setUsername(resultSet.getString("username"));
        author.setEmail(resultSet.getString("email"));
        author.setPassword(resultSet.getString("password"));
        author.setDescription(resultSet.getString("description"));

		blog.setAuthor(author);
        return blog;
    }
}



public class BlogRepository {
	public static Blog getDetailBlogById(long id) {
	    return (Blog)DBHelper.queryObject(" select a.*, b.username, b.email, b.password, b.description from blog as a, user as b where a.author = b.id and a.id=" + id, new DetailBlogMapper());
	}
	public static Blog getBlogDetailById(long id) {
	    return (Blog)DBHelper.queryObject(" select a.*, b.username, b.email, b.password, b.description from blog as a, user as b where a.author = b.id and a.id=" + id, new DetailBlogMapper());
	}
	
    public static List<Blog> getAll() {
        return DBHelper.queryList("select * from blog", new BlogMapper(), Blog.class);
    }
    public static List<Blog> getBlogsByUserId(long userId, int page, int size){
    	//return DBHelper.queryList("select * from blog where author=" + userId, new BlogMapper(), Blog.class);
    	String sql = String.format("select * from blog where author= %d limit %d, %d ", userId,(page - 1) * size, size);
    	return DBHelper.queryList(sql, new BlogMapper(), Blog.class);
    }
    
    public static Blog getBlogById(long id) {
        return (Blog)DBHelper.queryObject("select * from blog where id=" + id, new BlogMapper());
    }

    public static void add(Blog blog) throws BlogAppException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User author = blog.getAuthor();
        String authorId = null;
        if(author != null) {
        	authorId = author.getId().toString();
        }
        String sql = String.format("insert into blog(createdTime, author, title, content) values ('%s', %s, '%s', '%s') ", 
                sdf.format(blog.getCreatedTime()),
                authorId,
                blog.getTitle(),
                blog.getContent()
                );
        
        long id = DBHelper.create(sql);
        blog.setId(id); // 设置新插入博客的id
    }

    public static void remove(long id) {
        String sql = "delete from blog where id=" + id;
        DBHelper.update(sql);
    }
    
    public static int countAll() {
    	return DBHelper.queryCount("select count(*) from blog");
    }
    public static int countAll(long user) {
    	return DBHelper.queryCount("select count(*) from blog where author=" + user);
    }
    
    public static List<Blog> getAll(int page, int size) {
        String sql = String.format("select * from blog limit %d, %d", (page - 1) * size, size); 
        System.out.println(sql);
        return DBHelper.queryList(sql, new BlogMapper(), Blog.class);
    }
}

