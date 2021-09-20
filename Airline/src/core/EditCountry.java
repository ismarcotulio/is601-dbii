/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.*;

/**
 *
 * @author erick
 */

/*
    CLASE QUE CONTIENE LOS METODOS DML PARA LA VISTA EDITAR PAIS
*/
public class EditCountry {
        
    private Connection conn;
    
    public EditCountry(Connection conn){
        this.conn = conn;
    }


    public ResultSet getCountrys(){
        String sql = "SELECT id, name, code FROM COUNTRY";    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ResultSet searchCountrys(String key){
        String sql = "SELECT id, name, code FROM COUNTRY "
                     +"WHERE COUNTRY.code LIKE '%"
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
    
    public ResultSet getCountry(int id){
        
        String sql =  "SELECT id, name, code FROM COUNTRY "
                      +String.format("WHERE id = %d", id)
                      ;
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }
    
    
        public void deleteCountry(int id){
        String sql1 = "DELETE FROM PERSON WHERE "
                      +String.format("country_FK = %d", id);
        String sql2 = "DELETE FROM PHONE WHERE "
                      +String.format("country_FK = %d", id);
        String sql3 =  "DELETE FROM COUNTRY WHERE "
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
    
    
    public void updateCountry(int id, String name, String code){
        String sql = "UPDATE COUNTRY SET "
                +String.format("name = '%s', ", name)
                +String.format("code = '%s' ", code)
                +String.format("WHERE id = %d ", id)
                ;
        
        try {
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    
    }
    
    
}



