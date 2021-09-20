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
    
    public int verifyPersonFields(String firstName, String firstSurname, String passportNumber, String secondName, String secondSurname){
        if(firstName.matches("") == false && firstSurname.matches("") == false && passportNumber.matches("") == false ){
            if(firstName.matches("[A-Za-z]*")){
                if(firstSurname.matches("[A-Za-z]*")){
                    if(passportNumber.matches("[A-Za-z0-9-]{12}")){
                        if(secondName.matches("[A-Za-z]*")){
                            if(secondSurname.matches("[A-Za-z]*")){
                                //CAMPOS VALIDOS
                                return 0;
                            }else{
                                //CODIGO DE ERROR 6, campo secondSurname invalido
                                return 6;
                            }
                        }else{
                            //CODIGO DE ERROR 5, campo secondName invalido
                            return 5;
                        }
                    }else{
                        //CODIGO DE ERROR 4, campo passportNumber invalido
                        return 4;
                    }
                }else{
                    //CODIGO DE ERROR 3, campo firstSurname invalido
                    return 3;
                }
            }else{
                //CODIGO DE ERROR 2, campo firstname invalido
                return 2;
            }
        }else{
            //CODIGO DE ERROR 1, campos vacios
            return 1;
        }
    }
    
    public int verifyPhoneField(String phone){
        if(phone.matches("") == false){
            if(phone.matches("[0-9-]*")){
                //CAMPO VALIOO
                return 0;
            }else{
                //CODIGO DE ERROR 2, campo invalido
                return 2;
            }
        }else{
            //CODIGO DE ERROR 1, campos vacios
            return 1;
        }
    }
    
    public void setPhone(String number, int personId, int countryId){
        String sql = "INSERT INTO PHONE(number, person_FK, country_FK) VALUES("
                +String.format("'%s', ", number)
                +String.format("'%s', ", personId)
                +String.format("'%s' ", countryId)
                +")"
                ;
                     
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deletePhone(int id){
        String sql = "DELETE FROM PHONE WHERE "
                      +String.format("id = %d", id);
        
        try {
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.executeUpdate();
             
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }     
    }
    
    public ResultSet getPhone(int idPerson, String number){
        String sql =  "SELECT id FROM PHONE "
                      +String.format("WHERE person_FK = %d AND ", idPerson)
                      +String.format("number = '%s' ", number);
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }

    
}
