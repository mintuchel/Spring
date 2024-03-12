package springDB.jdbc_practice.repository;

import lombok.extern.slf4j.Slf4j;
import springDB.jdbc_practice.connection.UsingDriverManagerAPI;
import springDB.jdbc_practice.domain.Member;

import java.sql.*;
import java.util.NoSuchElementException;

// 여기서는 Connection Pool 을 배우기 전 사용했던
// DriverManager 로 DB 에 저장해본다
// 그냥 하나의 Connection 만 사용하는 DriverManager 를 사용한다
@Slf4j
public class DriverManagerRepository1 {

    private Connection getConnection() {
        return UsingDriverManagerAPI.getConnection();
    }

    // DB 에 Member 저장
    public Member save(Member member) throws SQLException {
        // DB 에 적용할 SQL 정의
        String sql = "insert into member(member_id, money) values (?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection(); // Connection 객체 받기

            // SQL 등록
            pstmt = con.prepareStatement(sql);
            // 등록한 SQL 에 필요한 매개변수 값 대입
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());

            // 준비된 SQL 을 실제 데이터베이스에 전달
            // executeUpdate() 는 저장, 변경할때  사용
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

    // DB 에서 Member 조회
    // 조회할때는 executeQuery 임!
    public Member findById(String memberId) throws SQLException{
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        
        // 조회하면 API 는 ResultSet 객체로 반환해서
        // ResultSet 으로 받아줘야함
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            // executeQuery 는 조회할때
            // 그리고 얘는 조회된 데이터를 ResultSet 으로 반환해줌
            rs = pstmt.executeQuery();

            // next 는 한번은 무조건 호출해야함
            // rs.next() 가 첫번째 원소임
            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else{
                // 만약 첫번째 원소가 없다면
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

    // DB Member 수정
    public void update(String memberId, int money) throws SQLException{
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

    // DB Member 삭제
    public void delete(String memberId) throws SQLException {
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

    // 사용한 자원 모두 반환!
    // 정리할때는 역순으로 정리
    // 리소스는 꼭 정리해줘야함!
    private void close(Connection con, Statement stmt, ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if(stmt != null){
            try{
                stmt.close();
            }catch(SQLException e){
                log.info("error", e);
            }
        }

        if(con != null){
            try{
                con.close();
            }catch(SQLException e){
                log.info("error", e);
            }
        }
    }
}