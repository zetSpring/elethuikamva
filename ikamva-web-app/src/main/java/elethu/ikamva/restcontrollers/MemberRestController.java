package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@Api(value = "Elethu Ikamva Members", description = "Operations pertaining to the details about the members of elethu ikamva.")
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/add")
    ResponseEntity<Member> saveNewMember(@RequestBody Member member){
        Member newMember = memberService.saveNewMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @PostMapping("/save-all")
    ResponseEntity<String> saveAllMembers(@RequestBody List<Member> members){
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
    Member findMemberByInvestId(@PathVariable String investId){
        return memberService.findMemberByInvestmentId(investId);
    }


    @GetMapping("/{id}")
    Member findMemberById(@PathVariable Long id) throws ResourceNotFoundException{
        Member findMem = memberService.findMemberById(id);

        if(findMem == null){
            throw new ResourceNotFoundException("Member: " + id + "could not be found.");
        }

        return findMem;
    }

    /*Update members*/
    @PutMapping("/update")
    ResponseEntity<Member> updateMember(@RequestBody Member member){
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
