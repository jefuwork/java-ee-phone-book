package entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Group
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findUserGroupByEmail", query = "SELECT u FROM Group u WHERE u.email = :email")
})
@Table(name = "USER_GROUP")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ADMIN_GROUP = "admin";
    public static final String USERS_GROUP = "user";
    
    @Id
    @Column(name="EMAIL", nullable=false, length=255)
    private String email;
    
    @Column(name="GROUP_NAME", nullable=false, length=32)
    private String groupName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Group [email=" + email + ", groupName=" + groupName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals(other.groupName))
            return false;
        return true;
    }
}
