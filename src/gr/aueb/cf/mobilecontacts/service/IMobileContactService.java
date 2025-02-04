package gr.aueb.cf.mobilecontacts.service;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.MobileContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public interface IMobileContactService {
    MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException;
    MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws MobileContactNotFoundException, PhoneNumberAlreadyExistsException;
    void deleteMobileContactById(Long id) throws MobileContactNotFoundException;
    MobileContact getMobileContactById(Long id) throws MobileContactNotFoundException;
    List<MobileContact> getAllContacts();

    MobileContact getMobileContactByPhoneNumber(String phoneNumber) throws MobileContactNotFoundException;
    void deleteMobileContactByPhoneNumber(String phoneNumber) throws MobileContactNotFoundException;
}
