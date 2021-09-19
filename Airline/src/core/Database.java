
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
   
        String url = "jdbc:sqlite:AirlineDatabase.db";
        
        try {
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/*  METODO DE EJEMPLO PARA LA SELECCION DE DATOS
    --------------------------------------------------------------------------
    public void selectAllArtists(){
        String sql = "SELECT ArtistId, Name FROM artists";
        
        try (
             Statement stmt  = this.conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
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
*/

/*  METODO DE EJEMPLO PARA LA INSERCION DE DATOS
    ----------------------------------------------------------------------------
    
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
*/
    
    public void test(){
        System.out.println("Instancia database funcionando");
    }
    
}
