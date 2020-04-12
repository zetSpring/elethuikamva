package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/members")
@RestController
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @PostMapping
//    Member saveMember(Member member){
//        return memberService.saveOrUpdateMember(member);
//    }

    @GetMapping("/")
    List<Member> findAllMembers() throws ResourceNotFoundException {
        List<Member> memberList = memberService.findAllMembers();
        if(!memberList.isEmpty())
        {
            return memberList;
        } else {
            throw new ResourceNotFoundException("Could not find any members to display.");
        }
    }

    @GetMapping("/invest/{investId}")
    Member findMemberByInvestId(@PathVariable String investId){
        Member investMember = memberService.findMemberByInvestmentId(investId);

        return investMember;
    }


    @GetMapping("/{id}")
    Member findMember(@PathVariable Long id) throws ResourceNotFoundException{
        Member findMem = memberService.findMemberById(id);
        if(memberService.isMemberActive(findMem))
            return findMem;
        else
            throw new ResourceNotFoundException("Member: " + id + "could not be found.");
    }

    /*Update members*/
    @PutMapping("/update/{investId}")
    Member updateMember(@RequestBody Member member, @PathVariable String investId) throws ResourceNotFoundException{

        Member memUpdate = memberService.findMemberByInvestmentId(investId);
        if (memberService.isMemberActive(member)){
            memberService.updateMember(member, investId);
            return memUpdate;
        }
        else
            throw new ResourceNotFoundException("Member investment id: " + investId + " could not be found for an update");
    }


    @PutMapping("/delete/{id}")
    void deleteMember(@PathVariable String investmentId) {
        memberService.deleteMember(investmentId);
    }
}
