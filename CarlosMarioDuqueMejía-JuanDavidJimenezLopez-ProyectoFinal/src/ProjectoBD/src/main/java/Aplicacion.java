import exportar.GeneradorPDF;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Aplicacion {
    private static Scanner SCANNER=new Scanner(System.in);

    private static String nombreUsuarioLogged;
    
    public static void main(String[] args) {
        String opcion="";
        boolean isIn=true;

        System.out.println("Inicio de Sesion para supervisores");
        System.out.println("Ingrese su correo");
        String correoL=SCANNER.nextLine();
        System.out.println("\nIngrese su cedula\n");
        String cedulaL=SCANNER.nextLine();
        if(login(cedulaL,correoL)) {

            do {
                System.out.println("\nSobre que entidad desea consultar\n");
                System.out.println("1)Clientes\n"
                        + "2)Asesores\n"
                        + "3)Pedidos\n"
                        + "4)Productos\n"
                        + "5)Reportes\n"
                        + "6)Graficas\n"
                        + "7)Salir");
                do {
                    System.out.println("Ingrese la operacion:");
                    opcion = SCANNER.nextLine();
                    if (opcion.isEmpty() || !opcion.matches("^\\d*$")) {
                        System.out.println("operacion invalida");
                    }
                } while (opcion.isEmpty() || !opcion.matches("^\\d*$"));
                int op = Integer.parseInt(opcion);
                if (0 < op && op < 8) {
                    menu(op);
                    isIn = op != 7;
                } else {
                    System.out.println("opcion invalida, eliga nuevamente");
                }
            } while (isIn);
        }else{
            System.out.println("Error de Inicio de Sesion");
        }

    }

    public static boolean login(String cedula, String correo){
        Conexion conn= new Conexion();
        int encontrado=-1;
        try {
            PreparedStatement pState=conn.conexion.prepareStatement("select nombre from supervisor where cedula=? and correo=?");
            pState.setString(1,cedula);
            pState.setString(2,correo);
            conn.result=pState.executeQuery();

            if(conn.result.next()) {
                System.out.println("Sesion Iniciada, Bienvenido");
                nombreUsuarioLogged=conn.result.getString(1);
                System.out.println("Nombre: " + nombreUsuarioLogged);
                encontrado=0;
            }else{
                System.out.println("No existe ese supervisor");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return encontrado==0;
    }
    
    public static void menu(int op){

        switch(op){
            case 1:
                CRUDCliente crudCliente= new CRUDCliente();
                break;
            case 2:
                CRUDAsesorComercial crudAsesor= new CRUDAsesorComercial();
                break;
            case 3:
                CRUDPedido crudPedido = new CRUDPedido();
                break;
            case 4:
                CRUDProducto crudProducto = new CRUDProducto();
                break;
            case 5:
                Reportes reportes = new Reportes();
                String mensaje ="--------------------------------------------- simples------------------------\n";
                mensaje += reportes.repoClientesResgistrados();
                mensaje += reportes.reportarAsesoresContratados();
                mensaje += reportes.repoProductos();
                mensaje += "------------------------------------------------- Intermedias --------------------\n";
                mensaje += reportes.repoProdSupervisorDiseniador();
                mensaje += reportes.repoCantidadTipoAsesor();
                mensaje += reportes.repoNominaTrabajadores();
                mensaje += reportes.repoComprasClientes();
                mensaje += reportes.repoIntermedia1();
                mensaje += reportes.repoIntermedia3();
                mensaje += "-------------------------------------------------- complejas ----------------------\n";
                mensaje += reportes.repoCompleja1();
                mensaje += reportes.repoCompleja2();
                mensaje += reportes.repoCompleja3();

                mensaje += "\n\n\t\t\tGenerado por el supervisor: "+nombreUsuarioLogged;
                System.out.println(mensaje);
                GeneradorPDF.generadorPDF(mensaje,"pdfs/reportes.pdf");
                break;
            case 6:
                JFrame frame= new ReportesGraficos("Mis reportes");
                frame.setVisible(true);
                break;
            case 7:
                System.out.println("Saliendo...");
                break;
            default:
        }
    }
}
