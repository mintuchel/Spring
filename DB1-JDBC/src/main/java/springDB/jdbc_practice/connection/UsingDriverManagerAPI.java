package springDB.jdbc_practice.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static springDB.jdbc_practice.connection.ConnectionConstants.*;

// 다양한 DB 에서 Connection 을 편리하게 받기 위해
// DriverManager 라는 JDBC 가 제공하는 API 를 사용하여 커넥션을 받자
// 이제부터 커넥션을 받을때는 이 클래스의 getConnection 을 사용한다
@Slf4j
public class UsingDriverManagerAPI {
    public static Connection getConnection(){
        try{
            // 핵심 코드
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection : {}", connection);
            log.info("class : {}", connection.getClass());
            return connection;
        }catch(SQLException e){
            throw new IllegalStateException();
        }
    }
}
