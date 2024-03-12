package springDB.jdbc_practice.connection;

// DB 접속을 위해 필요한 기본 정보를
// 편리하게 상수로 정의해두자
public abstract class ConnectionConstants {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
