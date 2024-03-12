package springDB.jdbc_practice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.PlatformTransactionManager;
import springDB.jdbc_practice.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 이제 TransactionManager 를 사용할 것이다
 * 하지만 여기는 Repository 이므로 TransactionManager 를 사용하지는 않는다
 * 하지만 TransactionManager 내 있는 트랜잭션 동기화를 사용해
 * 자동으로 같은 Connection 을 사용할 수 있도록 해줄 수 있다
 * DataSourceUtils 사용하는 쪽만 보면 된다
 * 나머지는 전꺼랑 같다
 * */
@Slf4j
public class TransactionManagerRepository{
    // Repository 이므로 DB에 접근할 수 있는 DataSource 가 필요
    private final DataSource dataSource;
    public TransactionManagerRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    // TransactionManager 의 트랜잭션 동기화를 사용하려면 
    // DataSourceUtils 를 사용해야한다
    private Connection getConnection() throws SQLException{
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("TransactionManager-DataSourceUtils");
        return con;
    }
    
    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }
    
    public Member save(Member member) throws SQLException{
        String sql = "insert into member(member_id, money) values(?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            return member;
        }catch(SQLException e){
            log.error("save sql error", e);
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException{
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else{
                throw new NoSuchElementException("member not found memberId="+memberId);
            }
        }catch(SQLException e){
            log.error("find sql error");
            throw e;
        }finally{
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException{
        String sql = "update member set money=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            pstmt.executeUpdate();
        }catch(SQLException e){
            log.error("update sql error");
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException{
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }catch(SQLException e){
            log.error("delete sql error");
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }
}
