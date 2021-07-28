public class Aplicacion {

    public static void main(String[] args) {
       // Conexion conn= new Conexion();
       // conn.hacerConsulta("select * from cliente");
        CRUDCliente crudCliente= new CRUDCliente();
        crudCliente.listarClientes();
        crudCliente.listarClientes();
    }
}
