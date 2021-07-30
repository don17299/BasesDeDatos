import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CRUDCliente {

    private Scanner SCANNER=new Scanner(System.in);

    private Conexion conn;

    private Statement state;

    private PreparedStatement pState=null;

    private ResultSet result;
    
    public CRUDCliente(){
        this.conn= new Conexion();
        this.state= conn.state;
        this.result= conn.result;
        menuCliente();

    }

    public void menuCliente(){
        String opcion="";
        boolean isIn=true;

        do {
            System.out.println("\nCrud del cliente");
            System.out.println("1)Crear un cliente\n"
                    + "2)Actualizar un cliente\n"
                    + "3)Buscar Cliente\n"
                    + "4)eliminar un cliente\n"
                    + "5)listar todos los clientes\n"
                    + "6)Salir");
            do {
                System.out.println("Ingrese la operacion:");
                opcion = SCANNER.nextLine();
                if (opcion.isEmpty() || !opcion.matches("^\\d*$")) {
                    System.out.println("operacion invalida");
                }
            } while (opcion.isEmpty() || !opcion.matches("^\\d*$"));
            int op = Integer.parseInt(opcion);

            if(0< op && op <7){
                switchCliente(op);
                isIn = op != 6;
            } else {
                System.out.println("opcion invalida, eliga nuevamente");
            }
        }while(isIn);
    }

    public void switchCliente(int opcion){
        String nombre="";
        String correo="";
        String cedula="";
        switch (opcion){
            case 1:

                System.out.println("Ingrese la cedula del cliente:");
                cedula = SCANNER.nextLine();
                if (-1 == buscarCLiente(cedula)){
                    System.out.println("Ingrese el nombre del cliente:");
                    nombre = SCANNER.nextLine();
                    System.out.println("Ingrese el correo del cliente:");
                    correo = SCANNER.nextLine();

                    crearCliente(nombre,correo,cedula);
                } else {
                    System.out.println("El cliente ya existe");
                }
                break;
            case 2:
                System.out.println("Ingrese la cedula del cliente a modificar:");
                cedula = SCANNER.nextLine();
                if (0 == buscarCLiente(cedula)){
                    System.out.println("Ingrese el nombre del cliente:");
                    nombre = SCANNER.nextLine();
                    System.out.println("Ingrese el correo del cliente:");
                    correo = SCANNER.nextLine();
                    modificarCliente(nombre,correo,cedula);
                } else {
                    System.out.println("Cliente inexistente");
                }
                break;
            case 3:
                System.out.println("Ingrese la cedula del cliente a buscar:");
                cedula = SCANNER.nextLine();
                buscarCLiente(cedula);
                break;
            case 4:
                System.out.println("Ingrese la cedula del cliente a eliminar:");
                cedula = SCANNER.nextLine();

                if (0 == buscarCLiente(cedula)){
                    eliminarCliente(cedula);
                } else {
                    System.out.println("Cliente inexistente");
                }
                break;
            case 5:
                listarClientes();
                break;
            case 6:
                System.out.println("////////////////////////////////////////////////////");
                System.out.println("///// SALIENDO ////// SALIENDO ////// SALIENDO /////");
                System.out.println("////////////////////////////////////////////////////");
                salir();
                break;
            default:
                System.out.println("opcion invalida, eliga nuevamente");
        }

    }

    public void crearCliente(String nombre, String correo, String cedula){
        try {
            pState = conn.conexion.prepareStatement("insert into cliente values(?,?,?)");

            pState.setString(1,nombre);
            pState.setString(2,correo);
            pState.setString(3,cedula);
            pState.executeUpdate();
            System.out.println("Cliente Creado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int buscarCLiente(String cedula){
        int encontrado = -1;
        try {
            pState = conn.conexion.prepareStatement("select * from cliente where cedula=?");
            pState.setString(1,cedula);
            result=pState.executeQuery();

            if(result.next()) {
                System.out.println("Cliente Encontrado");
                System.out.println("Nombre: " + result.getString(1) + "\t||Correo: " + result.getString(2) + "\t||Cedula: " + result.getString(3));
                encontrado=0;
            }else{
                System.out.println("No existe ese cliente");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return encontrado;
    }

    public void modificarCliente(String nombre, String correo,String cedula){
        try {
            pState = conn.conexion.prepareStatement("update cliente set nombre=?, correo=? where cedula=?");
            pState.setString(1,nombre);
            pState.setString(2,correo);
            pState.setString(3,cedula);
            int a = pState.executeUpdate();
            if(a!=0) {
                System.out.println("Cliente Actualizado");
            }else{
                System.out.println("Ocurrio un error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void eliminarCliente(String cedula){
        try {
            pState = conn.conexion.prepareStatement("delete from cliente where cedula =?");
            pState.setString(1,cedula);
            pState.executeUpdate();
            System.out.println("Cliente Eliminado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarClientes(){
        try {
            result = state.executeQuery("select * from cliente");
            System.out.println("Clientes: ");
            while(result.next()){
                System.out.println("Nombre: "+result.getString(1)+"\t||Correo: "+result.getString(2)+"\t||Cedula: "+result.getString(3));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void salir(){
        try {
            if (conn.conexion != null) {
                conn.conexion.close();
                System.out.println("Conexion cerrada");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
