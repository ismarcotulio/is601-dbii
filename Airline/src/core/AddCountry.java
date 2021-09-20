package core;

import java.sql.*;


/**
 * @author Marco
 */

/*
    ESTA CLASE SOLAMENTE CONTIENE METODOS DE MANIPULACION DE BD PARA LA VISTA EDITAR PERSONA
*/
public class AddCountry {
    
    private Connection conn;
    
    public AddCountry (Connection conn){
        this.conn = conn;
    }
    
    
    public void insert(String name, String code) {
        String sql = "INSERT INTO COUNTRY(name,code) VALUES( "
                 +String.format(" '%s',", name )
                 +String.format(" '%s'", code )
                 + ")"
                ;
        

        try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
         
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
    

    
}
