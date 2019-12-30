package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    Set<Member> findAllMembers() throws ResourceNotFoundException {
        Set<Member> memberSet = memberService.findAllMembers();
        if(!memberSet.isEmpty())
        {
            return memberSet;
        } else {
            throw new ResourceNotFoundException("Could not find any members to display.");
        }
    }


    @GetMapping("/{id}")
    Member findMember(@PathVariable Long id) throws ResourceNotFoundException{
        Member findMem = memberService.findMemberById(id);
        if(memberService.isMemberActive(findMem))
            return findMem;
        else
            throw new ResourceNotFoundException("Member: " + id + "could not be found.");
    }

    @PutMapping("/delete/{id}")
    void deleteMember(@PathVariable String investmentId) {
        memberService.deleteMember(investmentId);
    }
}
