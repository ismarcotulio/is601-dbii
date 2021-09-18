
package core;

import java.sql.*;


/**
 *
 * @author usuario
 */
public class Database {
    
    private Connection conn;
    
    public Database(){
        this.connect();
    }
    
    private void  connect() {
   
        String url = "jdbc:sqlite:chinook.db";
        
        try {
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void selectAllArtists(){
        String sql = "SELECT ArtistId, Name FROM artists";
        
        try (
             Statement stmt  = this.conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getInt("ArtistId") +  "\t\t" + 
                        rs.getString("Name") + "\t\t" 
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int getArtistsAutoIncrementId(){
        String sql = "SELECT ArtistId FROM artists ORDER BY ArtistId DESC LIMIT 1";
        int id = 0;
        
        try (
             Statement stmt  = this.conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             id = rs.getInt("ArtistId");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id + 1;
    }
    
    public void insertArtist(String name) {
        String sql = "INSERT INTO artists(Name) VALUES(?)";

        try (
            PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
