package springDB.jdbc_practice.repository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springDB.jdbc_practice.domain.Member;

import java.sql.SQLException;

import static springDB.jdbc_practice.connection.ConnectionConstants.*;

@Slf4j
class ConnectionPoolRepository2Test {

    ConnectionPoolRepository2 repo;

    // TEST 전 항상 호출됨
    @BeforeEach
    void beforeEach(){
        // 지금은 Hikari Connection Pool 을 사용한다
        HikariDataSource dataSource = new HikariDataSource();

        // Hikari Connection Pool 에 받을 DB 정보 입력
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPoolName(PASSWORD);

        // 해당 DB 에 대한 Connection 을 딱 한번만 받고 이제부터 사용하기만 하면 된다
        repo = new ConnectionPoolRepository2(dataSource);
    }

    @Test
    void crud() throws SQLException {

        log.info("<<< CONNECTION POOL TEST START >>>");
        // save
        Member member = new Member("HikariMem", 5);
        repo.save(member);

        // find
        Member findMember = repo.findById(member.getMemberId());
        log.info("findMember ={}", findMember);
        Assertions.assertThat(findMember).isNotNull();

        // update
        repo.update(member.getMemberId(), 999);
        Member updatedMember = repo.findById(member.getMemberId());
        Assertions.assertThat(updatedMember.getMoney()).isEqualTo(999);

        // delete test
        //repo.delete(member.getMemberId());
    }
}