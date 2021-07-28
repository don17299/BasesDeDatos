import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class CRUDPedido {
    private Scanner SCANNER=new Scanner(System.in);

    private Conexion conn;

    private Statement state;

    private PreparedStatement pState=null;

    private ResultSet result;

    public CRUDPedido(){
        this.conn= new Conexion();
        this.state= conn.state;
        this.result= conn.result;
        menuPedido();

    }

    public void menuPedido(){
        String opcion="";
        boolean isIn=true;

        do {
            System.out.println("\nCrud del Pedido");
            System.out.println("1)Crear un Pedido\n"
                    + "2)Actualizar un Pedido\n"
                    + "3)Buscar Pedido\n"
                    + "4)eliminar un Pedido\n"
                    + "5)listar todos los Pedidos\n"
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
                switchAsesorComercial(op);
                isIn = op != 6;
            } else {
                System.out.println("opcion invalida, eliga nuevamente");
            }
        }while(isIn);
    }

    public void switchAsesorComercial(int opcion){
        String fechaD="";
        String fechaE="";
        String codigo="";
        String cedAsesor;
        String cedCliente;
        String envioCodigo="";

        switch (opcion){
            case 1:

                System.out.println("Ingrese el codigo del pedido:");
                codigo = SCANNER.nextLine();
                if (-1 == buscarPedido(codigo)){
                    fechaD = LocalDate.now().toString();//Date(new P);
                    System.out.println("Ingrese la fecha de entega:");
                    fechaE = SCANNER.nextLine();
                    System.out.println("Ingrese la cedula del Cliente:");
                    cedCliente = SCANNER.nextLine();
                    System.out.println("Ingrese la cedula del Asesor:");
                    cedAsesor = SCANNER.nextLine();
                    System.out.println("Ingrese el codigo del Envio:");
                    envioCodigo = SCANNER.nextLine();
                    crearPedido(codigo,fechaD,fechaE,cedCliente,cedAsesor,envioCodigo);
                } else {
                    System.out.println("El Pedido ya existe");
                }
                break;
            case 2:
                System.out.println("Ingrese el codigo del  Pedido a modificar:");
                codigo = SCANNER.nextLine();
                if (0 == buscarPedido(codigo)){
                    System.out.println("Ingrese la fecha de entega:");
                    fechaE = SCANNER.nextLine();
                    System.out.println("Ingrese la cedula del Cliente:");
                    cedCliente = SCANNER.nextLine();
                    System.out.println("Ingrese la cedula del Asesor:");
                    cedAsesor = SCANNER.nextLine();
                    System.out.println("Ingrese el codigo del Envio:");
                    envioCodigo = SCANNER.nextLine();
                    modificarPedido(codigo,fechaE,cedCliente,cedAsesor,envioCodigo);
                } else {
                    System.out.println("Asesor comercial inexistente");
                }
                break;
            case 3:
                System.out.println("Ingrese el codigo del Pedido a buscar:");
                codigo = SCANNER.nextLine();
                buscarPedido(codigo);
                break;
            case 4:
                System.out.println("Ingrese el codigo del Pedido a eliminar:");
                codigo = SCANNER.nextLine();

                if (0 == buscarPedido(codigo)){
                    eliminarPedido(codigo);
                } else {
                    System.out.println("Pedido inexistente");
                }
                break;
            case 5:
                listarPedidos();
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

    public void crearPedido(String codigo, String fechaD, String fechaE,String cedCliente, String cedAsesor,String envioCodigo){
        try {
            pState = conn.conexion.prepareStatement("insert into pedido values(?,?,?,?,?,?)");

            pState.setString(1,codigo);
            pState.setString(2,fechaD);
            pState.setString(3,fechaE);
            pState.setString(4,cedCliente);
            pState.setString(5,cedAsesor);
            pState.setString(6,envioCodigo);
            pState.executeUpdate();
            System.out.println("Pedido creado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int buscarPedido(String codigo){
        int encontrado = -1;
        try {
            pState = conn.conexion.prepareStatement("select * from pedido where codigo=?");
            pState.setString(1,codigo);
            result=pState.executeQuery();

            if(result.next()) {
                System.out.println("Pedido Encontrado");
                System.out.println("Codigo: " + result.getString(1)
                        + "\t||Fecha Diligenciamiento: " + result.getString(2)
                        + "\t||Fecha Entrega: " + result.getString(3)
                        + "\t||Cedula Cliente: " + result.getString(4)
                        + "\t||Cedula Asesor: " + result.getString(5)
                        + "\t||Codigo Envio: " + result.getString(6));

                encontrado=0;
            }else{
                System.out.println("No existe ese Pedido");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return encontrado;
    }

    public void modificarPedido(String codigo,String fechaE,String cedCliente, String cedAsesor,String envioCodigo){
        try {
            pState = conn.conexion.prepareStatement("update pedido set fecha_entrega=?, cliente_cedula=?, asesor_cedula=?, envio_codigo=? where codigo=?");
            pState.setString(1,fechaE);
            pState.setString(2,cedCliente);
            pState.setString(3,cedAsesor);
            pState.setString(4,envioCodigo);
            pState.setString(5,codigo);
            int a = pState.executeUpdate();
            if(a!=0) {
                System.out.println("Pedido Actualizado");
            }else{
                System.out.println("Ocurrio un error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void eliminarPedido(String codigo){
        try {
            pState = conn.conexion.prepareStatement("delete from pedido where codigo =?");
            pState.setString(1,codigo);
            pState.executeUpdate();
            System.out.println("Pedido eliminado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarPedidos(){
        try {
            result = state.executeQuery("select * from pedido");
            System.out.println("Pedidos: ");
            while(result.next()){
                System.out.println("Codigo: " + result.getString(1)
                        + "\t||Fecha Diligenciamiento: " + result.getString(2)
                        + "\t||Fecha Entrega: " + result.getString(3)
                        + "\t||Cedula Cliente: " + result.getString(4)
                        + "\t||Cedula Asesor: " + result.getString(5)
                        + "\t||Codigo Envio: " + result.getString(6));
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
