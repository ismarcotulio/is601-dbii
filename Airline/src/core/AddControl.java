package core;

import java.sql.*;
import java.text.SimpleDateFormat;


/**
 * @author Marco
 */

/*
    ESTA CLASE SOLAMENTE CONTIENE METODOS DE MANIPULACION DE BD PARA LA VISTA AGREGAR CONTROL
*/
public class AddControl {
    
    private Connection conn;
    
    public AddControl (Connection conn){
        this.conn = conn;
    }
    
    
    public ResultSet getControls(){
        String sql = "SELECT CONTROL_LOG.id AS controlId, ACTIVITY.name AS activityName, PERSON.secretId AS personSecretId FROM CONTROL_LOG "
                    +"JOIN ACTIVITY ON CONTROL_LOG.activity_FK = ACTIVITY.id "
                    +"JOIN PERSON ON CONTROL_LOG.person_FK = PERSON.id";    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
  
    
    public ResultSet getActivities(){
        String sql = "SELECT id, name  FROM ACTIVITY";    
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public boolean verifySecretId(String secretId){
        String sql = "SELECT secretId  FROM PERSON WHERE "
                     +String.format("secretId = '%s'", secretId);   
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql).next();             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public void setControl(String dueDateTime, int personId, int activityId){
        String sql = "INSERT INTO CONTROL_LOG(dueDateTime, person_FK, activity_FK) VALUES( "
                 +String.format(" '%s', ", dueDateTime )
                 +String.format(" %d, ", personId )
                +String.format(" %d ", activityId )
                 + ")"
                ;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet getPersonBySecretId(String secretId){
        String sql = "SELECT id FROM PERSON WHERE "
                +String.format("secretId = '%s'", secretId );
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ResultSet getActivityByName(String name){
        String sql = "SELECT id FROM ACTIVITY WHERE "
                     +String.format("name = '%s'", name );   
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String getCurrentDateTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    
    
    
}
