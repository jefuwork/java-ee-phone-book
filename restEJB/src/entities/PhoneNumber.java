package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
    @NamedQuery(name = "findPhonesByEmail", query = "SELECT u FROM PhoneNumber u WHERE u.email = :email OR u.isPrivate = false")
})
@XmlRootElement
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHONE_ID")
    private long phoneId;
    
    @Column(name="EMAIL", nullable=false, length=255)
    private String email;
    
    @Column(name="FULLNAME", nullable=false, length=255)
    private String fullname;
    
    @Column(name="ADDRESS", nullable=false, length=255)
    private String address;
    
    @Column(name="NUMBER", unique=true, nullable=false, length=255)
    private String number;

    @Column(name="PRIVATE", nullable=false)
    private boolean isPrivate;
    
    public PhoneNumber() {
        
    }
    
    public PhoneNumber(long phoneId, String email, String fullname, String address, String number, boolean isPrivate) {
        super();
        this.phoneId = phoneId;
        this.email = email;
        this.fullname = fullname;
        this.address = address;
        this.number = number;
        this.isPrivate = isPrivate;
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "PhoneNumber [phoneId=" + phoneId + ", email=" + email + ", fullname=" + fullname + ", address="
                + address + ", number=" + number + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + (int) (phoneId ^ (phoneId >>> 32));
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
        PhoneNumber other = (PhoneNumber) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (fullname == null) {
            if (other.fullname != null)
                return false;
        } else if (!fullname.equals(other.fullname))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (phoneId != other.phoneId)
            return false;
        return true;
    }
}
