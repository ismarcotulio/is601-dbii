
package core;

import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


/**
 *
 * @author usuario
 */
public class Database {
    
    private Connection conn;

    public Connection getConn() {
        return conn;
    }
    
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
    
        public String[] getCountry(String country){
        String[] guar ={null,null};
        try{
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM COUNTRY WHERE name='"+country+"'");
            ResultSet re = p.executeQuery();
            re.next();
            guar[0] = re.getString("name");
            guar[1] = re.getString("code");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return guar;
    }
    
    public void getCountries(JComboBox comboPais){
        DefaultComboBoxModel modelo=new DefaultComboBoxModel();  
        try
        {
            PreparedStatement p = this.conn.prepareStatement("SELECT name FROM COUNTRY");
            ResultSet re = p.executeQuery();
            while(re.next())
            {
                modelo.addElement(re.getString("name"));
            }
            comboPais.setModel(modelo);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void getRoles(JComboBox comboRol){
        DefaultComboBoxModel modelo=new DefaultComboBoxModel();  
        try
        {
            PreparedStatement p = this.conn.prepareStatement("SELECT name FROM ROLE");
            ResultSet re = p.executeQuery();
            while(re.next())
            {
                modelo.addElement(re.getString("name"));
            }
            comboRol.setModel(modelo);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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
