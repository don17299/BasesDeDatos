import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CRUDAsesorComercial {

    private Scanner SCANNER=new Scanner(System.in);

    private Conexion conn;

    private Statement state;

    private PreparedStatement pState=null;

    private ResultSet result;

    public CRUDAsesorComercial(){
        this.conn= new Conexion();
        this.state= conn.state;
        this.result= conn.result;
        menuAsesorComercial();

    }

    public void menuAsesorComercial(){
        String opcion="";
        boolean isIn=true;

        do {
            System.out.println("\nCrud del Asesor Comercial");
            System.out.println("1)Crear un asesor\n"
                    + "2)Actualizar un asesor\n"
                    + "3)Buscar asesor\n"
                    + "4)eliminar un asesor\n"
                    + "5)listar todos los asesores\n"
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
        String nombre="";
        String correo="";
        String cedula="";
        String tipo="";
        switch (opcion){
            case 1:

                System.out.println("Ingrese la cedula del asesor comercial:");
                cedula = SCANNER.nextLine();
                if (-1 == buscarAsesorComercial(cedula)){
                    System.out.println("Ingrese el nombre del asesor comercial:");
                    nombre = SCANNER.nextLine();
                    System.out.println("Ingrese el correo del asesor comercial:");
                    correo = SCANNER.nextLine();
                    System.out.println("Ingrese el codigo del tipo del asesor comercial:");
                    tipo = SCANNER.nextLine();
                    crearAsesorComercial(nombre,correo,cedula,tipo);
                } else {
                    System.out.println("El  asesor comercial ya existe");
                }
                break;
            case 2:
                System.out.println("Ingrese la cedula del  asesor comercial a modificar:");
                cedula = SCANNER.nextLine();
                if (0 == buscarAsesorComercial(cedula)){
                    System.out.println("Ingrese el nombre del  asesor comercial:");
                    nombre = SCANNER.nextLine();
                    System.out.println("Ingrese el correo del  asesor comercial:");
                    correo = SCANNER.nextLine();
                    System.out.println("Ingrese el codigo del tipo del asesor comercial:");
                    tipo = SCANNER.nextLine();
                    modificarAsesorComercial(nombre,correo,cedula,tipo);
                } else {
                    System.out.println("Asesor comercial inexistente");
                }
                break;
            case 3:
                System.out.println("Ingrese la cedula del asesor comercial a buscar:");
                cedula = SCANNER.nextLine();
                buscarAsesorComercial(cedula);
                break;
            case 4:
                System.out.println("Ingrese la cedula del asesor comercial a eliminar:");
                cedula = SCANNER.nextLine();

                if (0 == buscarAsesorComercial(cedula)){
                    eliminarAsesorComercial(cedula);
                } else {
                    System.out.println("asesor comercial inexistente");
                }
                break;
            case 5:
                listarAsesoresComerciales();
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

    public void crearAsesorComercial(String nombre, String correo, String cedula,String tipo){
        try {
            pState = conn.conexion.prepareStatement("insert into asesor values(?,?,?,?)");

            pState.setString(1,nombre);
            pState.setString(2,correo);
            pState.setString(3,cedula);
            pState.setString(4,tipo);
            pState.executeUpdate();
            System.out.println("Asesor comercial creado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int buscarAsesorComercial(String cedula){
        int encontrado = -1;
        try {
            pState = conn.conexion.prepareStatement("select * from asesor where cedula=?");
            pState.setString(1,cedula);
            result=pState.executeQuery();

            if(result.next()) {
                System.out.println("Asesor Encontrado");
                System.out.println("Nombre: " + result.getString(1)
                        + "\t||Correo: " + result.getString(2)
                        + "\t||Cedula: " + result.getString(3)
                        + "\t||Tipo: " + result.getString(4));

                encontrado=0;
            }else{
                System.out.println("No existe ese asesor");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return encontrado;
    }

    public void modificarAsesorComercial(String nombre, String correo,String cedula, String tipo){
        try {
            pState = conn.conexion.prepareStatement("update asesor set nombre=?, correo=?, tipo_codigo=? where cedula=?");
            pState.setString(1,nombre);
            pState.setString(2,correo);
            pState.setString(3,cedula);
            pState.setString(4,tipo);
            int a = pState.executeUpdate();
            if(a!=0) {
                System.out.println("Asesor Actualizado");
            }else{
                System.out.println("Ocurrio un error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void eliminarAsesorComercial(String cedula){
        try {
            pState = conn.conexion.prepareStatement("delete from asesor where cedula =?");
            pState.setString(1,cedula);
            pState.executeUpdate();
            System.out.println("Asesor eliminado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarAsesoresComerciales(){
        try {
            result = state.executeQuery("select * from asesor");
            System.out.println("Asesores comerciales: ");
            while(result.next()){
                System.out.println("Nombre: "+result.getString(1)
                        +"\t||Correo: "+result.getString(2)
                        +"\t||Cedula: "+result.getString(3)
                        +"\t||Tipo: " + result.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void salir(){///jajajajaj ya vuelvo
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
