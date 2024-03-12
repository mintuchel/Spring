package hello.servlet.Classes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    // 모든 테스트들은 독립적으로 실행되어야함
    // 따라서 @AfterEach를 달아주면 모든 테스트 이후에 해당 함수를 무조건 실행함
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        // given
        Member member = new Member("hello", 20);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());

        Assertions.assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll(){
        // given
        Member member1 = new Member("son",7);
        Member member2 = new Member("kim",3);
        Member member3 = new Member("hwang",11);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> result = memberRepository.findAll();

        // then
        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(result).contains(member1,member2,member3);

    }
}