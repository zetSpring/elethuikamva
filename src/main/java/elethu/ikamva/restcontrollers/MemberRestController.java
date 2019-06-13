package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.services.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

    @GetMapping("/members")
    Set<Member> findAllMembers() {
        return memberService.findAllMembers();
    }


    @GetMapping("/member/{id}")
    Member findMember(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @PutMapping("/delete/member/{id}")
    void deleteMember(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        memberService.deleteMember(member);
    }
}
