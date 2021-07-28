import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    protected Connection conectar = null;
    private final String url = "jdbc:mysql://localhost/banco";
    private final String usuario = "root";
    private final String password = "rootroot";

    public void abrirConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // De esta forma cargamos la clase Driver de MySQL.
            conectar = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión Exitosa");
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println("Error al abrir Conexión: " + ex.getMessage());
        }
    }
    public static void main(String[] args) throws SQLException{
        Conexion con = new Conexion();
        con.abrirConexion();
    }
}
