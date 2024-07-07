package gift.web;

import gift.service.member.MemberService;
import gift.service.product.ProductService;
import gift.web.dto.MemberDto;
import gift.web.dto.Token;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final ProductService productService;

    public MemberController(MemberService memberService, ProductService productService) {
        this.memberService = memberService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.getMembers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberDto> getMemberByEmail(@PathVariable String email) {
        return new ResponseEntity<>(memberService.getMemberByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<MemberDto> registerMember(@RequestBody MemberDto memberDto) {
        memberService.createMember(memberDto);
        //토큰 생성후 리턴 구현해야함
    }

    @PutMapping("/{email}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable String email, @RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.updateMember(email, memberDto), HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);
        return ResponseEntity.ok("Delete Success");
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberDto memberDto) {
        HttpHeaders headers = new HttpHeaders();
        Token token = memberService.createJWT(memberDto);
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(token);
    }
}
