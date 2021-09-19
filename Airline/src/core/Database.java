
package core;

import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;


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
        String[] guar ={null,null,null};
        try{
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM COUNTRY WHERE name='"+country+"'");
            ResultSet re = p.executeQuery();
            re.next();
            guar[0] = re.getString("name");
            guar[1] = re.getString("code");
            guar[2] = re.getString("id");
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
    
    public void setPerson(JTable phone,String n1,String n2,String n3,String n4,String pass, String pais,String rol){
        String sql = "INSERT INTO PERSON VALUES(?,?,?,?,?,?,?,?,?)";
        String sql1 = "SELECT max(id) AS IDMAX FROM PERSON";
        String sql2 = "SELECT id FROM ROLE WHERE name='"+rol+"'";
        String sql3 = "SELECT id FROM COUNTRY WHERE name='"+pais+"'";

        try {
            PreparedStatement p = this.conn.prepareStatement(sql1);
            ResultSet re1 = p.executeQuery();
            re1.next();
            PreparedStatement p2 = this.conn.prepareStatement(sql2);
            ResultSet re2 = p2.executeQuery();
            re2.next();
            PreparedStatement p3 = this.conn.prepareStatement(sql3);
            ResultSet re3 = p3.executeQuery();
            re3.next();
            PreparedStatement pr = this.conn.prepareStatement(sql);
            pr.setInt(1, re1.getInt("IDMAX")+1);
            pr.setString(2, "jsnaojwd82d");
            pr.setString(3, pass);
            pr.setString(4, n1);
            pr.setString(5, n2);
            pr.setString(6, n3);
            pr.setString(7, n4);
            pr.setInt(8, re2.getInt("id"));
            pr.setInt(9, re3.getInt("id"));
            pr.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Se agrego la persona con exito");
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
