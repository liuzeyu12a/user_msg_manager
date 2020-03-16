package dao;

import domain.Admin;

public interface AdminDao {
    public Admin login(Admin loginAdmin);
    public Admin regsiter(String username, String password);

}
