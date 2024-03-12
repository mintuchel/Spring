package springDB.jdbc_practice.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static springDB.jdbc_practice.connection.ConnectionConstants.*;

@Slf4j
public class DBConnectionOnlyTest {

    // 1. DriverManager 사용해서 DB 연결하기
    // 얘는 매번 DB 를 요청할때마다 연결을 새로 한다
    @Test
    void driverManager() throws SQLException{
        //Connection con = DriverManagerEx.getConnection();
        Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection : {}", con);
        log.info("class : {}", con.getClass());
    }

    // 2-1. DataSource 로 DriverManager 객체랑 연결하기
    // 연결할 DB 정보 입력 후 useDataSource 로 전달
    @Test
    void dataSourceDriverManager() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    // 2-2. DataSource 로 Connection Pool 객체랑 연결하기 (실무에서는 이거씀)
    // 연결할 DB 정보 입력 후 useDataSource 로 전달
    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }

    // DataSource 로 데이터베이스와 연결할때는 매번 새 객체를 생성할 필요가 없다!
    // 그냥 getConnection 만 해주면 된다
    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con = dataSource.getConnection();

        log.info("connection = {}", con);
        log.info("class = {}", con.getClass());
    }
}
