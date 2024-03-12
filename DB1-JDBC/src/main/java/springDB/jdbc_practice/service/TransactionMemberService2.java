package springDB.jdbc_practice.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import springDB.jdbc_practice.domain.Member;
import springDB.jdbc_practice.repository.TransactionManagerRepository;

import java.sql.SQLException;

// 1 과 동일한 구성이지만
// PlatformTransactionManager 대신 TransactionTemplate 을 사용
public class TransactionMemberService2 {
    private final TransactionTemplate txTemplate;
    private final TransactionManagerRepository repository;

    TransactionMemberService2(PlatformTransactionManager transactionManager, TransactionManagerRepository repository){
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.repository = repository;
    }

    public void accountTransfer(String fromId, String toId, int money){
        txTemplate.executeWithoutResult((status)->{
            try{
                bizLogic(fromId, toId, money);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void bizLogic(String fromId, String toId, int money) throws
            SQLException {
        Member fromMember = repository.findById(fromId);
        Member toMember = repository.findById(toId);
        repository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        repository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
