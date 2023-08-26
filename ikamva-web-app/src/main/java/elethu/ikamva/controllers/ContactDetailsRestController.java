package elethu.ikamva.controllers;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.service.ContactDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/contacts")
@RestController
@Api(
        value = "Ikamva account ",
        description = "Operations pertaining to the bank accounts of the elethu ikamva investment")
public class ContactDetailsRestController {
    ContactDetailsService contactDetailsService;

    public ContactDetailsRestController(ContactDetailsService contactDetailsService) {
        this.contactDetailsService = contactDetailsService;
    }

    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Successfully retrieved contacts by invest id"),
                @ApiResponse(code = 500, message = "Tell me something I don't know")
            })
    @ApiOperation("Get Contacts by Member Investment ID")
    @GetMapping("/{investId}")
    List<ContactDetails> getMemberContacts(@PathVariable String investId) {
        return contactDetailsService.findMemberContactByInvestId(investId);
    }

    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Successfully retrieved contacts by type"),
                @ApiResponse(code = 500, message = "Tell me something I don't know")
            })
    @ApiOperation("Get Contacts by Contact Type")
    @GetMapping("/type/{type}")
    List<ContactDetails> getMemberContactsByType(@PathVariable String type) {
        return contactDetailsService.findALlContactsByContactType(type);
    }

    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Successfully retrieved all contacts"),
                @ApiResponse(code = 500, message = "Tell me something I don't know")
            })
    @ApiOperation("Get All Contacts")
    @GetMapping("/")
    List<ContactDetails> getAllMemberContacts() {
        return contactDetailsService.findAllContactDetails();
    }

    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Successfully retrieved all contacts"),
                @ApiResponse(code = 500, message = "Tell me something I don't know")
            })
    @ApiOperation("Get All Contacts")
    @PutMapping("/update")
    ResponseEntity<ContactDetails> updateContactDetail(@RequestBody ContactDetails contactDetail) {
        ContactDetails updateContact = contactDetailsService.updateContactDetail(contactDetail);
        return new ResponseEntity<>(updateContact, HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    ResponseEntity<ContactDetails> addContactDetail(@RequestBody ContactDetails contactDetails) {
        ContactDetails newContact = contactDetailsService.saveContactDetail(contactDetails);
        return new ResponseEntity<>(newContact, HttpStatus.OK);
    }

    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Successfully deleted all contacts"),
                @ApiResponse(code = 500, message = "Tell me something I don't know")
            })
    @ApiOperation("Delete member contact details")
    @DeleteMapping("/delete-all/{investId}")
    List<ContactDetails> deleteAllMemberContacts(@PathVariable String investId) {
        return contactDetailsService.deleteContactDetails(investId);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ContactDetails> deleteContactDetailById(@PathVariable Long id) {
        ContactDetails deleteContact = contactDetailsService.deleteContactById(id);
        return new ResponseEntity<>(deleteContact, HttpStatus.OK);
    }
}
