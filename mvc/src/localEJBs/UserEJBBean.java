package localEJBs;

import entities.User;
import entities.Group;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@Stateless
public class UserEJBBean {
    
    private EntityManager em = Persistence.createEntityManagerFactory("mvc").createEntityManager();
    
    @RolesAllowed("admin_role")
    public User createUser(User user) {
        if (!validateUser(user)) {
            return null;
        }
        try {
            user.setPassword(encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        Group group = new Group();
        group.setEmail(user.getEmail());
        group.setGroupName(Group.USERS_GROUP);
        try {
            em.getTransaction().begin();
            user = em.merge(user);
            group = em.merge(group);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return user;
    }
    
    @RolesAllowed("admin_role")
    public List<User> getAllUsers() {
        try {
            em.getTransaction().begin();
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            em.getTransaction().commit();
            return users;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("exception in pulling from db");
            return null;
        }
        
    }
    
    //@RolesAllowed("admin_role")
    public User findUserByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("findUserByEmail", User.class);
        query.setParameter("email", email);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return user;
    }
    
    //@RolesAllowed("admin_role")
    public Group findUserGroupByEmail(String email) {
        TypedQuery<Group> query = em.createNamedQuery("findUserGroupByEmail", Group.class);
        query.setParameter("email", email);
        Group group = null;
        try {
            group = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return group;
    }
    
    @RolesAllowed("admin_role")
    public boolean deleteUser(String email) {
        try {
            em.getTransaction().begin();
            User user = findUserByEmail(email);
            //User User = (User) em.find(User.class, email);
            em.remove(user);
            Group group = findUserGroupByEmail(email);
            em.remove(group);
            
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
    
    // Utils:
    
    /**
     * Returns SHA-256 encoded string
     * @param password - the string to be encoded
     * @return SHA-256 encoded string
     * @throws UnsupportedEncodingException if UTF-8 is not supported by the system
     * @throws NoSuchAlgorithmException if SHA-256 is not supported by the system
     */
    public static String encodeSHA256(String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }
    
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private static boolean validateUser(User user) {
        String name = user.getUserName();
        String pass = user.getPassword();
        String email = user.getEmail();
        
        if (name != null && pass != null && email != null) {
            if (name.length() <= 255 && email.length() <= 255) {
                CharSequence inputStr = email;
                Pattern pattern = Pattern.compile(new String ("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$"));
                Matcher matcher = pattern.matcher(inputStr);
                if(matcher.matches()) {
                     return true;
                } else {
                     return false;
                }
            }
        }
        return false;
    }
}
