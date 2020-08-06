package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.MemberService;
import io.swagger.annotations.Api;
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

    @PostMapping
    Member CreateNewMember(@RequestBody Member member){
        return memberService.CreateNewMember(member);
    }

    @GetMapping("/")
    List<Member> FindAllMembers() throws ResourceNotFoundException {
        List<Member> memberList = memberService.FindAllMembers();
        if(!memberList.isEmpty())
        {
            return memberList;
        } else {
            throw new ResourceNotFoundException("Could not find any members to display.");
        }
    }

    @GetMapping("/invest/{investId}")
    Member FindMemberByInvestId(@PathVariable String investId){
        Member investMember = memberService.FindMemberByInvestmentId(investId);

        return investMember;
    }


    @GetMapping("/{id}")
    Member FindMember(@PathVariable Long id) throws ResourceNotFoundException{
        Member findMem = memberService.FindMemberById(id);
        if(memberService.IsMemberActive(findMem))
            return findMem;
        else
            throw new ResourceNotFoundException("Member: " + id + "could not be found.");
    }

    /*Update members*/
    @PutMapping("/update/{investId}")
    Member updateMember(@RequestBody Member member, @PathVariable String investId) throws ResourceNotFoundException{

        Member memUpdate = memberService.FindMemberByInvestmentId(investId);
        if (memberService.IsMemberActive(member)){
            memberService.UpdateMember(member, investId);
            return memUpdate;
        }
        else
            throw new ResourceNotFoundException("Member investment id: " + investId + " could not be found for an update");
    }

    /*Delete (update end date) member*/
    @DeleteMapping("/delete/{id}")
    void DeleteMember(@PathVariable String investmentId) {
        memberService.DeleteMember(investmentId);
    }
}
