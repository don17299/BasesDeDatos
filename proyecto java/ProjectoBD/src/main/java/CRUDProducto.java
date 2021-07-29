import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class CRUDProducto {
    private Scanner SCANNER=new Scanner(System.in);

    private Conexion conn;

    private Statement state;

    private PreparedStatement pState=null;

    private ResultSet result;

    public CRUDProducto(){
        this.conn= new Conexion();
        this.state= conn.state;
        this.result= conn.result;
        menuProducto();

    }

    public void menuProducto(){
        String opcion="";
        boolean isIn=true;

        do {
            System.out.println("\nCrud del Producto");
            System.out.println("1)Crear un Producto\n"
                    + "2)Actualizar un Producto\n"
                    + "3)Buscar Producto\n"
                    + "4)eliminar un Producto\n"
                    + "5)listar todos los Productos\n"
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
                switchProducto(op);
                isIn = op != 6;
            } else {
                System.out.println("opcion invalida, eliga nuevamente");
            }
        }while(isIn);
    }

    public void switchProducto(int opcion){
        String nombre="";
        String descripcion="";
        String codigo="";
        float precio;
        int cantidad;
        String cedSuper="";
        String cedDise="";
        String codInv="";

        switch (opcion){
            case 1:

                System.out.println("Ingrese el codigo del producto:");
                codigo = SCANNER.nextLine();
                if (-1 == buscarProducto(codigo)){
                    System.out.println("Ingrese el nombre del producto:");
                    nombre = SCANNER.nextLine();
                    System.out.println("Ingrese la descripcion del Producto:");
                    descripcion = SCANNER.nextLine();
                    System.out.println("Ingrese el precio del Producto:");
                    precio = Float.parseFloat(SCANNER.nextLine());
                    System.out.println("Ingrese la cantidad del producto en stock:");
                    cantidad = Integer.parseInt(SCANNER.nextLine());
                    System.out.println("Ingrese la cedula del supervisor:");
                    cedSuper =SCANNER.nextLine();
                    System.out.println("Ingrese la cedula del diseñador:");
                    cedDise = SCANNER.nextLine();
                    System.out.println("Ingrese el codigo del inventario:");
                    codInv = SCANNER.nextLine();
                    crearProducto(codigo,nombre,descripcion,precio,cantidad,cedSuper, cedDise,codInv);
                } else {
                    System.out.println("El Producto ya existe");
                }
                break;
            case 2:
                System.out.println("Ingrese el codigo del  Producto a modificar:");
                codigo = SCANNER.nextLine();
                if (0 == buscarProducto(codigo)){
                    System.out.println("Ingrese el nombre del producto:");
                    nombre = SCANNER.nextLine();
                    System.out.println("Ingrese la descripcion del Producto:");
                    descripcion = SCANNER.nextLine();
                    System.out.println("Ingrese el precio del Producto:");
                    precio = Float.parseFloat(SCANNER.nextLine());
                    System.out.println("Ingrese la cantidad del producto en stock:");
                    cantidad = Integer.parseInt(SCANNER.nextLine());
                    System.out.println("Ingrese la cedula del supervisor:");
                    cedSuper =SCANNER.nextLine();
                    System.out.println("Ingrese la cedula del diseñador:");
                    cedDise = SCANNER.nextLine();
                    System.out.println("Ingrese el codigo del inventario:");
                    codInv = SCANNER.nextLine();
                    modificarProducto(codigo,nombre,descripcion,precio,cantidad,cedSuper,cedDise,codInv);
                } else {
                    System.out.println("Producto inexistente");
                }
                break;
            case 3:
                System.out.println("Ingrese el codigo del producto a buscar:");
                codigo = SCANNER.nextLine();
                buscarProducto(codigo);
                break;

            case 4:
                System.out.println("Ingrese el codigo del producto a eliminar:");
                codigo = SCANNER.nextLine();

                if (0 == buscarProducto(codigo)){
                    eliminarProducto(codigo);
                } else {
                    System.out.println("Producto inexistente");
                }
                break;
            case 5:
                listarProductos();
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

    public void crearProducto(String codigo, String nombre, String descripcion, float precio,
                            int cantidad,String cedulaSuper,String cedulaDise, String inventarioCodigo ){
        try {
            pState = conn.conexion.prepareStatement("insert into producto values(?,?,?,?,?,?,?,?)");

            pState.setString(1,nombre);
            pState.setString(2,descripcion);
            pState.setFloat(3,precio);
            pState.setInt(4,cantidad);
            pState.setString(5,codigo);
            pState.setString(6,cedulaSuper);
            pState.setString(7,cedulaDise);
            pState.setString(8,inventarioCodigo);
            pState.executeUpdate();
            System.out.println("Producto creado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int buscarProducto(String codigo){
        int encontrado = -1;
        try {
            pState = conn.conexion.prepareStatement("select * from producto where codigo=?");
            pState.setString(1,codigo);
            result=pState.executeQuery();

            if(result.next()) {
                System.out.println("Producto Encontrado");
                System.out.println("nombre: " + result.getString(1)
                        + "\t||descripcion: " + result.getString(2)
                        + "\t||Precio: " + result.getFloat(3)
                        + "\t||Cantidad: " + result.getInt(4)
                        + "\t||Codigo: " + result.getString(5)
                        + "\t||Cedula supervisor: " + result.getString(6)
                        + "\t||Cedula diseñador: " + result.getString(7)
                        + "\t||Código inventario: " + result.getString(8));

                encontrado=0;
            }else{
                System.out.println("No existe ese Producto");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return encontrado;
    }

    public void modificarProducto(String codigo, String nombre, String descripcion, float precio,
                                  int cantidad,String cedulaSuper,String cedulaDise, String inventarioCodigo){
        try {
            pState = conn.conexion.prepareStatement("update producto set nombre=?, descripcion=?, precio=?,cantidad=?,supervisor_cedula=?,diseñador_cedula=?, inventario_codigo=? where codigo=?");

            pState.setString(1,nombre);
            pState.setString(2,descripcion);
            pState.setFloat(3,precio);
            pState.setInt(4,cantidad);
            pState.setString(5,cedulaSuper);
            pState.setString(6,cedulaDise);
            pState.setString(7,inventarioCodigo);
            pState.setString(8,codigo);
            int a = pState.executeUpdate();
            if(a!=0) {
                System.out.println("Producto Actualizado");
            }else{
                System.out.println("Ocurrio un error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void eliminarProducto(String codigo){
        try {
            pState = conn.conexion.prepareStatement("delete from producto where codigo=?");
            pState.setString(1,codigo);
            pState.executeUpdate();
            System.out.println("Producto eliminado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarProductos(){
        try {
            result = state.executeQuery("select * from producto");
            System.out.println("Productos: ");
            while(result.next()){
                System.out.println("nombre: " + result.getString(1)
                        + "\t||descripcion: " + result.getString(2)
                        + "\t||Precio: " + result.getFloat(3)
                        + "\t||Cantidad: " + result.getInt(4)
                        + "\t||Codigo: " + result.getString(5)
                        + "\t||Cedula supervisor: " + result.getString(6)
                        + "\t||Cedula diseñador: " + result.getString(7)
                        + "\t||Código inventario: " + result.getString(8));
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
