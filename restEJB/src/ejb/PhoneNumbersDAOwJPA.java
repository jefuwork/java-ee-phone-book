package ejb;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import entities.PhoneNumber;

@Stateless
@LocalBean
public class PhoneNumbersDAOwJPA implements PhoneNumbersDAO {

    @PersistenceContext(unitName="restEJB")
    private EntityManager em;
    
    //@RolesAllowed("admin_role")
    public PhoneNumber getPhoneNumberById(long phoneId) {
        return em.find(PhoneNumber.class, phoneId);
    }
    
    //@RolesAllowed("admin_role")
    public List<PhoneNumber> getAllPhoneNumbers() {
        List<PhoneNumber> phones = em.createQuery("SELECT u FROM PhoneNumber u", PhoneNumber.class).getResultList();
        return phones;
    }
    
    //@RolesAllowed({"admin_role", "user_role"})
    public List<PhoneNumber> getPhoneNumbersByEmail(String email) {
        TypedQuery<PhoneNumber> query = em.createNamedQuery("findPhonesByEmail", PhoneNumber.class);
        query.setParameter("email", email);
        List<PhoneNumber> phonesByEmail = query.getResultList();
        return phonesByEmail;
    }
    
    //@RolesAllowed("admin_role")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PhoneNumber addPhoneNumber(String email, String fullname, String address, String number, boolean isPrivate) {
        if (!validatePhone(email, fullname, address, number)) {
            return null;
        }
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setAddress(address);
        phoneNumber.setEmail(email);
        phoneNumber.setFullname(fullname);
        phoneNumber.setNumber(number);
        phoneNumber.setIsPrivate(isPrivate);
        em.persist(phoneNumber);
        return phoneNumber;
    }
    
    //@RolesAllowed("admin_role")
    public PhoneNumber updateUser(long phoneId, String email, String fullname, String address, String number) {
        if (!validatePhone(email, fullname, address, number)) {
            return null;
        }
        /* We better use UserTransaction:
         * 
         * UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
         * transaction.begin();
         * PhoneNumber phoneNumber = (PhoneNumber) em.find(PhoneNumber.class, phoneId);
         * phoneNumber.setAddress(address);
         * phoneNumber.setEmail(email);
         * phoneNumber.setFullname(fullname);
         * phoneNumber.setNumber(number);
         * transaction.commit();
         * 
         * But I have an exception: "Lookup failed for 'java:comp/UserTransaction' in SerialContext"
         * so the workaround is using query for updating the entity.
         */
        
        Query query = em.createQuery("UPDATE PhoneNumber c SET c.email = :email, c.fullname = :fullname, c.number = :number, c.address = :address " +
                "WHERE c.phoneId = :id");
        
        query.setParameter("email", email);
        query.setParameter("fullname", fullname);
        query.setParameter("number", number);
        query.setParameter("address", address);
        query.setParameter("id", phoneId);
        query.executeUpdate();
        PhoneNumber pn = (PhoneNumber) em.find(PhoneNumber.class, phoneId);
        return pn;
    }
    
    //@RolesAllowed("admin_role")
    public boolean deletePhoneNumber(long phoneId) {
        try {
            PhoneNumber phoneNumber = (PhoneNumber) em.find(PhoneNumber.class, phoneId);
            em.remove(phoneNumber);
            return true;
        } catch (Exception e) {
            System.out.println("exception in deleting phone by id from db [rest]");
            return false;
        }
    }
    
    //@RolesAllowed("admin_role")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deletePhonesByEmail(String email) {
        List<PhoneNumber> phones = getPhoneNumbersByEmail(email);
        if (phones == null) {
            return false;
        } else {
            for (PhoneNumber phone : phones) {
                em.remove(phone);
            }
            return true;
        }
    }
    
    //@RolesAllowed("admin_role")
    public Long getAmountOfPhones() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(PhoneNumber.class)));
        Long count = em.createQuery(countQuery).getSingleResult();
        return count;
    }
    
    //@RolesAllowed("user_role");
    public Long getAmountOfPhonesByEmail(String email) {
        return (long) getPhoneNumbersByEmail(email).size();
    }
    
    //@RolesAllowed("admin_role")
    public List<PhoneNumber> getPhonesAtPage(int pageNumber, int recordsPerPage) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PhoneNumber> criteriaQuery = criteriaBuilder.createQuery(PhoneNumber.class);
        Root<PhoneNumber> from = criteriaQuery.from(PhoneNumber.class);
        CriteriaQuery<PhoneNumber> select = criteriaQuery.select(from);
        TypedQuery<PhoneNumber> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult((pageNumber - 1) * recordsPerPage);
        typedQuery.setMaxResults(recordsPerPage);
        List<PhoneNumber> fooList = typedQuery.getResultList();
        return fooList;
    }
    
    //@RolesAllowed("admin_role")
    public List<PhoneNumber> getSearchedPhones(String searchString) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PhoneNumber> criteriaQuery = criteriaBuilder.createQuery(PhoneNumber.class);
        Root<PhoneNumber> root = criteriaQuery.from(PhoneNumber.class);
        
        criteriaQuery.select(root);
        
        Predicate rez = null;
        if(searchString!=null){
            Predicate conditionId = criteriaBuilder.like(root.<String>get("phoneId"), "%" + searchString + "%");
            Predicate conditionEmail = criteriaBuilder.like(criteriaBuilder.upper(root.<String>get("email")), "%" + searchString.toUpperCase() + "%");
            Predicate conditionFullName = criteriaBuilder.like(criteriaBuilder.upper(root.<String>get("fullname")), "%" + searchString.toUpperCase() + "%");
            Predicate conditionAddress = criteriaBuilder.like(criteriaBuilder.upper(root.<String>get("address")), "%" + searchString.toUpperCase() + "%");
            Predicate conditionPhone = criteriaBuilder.like(root.<String>get("number"), "%" + searchString + "%");
            rez = criteriaBuilder.or(conditionId, conditionEmail, conditionFullName, conditionAddress, conditionPhone);
        }
        criteriaQuery.where(rez);

        TypedQuery<PhoneNumber> typedQuery = em.createQuery(criteriaQuery);

        if (searchString != null) {
            typedQuery.setParameter("name", searchString);
        }

        //typedQuery.setFirstResult((pageNumber - 1) * recordsPerPage);
        //typedQuery.setMaxResults(recordsPerPage);
        List<PhoneNumber> fooList = typedQuery.getResultList();
        return fooList;
    }
    
    private static boolean validatePhone(String email, String fullname, String address, String number) {
        if (email != null && fullname != null && address != null && number != null) {
            if (email.length() <= 255 && email.length() <= 255 && address.length() <= 255 && number.length() <= 255) {
                CharSequence inputStr = email;
                Pattern pattern = Pattern.compile(new String ("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$"));
                Matcher matcher = pattern.matcher(inputStr);
                if(matcher.matches()) {
                    CharSequence inputPhone = number;
                    Pattern patternPhone = Pattern.compile(new String ("((?:\\+|00)[17](?: |\\-)?|(?:\\+|00)[1-9]\\d{0,2}(?: |\\-)?|(?:\\+|00)1\\-\\d{3}(?: |\\-)?)?(0\\d|\\([0-9]{3}\\)|[1-9]{0,3})(?:((?: |\\-)[0-9]{2}){4}|((?:[0-9]{2}){4})|((?: |\\-)[0-9]{3}(?: |\\-)[0-9]{4})|([0-9]{7}))"));
                    Matcher matcherPhone = patternPhone.matcher(inputPhone);
                    if(matcherPhone.matches()) {
                         return true;
                    }
                }
            }
        }
        return false;
    }
}
