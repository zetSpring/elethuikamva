package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.MemberService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/members")
@RestController
@Api(value = "Elethu Ikamva Members", description = "Operations pertaining to the details about the members of elethu ikamva.")
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/add")
    ResponseEntity<Member> CreateNewMember(@RequestBody Member member){
        Member newMember = memberService.CreateNewMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    List<Member> FindAllMembers() {
        List<Member> memberList = memberService.FindAllMembers();

        return memberList;
    }

    @GetMapping("/invest/{investId}")
    @ResponseStatus(HttpStatus.OK)
    Member FindMemberByInvestId(@PathVariable String investId){
        Member investMember = memberService.FindMemberByInvestmentId(investId);

        return investMember;
    }


    @GetMapping("/{id}")
    Member FindMember(@PathVariable Long id) throws ResourceNotFoundException{
        Member findMem = memberService.FindMemberById(id);

        if(findMem == null){
            throw new ResourceNotFoundException("Member: " + id + "could not be found.");
        }

        return findMem;
    }

    /*Update members*/
    @PutMapping("/update/{investId}")
    ResponseEntity<Member> updateMember(@RequestBody Member member, @PathVariable String investId){
        Member memUpdate = memberService.UpdateMember(member, investId);

        return new ResponseEntity<>(memUpdate, HttpStatus.OK);
    }

    /*Delete (update end date) member*/
    @DeleteMapping("/delete/{investId}")
    ResponseEntity<Member> DeleteMember(@PathVariable String investId) {
        Member member = memberService.DeleteMember(investId);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}
