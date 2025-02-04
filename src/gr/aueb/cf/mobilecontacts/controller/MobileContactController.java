package gr.aueb.cf.mobilecontacts.controller;

import gr.aueb.cf.mobilecontacts.core.serializer.Serializer;
import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.MobileContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.mapper.Mapper;
import gr.aueb.cf.mobilecontacts.model.MobileContact;
import gr.aueb.cf.mobilecontacts.service.IMobileContactService;
import gr.aueb.cf.mobilecontacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobilecontacts.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

public class MobileContactController {
    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO insertDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            String errorVector = ValidationUtil.validate(insertDTO);
            if (!errorVector.isEmpty()) {
                return "Error." + "Validation error\n" + errorVector + "\n";
            }
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error.\n" + e.getMessage() + "\n";
        }
    }

    public String updateContact(MobileContactUpdateDTO updateDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            String errorVector = ValidationUtil.validate(updateDTO);
            if (!errorVector.isEmpty()) {
                return "Error." + "Validation error\n" + errorVector  + "\n";
            }
            mobileContact = service.updateMobileContact(updateDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error.\nWrong during update\n" + e.getMessage() + "\n";
        } catch (MobileContactNotFoundException e) {
            return "Error.\n" + e.getMessage() + "\n";
        }
    }

    public String deleteContactById(Long id) {
        try {
            service.deleteMobileContactById(id);
            return "OK\nContact was deleted.";
        } catch (MobileContactNotFoundException e) {
            return "Error.\nWrong in delete. Contact not found.\n";
        }
    }

    public String getContactById(Long id) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getMobileContactById(id);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (MobileContactNotFoundException e) {
            return "Error.\nContact not found.\n";
        }
    }

    public List<String> getAllContacts() {
        List<MobileContact> contacts;
        List<String> serializedList = new ArrayList<>();
        MobileContactReadOnlyDTO readOnlyDTO;
        String serializedDTO;
        contacts = service.getAllContacts();
        for (MobileContact mobileContact : contacts) {
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            serializedDTO = Serializer.serializeDTO(readOnlyDTO);
            serializedList.add(serializedDTO);
        }
        return serializedList;
    }

    public String deleteContactByPhoneNumber(String phoneNumber) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getMobileContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            service.deleteMobileContactByPhoneNumber(phoneNumber);
            return "OK\nContact was deleted." + Serializer.serializeDTO(readOnlyDTO);
        } catch (MobileContactNotFoundException e) {
            return "Error.\nWrong in delete. Contact not found.\n";
        }
    }

    public String getContactByPhoneNumber(String phoneNumber) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getMobileContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (MobileContactNotFoundException e) {
            return "Error.\nContact not found.\n";
        }
    }

}
