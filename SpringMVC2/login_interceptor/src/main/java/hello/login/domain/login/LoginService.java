package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    // login check 
    // 맞으면 해당 Member 객체 반환
    // 틀리면 null 반환
    public Member login(String loginId, String password){
        return memberRepository.findByLoginId(loginId)
                .filter(m->m.getPassword().equals(password))
                .orElse(null);
    }
}
