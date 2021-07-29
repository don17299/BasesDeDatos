import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reportes {
    Conexion conexion;
    private Statement state;
    private PreparedStatement pState=null;
    private ResultSet result;


    public Reportes(){
        conexion = new Conexion();
        this.state= conexion.state;
        this.result= conexion.result;
    }

    //simples
    public String repoClientesResgistrados(){
        //mostrar los correos de los clientes registrados
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select nombre, correo from cliente");
            cadenaConsulta +="Informacion de contacto de los clientes registrados\n\n";
            while(result.next()){
                cadenaConsulta += "Nombre: "+result.getString(1) +"\nCorreo de contacto: "+result.getString(2)
                        +"\n______________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;
    }

    public String reportarAsesoresContratados(){
        //mostrar los asesores con su sueldo
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select  cedula, nombre, sueldo from asesor");
            cadenaConsulta +="Informacion de asesores contratados\n\n";
            while(result.next()){
                cadenaConsulta += "\nCedula: "+result.getString(1)
                        +"\n\tNombre: "+result.getString(2)
                        +"\n\tSueldo: "+result.getFloat(3)
                        +"\n///////////////////////////////////////////////////////\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;
    }

    public String repoProductos(){
        //Mostrar todos los productos registrados en stock
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select * from producto");
            cadenaConsulta +="Informacion de productos para venta\n\n";
            while(result.next()){
                cadenaConsulta += "Nombre: "+result.getString(1)
                        +"\nCodigo: "+result.getString(5)
                        +"\n\tCantidad: "+result.getString(4)
                        +"\n\tCedula del supervisor: "+result.getString(6)
                        +"\n\tCcedula del diseñador: "+result.getString(7)
                        +"\n\tCodigo del inventario: "+result.getString(8)
                        +"\n\t\tPrecio: "+result.getString(3)
                        +"\n\t\tDescripcion: "+result.getString(2)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;

    }

    //Intermedia-------------Intermedia---------------Intermedia---------------Intermedia-----------Intermedia----------


    public String repoIntermedia1(){
        //seleccionar los 5 empleados que ganen mas, de los empleados que sean asesores de tipo Movil,
        // y ordenarlos de forma ascendente
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select h.nombre, h.sueldo from (select a.nombre, sueldo from asesor a join tipo_asesor t on a.tipo_codigo=t.codigo " +
                    "where t.nombre='Movil' order by sueldo desc) h order by sueldo limit 5");//

            cadenaConsulta +="Los asesores que tienen el mayor sueldo :\n\n";
            while(result.next()){
                cadenaConsulta += "Nombre: "+result.getString(1)
                        +"Sueldo: "+result.getFloat(2)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;
    }


    public String repoComprasClientes(){
        //Seleccionar todos los clientes que tengan compras inferiores
        // a “1.500.000” junto con los clientes que tengan compras mayores a “2.000.000”
        /**
         * πnombreCliente,clienteCedula σtotal>900000 (Cliente⨝Factura)∪πnombreCliente,clienteCedula σtotal<100000 (Cliente⨝Factura)
         */
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select c.nombre, c.cedula from cliente c join factura f on c.cedula=f.cliente_cedula where f.total>2000000 " +
                    "union select cd.nombre, cd.cedula from cliente cd join factura fd on cd.cedula=fd.cliente_cedula where fd.total<1500000");
            cadenaConsulta +="Clientes con compras inferiores a 1500000 y mayores a 2000000:\n\n";
            while(result.next()){
                cadenaConsulta += "Nombre cliente: "+result.getString(1)
                        +"\nCedula cliente: "+result.getString(2)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;

    }

    public String repoCantidadTipoAsesor(){
        //contar la cantidad de asesores que hay por cada tipo
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select t.nombre, count(cedula) " +
                    "from asesor ase right join tipo_asesor t on ase.tipo_codigo = t.codigo " +
                    "group by t.nombre");
            cadenaConsulta +="Cantidad de Asesores por tipo de Asesor:\n\n";
            while(result.next()){
                cadenaConsulta += "Tipo Asesor: "+result.getString(1)
                        +"\nNumero de Asesores: "+result.getInt(2)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;
    }

    public String repoNominaTrabajadores(){
        //Nomina de todos los trabajadores
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select sum(h.sueldo) from (select a.sueldo from asesor a union all select s.sueldo from supervisor s union all select d.sueldo from diseñador d union all select se.sueldo from secretaria se union all select o.sueldo from operario o union all select te.sueldo from trabajador_env te union all select ti.sueldo from trabajador_inv ti) h");

            cadenaConsulta +="Nomina de la empresa Gear:\n\n";
            while(result.next()){
                cadenaConsulta += "Nomina: "+result.getInt(1)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;

    }

    //Complejas------------Complejas------------Complejas------------Complejas------------Complejas------------Complejas

    public String repoProdSupervisorDiseniador(){
        //Consulta 3: Seleccionar por nombre  todos los productos
        // supervisados por “Fred” y que solo esten diseñados por “Olga”

        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select * from (select p.codigo,p.nombre from producto p join supervisor s on p.supervisor_cedula = s.cedula where s.nombre ='Fred') l " +
                    "join (select p.codigo,p.nombre from producto p join diseñador d on p.diseñador_cedula = d.cedula where d.nombre ='Olga') m on m.codigo = l.codigo ");
            cadenaConsulta +="Productos Supervisados por Fred y diseñados por Olga:\n\n";
            while(result.next()){
                cadenaConsulta += "Código del producto: "+result.getString(1)
                        +"\nNombre de producto: "+result.getString(2)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;

    }

    public String repoCompleja2(){
        //listar los clientes que hayan comprado algo más caro que un cliente especifico
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select c.nombre, c.cedula from cliente c join factura f on c.cedula=f.cliente_cedula where f.total>" +
                    "(select fd.total from cliente cd join factura fd on cd.cedula=fd.cliente_cedula where cd.nombre like '%Jorge%') ");
            cadenaConsulta +="\nClientes con compras mayores a Jorge:\n";
            while(result.next()){
                cadenaConsulta += "Nomina: "+result.getString(1)
                        +"\tCedula: "+result.getString(2)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;
    }

    public String repoCompleja3(){
        //contar la cantidad de asesores que hay por cada tipo
        String cadenaConsulta = "";

        try{
            result = state.executeQuery("select ");
            cadenaConsulta +="Nomina de la empresa Gear:\n\n";
            while(result.next()){
                cadenaConsulta += "Nomina: "+result.getInt(1)
                        +"\n_____________________________________________\n\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cadenaConsulta;
    }

}
