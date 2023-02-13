import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class FTM {
    
    //static final String JDCB_DRIVER="org.postgresql.Driver";
    //static final String DB_URL = "jdbc:postgresql://localhost:5432/MyData";
    //static final String user="postgres";
    //static final String pass="Jules0608!!";
    
        
    public static void main(String[] args) {
         String JDCB_DRIVER="org.postgresql.Driver";
         String DB_URL = args[0];
         String user = args[1];
         String pass = args[2];
        
      //   String DB_URL = "jdbc:postgresql://localhost:5432/MyData";
       //  String user="postgres";
        // String pass="Jules0608!!";
        
        // TODO Auto-generated method stub
      try (Connection conn=DriverManager.getConnection(DB_URL,user,pass);
              Statement stmt = conn.createStatement();){
          stmt.executeUpdate("DROP TABLE IF EXISTS Depo;");
          stmt.executeUpdate("DROP TABLE IF EXISTS Curr;");
          stmt.executeUpdate("DROP TABLE IF EXISTS Different;");
          stmt.executeUpdate("DROP TABLE IF EXISTS influence;");
          stmt.executeUpdate("CREATE TABLE Depo(depot char(255), ano char(255));");
           
          stmt.executeUpdate("CREATE TABLE Curr(Depositor1 char(255), Depositor2 char(255));");
          stmt.executeUpdate("CREATE TABLE Different(Depositor1 char(255), Depositor2 char(255));");
          stmt.executeUpdate("INSERT INTO Depo SELECT * from depositor");
          stmt.executeUpdate("CREATE TABLE influence(Depositor1 char(255), Depositor2 char(255));");
            
          
          
          String query="INSERT INTO Curr SELECT depositor.cname, Depo.depot FROM transfer, depositor, Depo where depositor.ano=transfer.src AND Depo.ano=transfer.tgt;";
          int k= stmt.executeUpdate(query);
           query="INSERT INTO Different SELECT * from Curr;";
         stmt.executeUpdate(query);
         query="INSERT INTO influence SELECT * from Curr;";
         stmt.executeUpdate(query);
       //
         stmt.executeUpdate("DROP TABLE IF EXISTS OldTable;");
         stmt.executeUpdate("DROP TABLE IF EXISTS OldTable2;");
            
         stmt.executeUpdate("CREATE TABLE OldTable(Depositor1 char(255), Depositor2 char(255))");
         stmt.executeUpdate("CREATE TABLE OldTable2(Depositor1 char(255), Depositor2 char(255))");
              
          stmt.executeUpdate("INSERT INTO OldTable SELECT * FROM Different;");
          stmt.executeUpdate("INSERT INTO OldTable2 SELECT * FROM Different;");
          
          stmt.executeUpdate("DROP TABLE IF EXISTS Curr;");
        stmt.executeUpdate("CREATE TABLE Curr(Depositor1 char(255), Depositor2 char(255));");
      
       stmt.executeUpdate("INSERT INTO Curr SELECT * FROM OldTable");
    //  System.out.println("Was here3");
       query="INSERT INTO Curr SELECT OldTable2.Depositor1, Depo.depot FROM OldTable2, depositor, Depo, transfer where OldTable2.Depositor2=depositor.cname AND depositor.ano=transfer.src AND transfer.tgt=Depo.ano";
      stmt.executeUpdate(query);
  
        stmt.executeUpdate("DROP TABLE IF EXISTS Different;");
        stmt.executeUpdate("CREATE TABLE Different(Depositor1 char(255), Depositor2 char(255));");
      stmt.executeUpdate("INSERT INTO Different SELECT * FROM Curr EXCEPT SELECT * FROM OldTable;");
    stmt.executeUpdate("DROP TABLE IF EXISTS Curr;");
      stmt.executeUpdate("CREATE TABLE Curr(Depositor1 char(255), Depositor2 char(255));");
      stmt.executeUpdate("INSERT INTO Curr SELECT * FROM Different;");
        stmt.executeUpdate("INSERT INTO influence SELECT * FROM Different ;");
        stmt.executeUpdate("DROP TABLE IF EXISTS Different;");
         while(k!=0){
             
                         stmt.executeUpdate("DROP TABLE IF EXISTS OldTable;");
                         stmt.executeUpdate("DROP TABLE IF EXISTS OldTable2;");
                            
                         stmt.executeUpdate("CREATE TABLE OldTable(Depositor1 char(255), Depositor2 char(255))");
                         stmt.executeUpdate("CREATE TABLE OldTable2(Depositor1 char(255), Depositor2 char(255))");
                              
                          stmt.executeUpdate("INSERT INTO OldTable SELECT * FROM Curr;");
                          stmt.executeUpdate("INSERT INTO OldTable2 SELECT * FROM Curr;");
                          
                          stmt.executeUpdate("DROP TABLE IF EXISTS Curr;");
                        stmt.executeUpdate("CREATE TABLE Curr(Depositor1 char(255), Depositor2 char(255));");
                       query="INSERT INTO Curr SELECT OldTable2.Depositor1, Depo.depot FROM OldTable2, depositor, Depo, transfer where OldTable2.Depositor2=depositor.cname AND depositor.ano=transfer.src AND transfer.tgt=Depo.ano";
                       stmt.executeUpdate(query);
                        stmt.executeUpdate("CREATE TABLE Curr2(Depositor1 char(255), Depositor2 char(255));");
                        stmt.executeUpdate("INSERT INTO Curr2 SELECT * FROM Curr EXCEPT SELECT * FROM influence; ");
                      stmt.executeUpdate("DROP TABLE IF EXISTS Curr;");
                       stmt.executeUpdate("CREATE TABLE Curr(Depositor1 char(255), Depositor2 char(255));");
                        stmt.executeUpdate("INSERT INTO Curr SELECT * FROM Curr2 ");
                        
                      stmt.executeUpdate("DROP TABLE IF EXISTS Curr2;");
                      k= stmt.executeUpdate( "INSERT INTO influence SELECT * FROM Curr;");
                    
          }
          stmt.executeUpdate("DROP TABLE IF EXISTS Depo;");
          stmt.executeUpdate("DROP TABLE IF EXISTS Curr;");
          stmt.executeUpdate("DROP TABLE IF EXISTS Different;");
          stmt.executeUpdate("DROP TABLE IF EXISTS OldTable;");
          stmt.executeUpdate("DROP TABLE IF EXISTS OldTable2;");
          stmt.executeUpdate("DROP TABLE IF EXISTS Depo;");
          
      }
      catch(Exception e) {
          System.out.print("failed");
      }
    
    }

}
