package localEJBs;

import java.util.List;

import javax.ejb.Local;

import entities.User;

@Local
public interface UserEJBLocal {
    public User createUser(User user);
    public User findUserByEmail(String email);
    public boolean deleteUser(String email);
    public List<User> getAllUsers();
}
