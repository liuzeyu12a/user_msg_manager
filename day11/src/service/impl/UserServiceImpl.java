package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.PageBean;
import domain.User;
import service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();


    @Override
    public List<User> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public void add(User user) {
        dao.addUser(user);
    }

    @Override
    public void delete(String id) {
        int i = Integer.parseInt(id);
        dao.deleteUser(i);
    }

    @Override
    public User findOne(int id) {
        return dao.findUserById(id);
    }

    @Override
    public void update(User user) {
        dao.UpdateUser(user);
    }

    //批量删除
    @Override
    public void deleteSelected(String[] uids) {

        if(uids != null && uids.length > 0){
            for (String uid : uids) {
                dao.deleteUser(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> conditions) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        //1.创建空的PageBean对象
        PageBean pageBean = new PageBean();

        //2.调用dao层查询totalCount总记录数:dao.findTotalCount()
        int totalCount = dao.findTotalCount(conditions);
        pageBean.setTotalCount(totalCount);


        //6.计算总页码 totalPage
        int totalPage = totalCount % rows == 0 ? totalCount / rows :totalCount / rows +1;
        if(currentPage > totalPage){
            currentPage = totalPage;
        }

        //3.start = (currentPage - 1) *rows
        int start = (currentPage-1) * rows; //开始位置

        //返回PageBean对象
        pageBean.setTotalPage(totalPage);

        //2.设置当前页面属性currentPage和rows属性
        pageBean.setCurrentPage(currentPage);
        pageBean.setRows(rows);

        //5.调用dao查询list集合：dao.findByPage(int start,int rows)
        if(totalCount > 0){  //如果没查找到就不去按照条件再查找
            List byPage = dao.findByPage(start, rows,conditions);
            pageBean.setList(byPage);
            return pageBean;
        }
        return null;

    }
}
