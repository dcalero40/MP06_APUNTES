package EjerciciosBaseDatos;

import java.sql.*;
import java.util.Scanner;

/**
 * DAVID CALERO
 *
 */
public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Connection connection = null;
        Statement statement = null;
        ResultSet rs=null;
        boolean isConected=false;

        int option=1;
        while(option!=0){
            menu();
            option = in.nextInt();
            in.nextLine();
            switch (option){
                case 0:
                    System.out.println("Saliendo...");
                    try {
                        rs.close();
                        statement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 1:
                    //probar conexion
                    connection = getConection();
                    isConected=true;
                    break;

                case 2:
                    //mostrar nombres y edad
                    if(isConected) {
                        try {
                            statement = connection.createStatement();
                            String consulta = "select Nom, Edat from Alumnes";
                            rs = statement.executeQuery(consulta);
                            while (rs.next()){
                                System.out.println("Nom: "+ rs.getString(1)+" | Edat: "+ rs.getInt(2));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else System.out.println("PRIMERO HAS DE ESTABLECER LA CONEXIÓN");
                    break;

                case 3:
                    //introducir usuario
                    if(isConected) {
                        try {
                            System.out.println("Introduce los siguiente datos: ");
                            System.out.println("Nombre alumno: ");
                            String nombre = in.nextLine();
                            System.out.println("Apellido alumno: ");
                            String apellido = in.nextLine();

                            statement = connection.createStatement();
                            String consulta = "INSERT INTO Alumnes (Nom, Cognoms, Edat) VALUES ('" + nombre + "', '" + apellido + "', 38)";
                            statement.executeUpdate(consulta);
                            //statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Alumno añadido correctamente.");
                    }
                    else System.out.println("PRIMERO HAS DE ESTABLECER LA CONEXIÓN");
                    break;

                case 4:
                    if(isConected) {
                        try {
                            statement = connection.createStatement();
                            String consulta = "SELECT Nom, Edat FROM Alumnes WHERE Edat BETWEEN 25 AND 48";
                            rs = statement.executeQuery(consulta);

                            while (rs.next()){
                                System.out.print("Nom: "+ rs.getString(1));
                                System.out.println("Edat: "+ rs.getInt(2));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else System.out.println("PRIMERO HAS DE ESTABLECER LA CONEXIÓN");
                    break;
            }
        }
    }
    private static void menu() {
        System.out.println("SALIR = 0 | ESTABLECER CONEXION = 1 | LISTAR = 2 | INSERTAR ALUMNO = 2 | LISTAR WHERE EDAT = 4\n Introduce una opcion:");
    }
    //establece tu configuracion
    public static Connection getConection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.22.197:3306/DavidCaleroPuig", "david", "*David98*");
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connection;
    }
}
