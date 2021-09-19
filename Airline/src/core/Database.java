
package core;

import java.sql.*;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


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
    
    public boolean setPerson(JTable phone,String n1,String n2,String n3,String n4,String pass, String pais,String rol){
        
        Calendar ca = Calendar.getInstance();
        
        String clave = n1+pass.charAt(3)+(ca.get(Calendar.YEAR))+(ca.get(Calendar.MONTH)+1)+""+(ca.get(Calendar.DAY_OF_MONTH)+1);

        //para la persona
        String sql = "INSERT INTO PERSON VALUES(?,?,?,?,?,?,?,?,?)";
        String sql1 = "SELECT max(id) AS IDMAX FROM PERSON";
        String sql2 = "SELECT id FROM ROLE WHERE name='"+rol+"'";
        String sql3 = "SELECT id FROM COUNTRY WHERE name='"+pais+"'";
        //para el telefono
        String sql4 = "SELECT max(id) AS IDMAX FROM PHONE";
        String sql5 = "INSERT INTO PHONE VALUES(?,?,?,?)";
        int idperson =-1;
        try {
            //Ingreso de la persona
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
            idperson=re1.getInt("IDMAX")+1;
            pr.setInt(1, idperson);
            clave=clave+idperson;
            pr.setString(2, clave);
            pr.setString(3, pass);
            pr.setString(4, n1);
            pr.setString(5, n2);
            pr.setString(6, n3);
            pr.setString(7, n4);
            pr.setInt(8, re2.getInt("id"));
            pr.setInt(9, re3.getInt("id"));
            pr.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se agrego la persona con exito");
            //Fin ingreso de la persona
            
            //Inicio ingreso de telefonos
            PreparedStatement p4 = this.conn.prepareStatement(sql4);
            ResultSet re4 = p4.executeQuery();
            re4.next();
            int recurso = 1;
            for(int i=0;i<phone.getRowCount();i++){
                PreparedStatement ppr = this.conn.prepareStatement(sql5);
                ppr.setInt(1, re4.getInt("IDMAX")+recurso);
                ppr.setString(2, phone.getValueAt(i, 3).toString()); 
                ppr.setInt(3, idperson);
                ppr.setInt(4, Integer.parseInt(phone.getValueAt(i, 0).toString()));
                ppr.executeUpdate();
                recurso++;
            }
            JOptionPane.showMessageDialog(null, "Se agregaron los teleonos correspondientes");
            DefaultTableModel modelo = (DefaultTableModel)phone.getModel();
            modelo.setRowCount(0);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
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
