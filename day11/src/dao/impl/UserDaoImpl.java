package dao.impl;

import dao.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public int addUser(User auser) {
        //编写SQL语句
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        int update = jdbcTemplate.update(sql, auser.getName(), auser.getGender(), auser.getAge(), auser.getAddress(),
                auser.getQq(), auser.getEmail());
        return update;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        String sql = "delete from user where id = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public User findUserById(int id) {

        //sql
        String sql = "select * from user where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void UpdateUser(User user) {

        String sql = "update user set name= ?,gender= ?,age= ?,address= ?,qq= ?,email= ? where id = ?";
        jdbcTemplate.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> conditions) {
        //定义条件查询sql模板
        String sql = "select count(*) from user WHERE  1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList(); //存储请求参数
        for (String key : conditions.keySet()) {
            //排除掉分页查询的条件
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = conditions.get(key)[0];    //获取value值
            if(value != null && !"".equals(value)){
                //value不为空
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        return jdbcTemplate.queryForObject(sb.toString(), Integer.class,params.toArray());
    }

    @Override
    public List findByPage(int start, int rows, Map<String, String[]> conditions) {
        String sql = "select * from user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List<Object> params = new ArrayList<>();
        for (String key : conditions.keySet()) {
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = conditions.get(key)[0];
            if(value != null && !"".equals(value)){
                sb.append(" and "+key+" like ?");
                params.add("%"+value+"%");
            }
        }
        sb.append(" limit ? ,? ");
        params.add(start);
        params.add(rows);

        System.out.println(sb);
        System.out.println(params);

        List<User> query = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class),params.toArray());
        return query;
    }
}
