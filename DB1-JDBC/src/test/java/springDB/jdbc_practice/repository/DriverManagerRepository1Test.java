package springDB.jdbc_practice.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import springDB.jdbc_practice.domain.Member;

import java.sql.SQLException;

@Slf4j
class DriverManagerRepository1Test {

    DriverManagerRepository1 repo = new DriverManagerRepository1();

    @Test
    void crud() throws SQLException {

        // save test
        Member member = new Member("DrvMngMem", 555);
        repo.save(member);

        // find test
        Member findMember = repo.findById(member.getMemberId());
        log.info("findMember ={}", findMember);
        Assertions.assertThat(findMember).isEqualTo(member);

        // update test
        repo.update(member.getMemberId(), 12345);
        Member updatedMember = repo.findById(member.getMemberId());
        Assertions.assertThat(updatedMember.getMoney()).isEqualTo(12345);

        // delete test
        //repo.delete(member.getMemberId());
    }
}