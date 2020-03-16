package service.impl;

import dao.AdminDao;
import dao.impl.AdminDaoImpl;
import domain.Admin;
import service.AdminService;

public class AdminServiceImpl implements AdminService{

    private AdminDao adminDao = new AdminDaoImpl();
    @Override
    public Admin login(Admin loginAdmin) {
        return adminDao.login(loginAdmin);
    }

    @Override
    public Admin regsiter(String username, String password) {
        return adminDao.regsiter(username, password);
    }
}
