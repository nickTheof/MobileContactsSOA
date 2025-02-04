package gr.aueb.cf.mobilecontacts.dao;

import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.ArrayList;
import java.util.List;

public class MobileContactDAOImpl implements IMobileContactDAO {
    private static final List<MobileContact> contacts = new ArrayList<>();
    private static Long id = 1L;


    @Override
    public MobileContact insert(MobileContact mobileContact) {
        mobileContact.setId(id++);
        contacts.add(mobileContact);
        return mobileContact;
    }

    @Override
    public MobileContact update(Long id, MobileContact mobileContact) {
        contacts.set(getIndexById(id), mobileContact);
        return mobileContact;
    }

    @Override
    public void deleteById(Long id) {
//        contacts.remove(getIndexById(id));
        contacts.removeIf(contact -> contact.getId().equals(id));
    }

    @Override
    public MobileContact getById(Long id) {
        int positionToReturn = getIndexById(id);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public List<MobileContact> getAll() {
        return new ArrayList<>(contacts);
    }

    @Override
    public MobileContact getByPhoneNumber(String phoneNumber) {
        return contacts.stream()
                .filter(c -> c.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        contacts.removeIf(c -> c.getPhoneNumber().equals(phoneNumber));
    }

    @Override
    public boolean userIdExists(Long id) {
        return contacts.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        return contacts.stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phoneNumber));
    }

    private int getIndexById(Long id) {
        int positionToReturn = -1;
        for (int i = 0; i < contacts.size(); i++){
            if (contacts.get(i).getId().equals(id)){
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
    }

    private int getIndexByPhoneNumber(String phoneNumber) {
        int positionToReturn = -1;
        for (int i = 0; i < contacts.size(); i++){
            if (contacts.get(i).getPhoneNumber().equals(phoneNumber)){
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
    }
}
