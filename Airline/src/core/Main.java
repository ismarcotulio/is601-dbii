
package core;

import InterfazGrafica.escritorio;

/**
 *
 * @author usuario
 */
public class Main {
    
    public static void main( String[] args){
        
        Database database = new Database();
        escritorio desk = new escritorio();
        desk.setVisible(true);
        //database.insertArtist("Artista anonimo de id "+ database.getArtistsAutoIncrementId());
        //database.selectAllArtists();
        
    }
    
}
