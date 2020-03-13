package service;

import domain.PageBean;
import domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();

    public void add(User user);

    public void delete(String id);

    public User findOne(int id);

    public void update(User user);

    void deleteSelected(String[] uids);

    //查询分页对象
    PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> conditions);

}
