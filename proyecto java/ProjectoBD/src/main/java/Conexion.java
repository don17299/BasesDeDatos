import java.sql.*;

public class Conexion {

    protected Connection conexion = null;
    protected Statement state = null;
    protected ResultSet result = null;

    private final String url = "jdbc:mysql://localhost/gear";
    private final String usuario = "root";
    private final String password = "root1729";

    public Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // De esta forma cargamos la clase Driver de MySQL.
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión a la Base de Datos Exitosa");
            state=conexion.createStatement();
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println("Error al abrir Conexión: " + ex.getMessage());
        }
    }

    public void hacerConsulta(String consulta){
        try{
            result= state.executeQuery(consulta);
            while(result.next()){
                System.out.println("Cliente:");
                System.out.println(result.getString(1));
                System.out.println(result.getString(2));
                System.out.println(result.getString(3));

            }

        }catch(SQLException ex){
            System.out.println("Error al abrir Conexión: " + ex.getMessage());
        }


    }
}
