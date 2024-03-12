package springDB.jdbc_practice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import springDB.jdbc_practice.domain.Member;
import springDB.jdbc_practice.repository.TransactionManagerRepository;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static springDB.jdbc_practice.connection.ConnectionConstants.*;

class TransactionManagerServiceTest {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private TransactionManagerRepository repository;
    private TransactionManagerService service;

    @BeforeEach
    void before(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        repository = new TransactionManagerRepository(dataSource);
        service = new TransactionManagerService(transactionManager, repository);
    }

    @AfterEach
    void after() throws SQLException {
        repository.delete(MEMBER_A);
        repository.delete(MEMBER_B);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A,10000);
        Member memberB = new Member(MEMBER_B, 10000);
        repository.save(memberA);
        repository.save(memberB);

        // when
        service.accountTransfer(MEMBER_A, MEMBER_B, 2000);

        // then
        Member findMemberA = repository.findById(MEMBER_A);
        Member findMemberB = repository.findById(MEMBER_B);
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);

    }

    @Test
    @DisplayName("이체 중 예외발생")
    void accountTransferEX() throws SQLException{
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        repository.save(memberA);
        repository.save(memberEx);

        // when
        assertThatThrownBy(()->service.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000)).isInstanceOf(IllegalStateException.class);

        // then
        Member findMemberA = repository.findById(memberA.getMemberId());
        Member findMemberEx = repository.findById(memberEx.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }
}