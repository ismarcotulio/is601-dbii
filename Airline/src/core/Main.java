
package core;

/**
 *
 * @author usuario
 */
public class Main {
    
    public static void main( String[] args){
        
        Database database = new Database();
      
        database.insertArtist("Artista anonimo de id "+ database.getArtistsAutoIncrementId());
        database.selectAllArtists();
        
    }
    
}
