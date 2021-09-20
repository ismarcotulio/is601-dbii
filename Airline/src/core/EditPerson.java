package core;

import java.sql.*;


/**
 *
 * @author Marco
 */

/*
    ESTA CLASE SOLAMENTE CONTIENE METODOS DE MANIPULACION DE BD PARA LA VISTA EDITAR PERSONA
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
    
    
    public void deletePerson(int id){
        String sql1 = "DELETE FROM CONTROL_LOG WHERE "
                      +String.format("person_FK = %d", id);
        String sql2 = "DELETE FROM PHONE WHERE "
                      +String.format("person_FK = %d", id);
        String sql3 =  "DELETE FROM PERSON WHERE "
                      +String.format("id = %d", id);
        try {
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             pstmt.executeUpdate();
             
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);
             pstmt2.executeUpdate();
             
             PreparedStatement pstmt3 = conn.prepareStatement(sql3);
             pstmt3.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }     
    }
    
    
    public void updatePerson(int id, String passportNumber, String firstName, String middleName, String fisrtSurname, String secondSurname, int roleId, int countryId){
        String sql = "UPDATE PERSON SET "
                +String.format("passportNumber = '%s', ", passportNumber)
                +String.format("firstName = '%s', ", firstName)
                +String.format("middleName = '%s', ", middleName)
                +String.format("fisrtSurname = '%s', ", fisrtSurname)
                +String.format("secondSurname = '%s', ", secondSurname)
                +String.format("role_FK = %s, ", roleId)
                +String.format("country_FK = %s ", countryId)
                +String.format("WHERE id = %d ", id)
                ;
        
        try {
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    
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
    
    
    public ResultSet getCountryIdByName(String name){
        String sql =  "SELECT id FROM COUNTRY "
                      +String.format("WHERE name = '%s'", name);
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }
    
    public ResultSet getRoleIdByName(String name){
        String sql =  "SELECT id FROM ROLE "
                      +String.format("WHERE name = '%s'", name);
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }

    
}
