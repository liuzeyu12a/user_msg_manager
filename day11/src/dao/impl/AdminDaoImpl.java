package dao.impl;

import dao.AdminDao;
import domain.Admin;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class AdminDaoImpl implements AdminDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Admin login(Admin loginAdmin) {

        try {
            String sql = "select * from user where username = ? and password = ?";
            Admin admin = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class),
                    loginAdmin.getUsername(), loginAdmin.getPassword());
            return admin;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Admin regsiter(String username, String password) {
        if(username != null && !username.equals("") && password != null && !password.equals("")){
            String sql_query = "SELECT * from user where username= ?";
            List<Admin> query = jdbcTemplate.query(sql_query, new BeanPropertyRowMapper<Admin>(Admin.class), username);
            if( query.size() > 0){
                return null;   //查到数据库中已经存在的用户名
            }

            //没重复部分
            String sql = "insert into user(username,password)value(?,?)";
            int zs = jdbcTemplate.update(sql, username,password);
            System.out.println(zs);

            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            return admin;
        }
        return null;
    }



}
