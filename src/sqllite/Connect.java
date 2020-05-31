package sqlLite;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.*;
import java.sql.PreparedStatement;


public class Connect {
    
/**
* Connect to a sample database
*/
private Statement db=null;
private Connection conn = null;
private Connection connect(){
        
    String url = "jdbc:sqlite:F:/sqlLite/Crawler.db";
    
    //Connection conn = null;
    this.conn=null;
    try {
        this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
     return this.conn;
         
}  
    
public boolean contains(String URL)
{
  String sql = "SELECT count(link) FROM links WHERE link='"+URL+"'";
        
        try
        {   
            Connection conn = this.connect();        
            Statement stmt  = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // loop through the result set
            int num = rs.getInt(1);
            if(num>0){return true;}
            else {return false;}
        
        }catch (SQLException e) {
        System.out.println(e.getMessage());
        }finally
            {
            if(conn!=null)
                try
                {
                conn.close();
                }catch(SQLException e){}
            }
        
        //conn.close();
return false;
}

public void add(String url)
{
 
    String sql = "INSERT INTO links(link) VALUES(?)";

    try (Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1,url);        
        pstmt.executeUpdate();
        } catch (SQLException e) {
           // System.out.println(e.getMessage());
        }// end of catch
 
    
    
    
} // end of add(String url)
    

    
    /**
     * @param args the command line arguments
     */
    
    
    
    
    public static void main(String[] args) {
        //connect();
    }
    
 
}