package gr.aueb.cf.mobilecontacts.service;

import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.MobileContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.mapper.Mapper;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService {
    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException {
        MobileContact mobileContact;
        try {
            if (dao.phoneNumberExists(dto.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number " + dto.getPhoneNumber() + " already exists.");
            }
            mobileContact = Mapper.mapMobileContactInsertDTOToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: Contact with info: %s inserted successfully.\n", mobileContact);
            return dao.insert(mobileContact);

        } catch (PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s already exists\n", dto.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws MobileContactNotFoundException, PhoneNumberAlreadyExistsException {
        MobileContact mobileContact;
        MobileContact newContact;
        try {
            if (!dao.userIdExists(dto.getId())) {
                throw new MobileContactNotFoundException("Contact with id " + dto.getId() + " not found for update.");
            }
            mobileContact = dao.getById(dto.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(dto.getPhoneNumber());
            boolean isPhoneNumberExists = dao.phoneNumberExists(dto.getPhoneNumber());
            if (isPhoneNumberExists && !isPhoneNumberOurOwn) {
                throw new PhoneNumberAlreadyExistsException("There is already registered contact with phone number: " + dto.getPhoneNumber());
            }
            newContact = Mapper.mapMobileContactUpdateDTOToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: %s was updated with new info: %s.\n", mobileContact, newContact);
            return dao.update(dto.getId(), newContact);
        } catch (MobileContactNotFoundException | PhoneNumberAlreadyExistsException e) {
            System.err.println("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteMobileContactById(Long id) throws MobileContactNotFoundException {
        try {
            if (!dao.userIdExists(id)) {
                throw new MobileContactNotFoundException("Contact with id " + id + " not found for delete.");
            }
            System.err.println("MobileContactServiceImpl Logger: Contact with id " + id + " deleted successfully.");
            dao.deleteById(id);
        } catch (MobileContactNotFoundException e) {
            System.err.println("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public MobileContact getMobileContactById(Long id) throws MobileContactNotFoundException {
        MobileContact mobileContact;
        try {
            mobileContact = dao.getById(id);
            if (mobileContact == null) {
                throw new MobileContactNotFoundException("Contact with id " + id + " not found for fetching.");
            }
            return mobileContact;
        } catch (MobileContactNotFoundException e) {
            System.err.println("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    @Override
    public MobileContact getMobileContactByPhoneNumber(String phoneNumber) throws MobileContactNotFoundException {
        MobileContact mobileContact;
        try {
            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if (mobileContact == null) {
                throw new MobileContactNotFoundException("Contact with phone number " + phoneNumber + " not found for fetching.");
            }
            return mobileContact;
        } catch (MobileContactNotFoundException e) {
            System.err.println("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteMobileContactByPhoneNumber(String phoneNumber) throws MobileContactNotFoundException {
        try {
            if (!dao.phoneNumberExists(phoneNumber)) {
                throw new MobileContactNotFoundException("Contact with phone number " + phoneNumber + " not found for fetching.");
            }
            System.err.println("MobileContactServiceImpl Logger: Contact with phone number " + phoneNumber + " deleted successfully.");
            dao.deleteByPhoneNumber(phoneNumber);
        } catch (MobileContactNotFoundException e) {
            System.err.println("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

}
