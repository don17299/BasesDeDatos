import java.util.Scanner;

public class Aplicacion {

    private static Scanner SCANNER=new Scanner(System.in);

    public static void main(String[] args) {
        String opcion="";
        boolean isIn=true;

        do {
            System.out.println("\nSobre que entidad desea consultar\n");
            System.out.println("1)Clientes\n"
                    + "2)Asesores\n"
                    + "3)Pedidos\n"
                    + "4)Productos\n"
                    + "5)Salir");
            do {
                System.out.println("Ingrese la operacion:");
                opcion = SCANNER.nextLine();
                if (opcion.isEmpty() || !opcion.matches("^\\d*$")) {
                    System.out.println("operacion invalida");
                }
            } while (opcion.isEmpty() || !opcion.matches("^\\d*$"));
            int op = Integer.parseInt(opcion);
            if(0< op && op <6){
                menu(op);
                isIn = op != 5;
            } else {
                System.out.println("opcion invalida, eliga nuevamente");
            }
        }while(isIn);

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
                System.out.println("Saliendo...");
                break;
            default:
        }
    }
}
