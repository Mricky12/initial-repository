package task;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
	 // データベースへの接続情報
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/taskstmdb";
    private static final String USER     = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    
    // データベースのコネクション作成
    public static Connection getConnection() {
    	try{
    		//ドライバのロードを追加しよう
    		Class.forName(DRIVER);
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return null;
        }  catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        return null;
		    }
    	}
		
	}


