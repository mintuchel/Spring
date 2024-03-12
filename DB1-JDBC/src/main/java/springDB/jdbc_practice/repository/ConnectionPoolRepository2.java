package springDB.jdbc_practice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import springDB.jdbc_practice.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

// Connection Pool 을 사용해서 Repository 를 좀 더 업그레이드해보자
// 여기서는 Connection Pool 을 생성하고 이를 DataSource 변수로 참조값을 받는다
// DataSource 는 다양한 커넥션풀과 DriverManager 객체를 받을 수 있다
// DataSource 로 DB 받는거 빼고 나머지는 다 똑같음(save find 등)
@Slf4j
public class ConnectionPoolRepository2 {

    // 각종 Connection Pool 또는 DriverManager 객체를 받을 DataSource 변수
    private final DataSource dataSource;

    // 생성자로 사용할 Connection Pool 받기
    public ConnectionPoolRepository2(DataSource dataSource){
        this.dataSource = dataSource;
    }

    // 이제는 Connection Pool 을 통해서 Connection 객체에 대한 참조를 얻는다
    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("DataSource connection = {}", con);
        log.info("connection class = {}", con.getClass());
        return con;
    }

    public Member save(Member member) throws SQLException {
        log.info("<<< Connection Pool Save >>>");

        String sql = "insert into member(member_id, money) values (?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        }catch(SQLException e){
            log.error("db error");
            e.printStackTrace();
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException{
        log.info("<<< Connection Pool Find >>>");

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
                throw new NoSuchElementException(memberId + "not found");
            }
        } catch (SQLException e) {
            log.error("db error", e);
            e.printStackTrace();
            throw e;
        }finally{
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException{
        log.info("<<< Connection Pool Update >>>");

        String sql = "update member set money=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        }catch(SQLException e){
            log.error("db error", e);
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        log.info("<<< Connection Pool Delete >>>");

        String sql = "delete from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }catch(SQLException e){
            log.error("db error", e);
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    // 자원반환은 JdbcUtils 로 편리하게 할 수 있다
    // 전보다 훨씬 close 가 깔끔하다
    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}