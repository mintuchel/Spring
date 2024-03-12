package springDB.jdbc_practice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springDB.jdbc_practice.domain.Member;
import springDB.jdbc_practice.repository.TransactionManagerRepository;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class TransactionManagerService {

    // Service 단에서 PlatformTransactionManager 사용
    private final PlatformTransactionManager transactionManager;
    // Repository 참조하기 위한 변수 선언
    private final TransactionManagerRepository repository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException{
        // Transaction 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            bizLogic(fromId, toId, money);
            // Transaction 성공 시 commit
            transactionManager.commit(status);
        } catch (Exception e) {
            // bizLogic 에서 Exception 발생 시 rollback
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = repository.findById(fromId);
        Member toMember = repository.findById(toId);

        repository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        repository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember){
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체 중 예외발생");
        }
    }
}
