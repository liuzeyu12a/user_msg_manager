package dao;

import domain.User;

/**
 * 用户操作的DAO
 */
import java.util.List;
import java.util.Map;

public interface UserDao {

    public List<User> findAll();

    public int addUser(User auser);

    public void deleteUser(int id);

    public  User findUserById(int id);

    public  void UpdateUser(User user);

    //查询数据库总记录数
    public int findTotalCount(Map<String, String[]> conditions);
    //分页查询List
    List findByPage(int start, int rows, Map<String, String[]> conditions);
}
