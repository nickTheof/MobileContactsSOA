package gr.aueb.cf.mobilecontacts.mapper;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

public class Mapper {
    /**
     * No instances of this class should be available.
     */
    private Mapper() {};

    public static MobileContact mapMobileContactInsertDTOToContact(MobileContactInsertDTO mobileContactInsertDTO) {
        return new MobileContact(null, mobileContactInsertDTO.getFirstname(), mobileContactInsertDTO.getLastname(), mobileContactInsertDTO.getPhoneNumber());
    }

    public static MobileContact mapMobileContactUpdateDTOToContact(MobileContactUpdateDTO mobileContactUpdateDTO) {
        return new MobileContact(mobileContactUpdateDTO.getId(), mobileContactUpdateDTO.getFirstname(), mobileContactUpdateDTO.getLastname(), mobileContactUpdateDTO.getPhoneNumber());
    }

    public static MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(), mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }
}
