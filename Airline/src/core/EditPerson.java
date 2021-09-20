package core;

import java.sql.*;


/**
 *
 * @author usuario
 */
public class EditPerson {
    
    private Connection conn;
    
    public EditPerson(Connection conn){
        this.conn = conn;
    }
    
    
    
    public ResultSet getPersons(){
        String sql = "SELECT id, firstName, secretId FROM PERSON";
    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
        
    }
    
    public ResultSet searchPersons(String key){
        String sql = "SELECT id, firstName, secretId FROM PERSON "
                     +"WHERE PERSON.secretId LIKE '%"
                     +String.format("%s", key)
                     +"%'";
    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
        
    }
    
    public ResultSet getCountries(){
        String sql = "SELECT id, name, code FROM COUNTRY";
    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
        
    }
    
    public ResultSet getRoles(){
        String sql = "SELECT id, name FROM ROLE";
    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
        
    }
    
    public ResultSet getPerson(int id){
        String sql =  "SELECT "
                        + "PERSON.id AS personId, passportNumber, firstName, middleName, fisrtSurname, secondSurname, COUNTRY.id AS countryId, COUNTRY.name AS countryName,"
                        + "ROLE.id AS roleId, ROLE.name AS roleName "
                      +"FROM PERSON "
                      +"JOIN COUNTRY ON PERSON.country_FK = COUNTRY.id "
                      +"JOIN ROLE ON PERSON.role_FK = ROLE.id "
                      +String.format("WHERE PERSON.id = %d", id)
                      ;
    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
        
    }
    
    public ResultSet getPersonNumbers(int id){
        String sql =  "SELECT "
                        + "PHONE.id AS phoneId, number, COUNTRY.name AS countryName, COUNTRY.code AS countryCode "
                      +"FROM PHONE "
                      +"JOIN PERSON ON PHONE.person_FK = PERSON.id "
                      +"JOIN COUNTRY ON PHONE.country_FK = COUNTRY.id "
                      +String.format("WHERE PERSON.id = %d", id)
                      ;
    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
        
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
