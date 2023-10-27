package elethu.ikamva.controllers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.service.MemberService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

@RestController
@RequestMapping("/members")
@ApiOperation(
        value = "Elethu Ikamva Members",
        notes = "Operations pertaining to the details about the members of elethu ikamva.")
@SecurityRequirement(name = "bearerAuth")
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    ResponseEntity<Member> saveNewMember(@RequestBody Member member) {
        Member newMember = memberService.saveNewMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @PostMapping("/save-all")
    ResponseEntity<String> saveAllMembers(@RequestBody List<Member> members) {
        memberService.saveAllMembers(members);
        return new ResponseEntity<>("Successfully saved all members", HttpStatus.CREATED);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    List<Member> findAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/invest/{investId}")
    @ResponseStatus(HttpStatus.OK)
    Member findMemberByInvestId(@PathVariable String investId) {
        return memberService.findMemberByInvestmentId(investId);
    }

    @GetMapping("/{id}")
    Member findMemberById(@PathVariable Long id) throws ResourceNotFoundException {
        Member findMem = memberService.findMemberById(id);

        if (findMem == null) {
            throw new ResourceNotFoundException("Member: " + id + "could not be found.");
        }

        return findMem;
    }

    /*Update members*/
    @PutMapping("/update")
    ResponseEntity<Member> updateMember(@RequestBody Member member) {
        Member memUpdate = memberService.updateMember(member);

        return new ResponseEntity<>(memUpdate, HttpStatus.OK);
    }

    /*Delete (update end date) member*/
    @DeleteMapping("/delete/{investId}")
    ResponseEntity<Member> deleteMember(@PathVariable String investId) {
        Member member = memberService.deleteMember(investId);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}
