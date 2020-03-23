package service;

import domain.Admin;

public interface AdminService {
     public Admin login(Admin loginAdmin);
    Admin regsiter(String username, String password);
    Admin findUsername(String username);

}
