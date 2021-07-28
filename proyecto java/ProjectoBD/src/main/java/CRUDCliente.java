import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CRUDCliente {

    private Conexion conn;

    private Statement state;

    private PreparedStatement pState=null;

    private ResultSet result;
    
    public CRUDCliente(){
        this.conn= new Conexion();
        this.state= conn.state;
        this.result= conn.result;
    }

    public void crearCliente(){

    }

    public void modificarCliente(){
        try {
            pState = conn.conexion.prepareStatement("update cliente set nombre=?, correo=? where cedula=?");
            pState.setString(1,"Rasputin");
            pState.setString(2,"raspu@mail.com");
            pState.setString(3,"1100398810");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void eliminarCliente(){

    }

    public void listarClientes(){
        try {
            result = state.executeQuery("select * from cliente");
            while(result.next()){
                System.out.println("Nombre: "+result.getString(1)+"\t||Correo: "+result.getString(2)+"\t||Cedula: "+result.getString(3));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
}
