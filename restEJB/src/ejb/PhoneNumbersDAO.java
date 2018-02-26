package ejb;

import java.util.List;

import entities.PhoneNumber;

public interface PhoneNumbersDAO {
    
    public List<PhoneNumber> getAllPhoneNumbers();
    PhoneNumber getPhoneNumberById(long phoneId);
    public List<PhoneNumber> getPhoneNumbersByEmail(String email);
    
    public PhoneNumber addPhoneNumber(String email, String fullname, String address, String number, boolean isPrivate);
    
    public PhoneNumber updateUser(long phoneId, String email, String fullname, String address, String number);
    
    public boolean deletePhoneNumber(long phoneId);
    public boolean deletePhonesByEmail(String email);
    
    public Long getAmountOfPhones();
    public List<PhoneNumber> getPhonesAtPage(int pageNumber, int pageSize);
}
