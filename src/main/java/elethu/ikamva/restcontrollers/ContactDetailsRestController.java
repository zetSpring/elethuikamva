package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.services.ContactDetailsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/v1/contacts")
@RestController
@Api(value = "Ikamva account ", description = "Operations pertaining to the bank accounts of the elethu ikamva investment")
public class ContactDetailsRestController {

    ContactDetailsService contactDetailsService;

    public ContactDetailsRestController(ContactDetailsService contactDetailsService) {
        this.contactDetailsService = contactDetailsService;
    }

    @GetMapping("/{investId}")
    List<ContactDetails> getMemberContacts(@PathVariable String investId){
        List<ContactDetails> contactDetailsList = contactDetailsService.getContactDetail(investId);

        return contactDetailsList;
    }

    @GetMapping("/type/{type}")
    List<ContactDetails> getMemberContactsBytype(@PathVariable String type){
        List<ContactDetails> contactDetailsList = contactDetailsService.findALlContactTypes(type);

        return contactDetailsList;
    }

    @GetMapping("/")
    List<ContactDetails> getAllMemberContacts(){
        List<ContactDetails> contactDetailsList = contactDetailsService.findAllContactDetails();

        return contactDetailsList;
    }


}
