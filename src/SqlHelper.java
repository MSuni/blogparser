package src;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SqlHelper {
  
  String user;
  String password;
  String server;
  MysqlDataSource dataSource;
  Connection connection;
  
  public SqlHelper(String user, String password, String server){
    
    this.user = user;
    this.password = password;
    this.server = server;
    
    dataSource = new MysqlDataSource();
    dataSource.setUser(user);
    dataSource.setPassword(password);
    dataSource.setServerName(server);
    dataSource.setDatabaseName("FBminer2");
    
    
  }
  
  public void connect(){
    try {
      connection = dataSource.getConnection();
      System.out.println("Connection Succesful ");
      
      Statement state = (Statement) connection.createStatement();

      System.out.println("getting info...");
      ResultSet result = state.executeQuery("SELECT user_name, user_id, app01_id FROM UserRegistry WHERE app01_id IS NOT NULL LIMIT 10 ");
      System.out.println("result acquired");
      result.next();
      System.out.println("result: " + result.getString(1) + " " + result.getString(2) + " " + result.getString(3));
      result.close();
      state.close();
      connection.close();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
