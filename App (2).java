package nba;

import java.sql.*;
import java.util.Scanner;

/**
 * DAVID CALERO
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Scanner in = new Scanner(System.in);
        Connection connection = null;
        String consulta=null;
        ResultSet rs=null;
        PreparedStatement statement = null;
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
                        if(rs!=null) {
                            rs.close();
                        }
                        if (statement!=null) {
                            statement.close();
                        }
                        if (connection!=null){
                            connection.close();
                        }
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
                            statement = connection.prepareStatement("SELECT Procedencia, Posicion FROM jugadores WHERE Nombre_equipo= ?");
                            statement.setString(1,"Cavaliers");
                            rs = statement.executeQuery();
                            while (rs.next()){
                                System.out.println("Procedencia: "+ rs.getString(1)+" | Posicion: "+ rs.getString(2));
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
                            statement = connection.prepareStatement("SELECT count(Nombre) FROM jugadores WHERE Procedencia= ?");
                            statement.setString(1,"Spain");
                            rs = statement.executeQuery();
                            while (rs.next()){
                                System.out.println("Numero de jugadores españoles: "+ rs.getInt(1));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                    else System.out.println("PRIMERO HAS DE ESTABLECER LA CONEXIÓN");
                    break;

                case 4:
                    //introducir usuario
                    if(isConected) {
                        try {
                            System.out.println("Introduce un codigo identificador para el nuevo jugador: ");
                            String codigo = in.nextLine();
                            statement=connection.prepareStatement("SELECT Nombre FROM jugadores WHERE codigo=?");
                            statement.setString(1,codigo);
                            rs = statement.executeQuery();
                            while (rs.next()){

                                System.out.println("ERROR, ese codigo pertenece a otro jugador.");

                                System.out.println("Introduce otro código: ");
                                codigo = in.nextLine();
                                statement.setString(1,codigo);
                                rs=statement.executeQuery();
                            }
                            System.out.println("Introduce el nombre del nuevo jugador: ");
                            String nombre = in.nextLine();
                            System.out.println("Introduce la procedencia del nuevo jugador: ");
                            String procedencia = in.nextLine();
                            System.out.println("Introduce la altura del nuevo jugador: ");
                            String altura = in.nextLine();
                            System.out.println("Introduce el peso del nuevo jugador: ");
                            int peso = in.nextInt();
                            in.nextLine();
                            System.out.println("Introduce la posicion del nuevo jugador: ");
                            String posicion = in.nextLine();
                            System.out.println("Introduce del equipo del nuevo jugador: ");
                            String nombre_equipo = in.nextLine();
                            statement = connection.prepareStatement("INSERT INTO jugadores (codigo, nombre, procedencia, altura, peso, Posicion, Nombre_equipo) VALUES (?, ?, ?, ?, ?, ?, ?);");
                            statement.setString(1,codigo);
                            statement.setString(2,nombre);
                            statement.setString(3,procedencia);
                            statement.setString(4,altura);
                            statement.setInt(5,peso);
                            statement.setString(6,procedencia);
                            statement.setString(7,nombre_equipo);
                            statement.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                    else System.out.println("PRIMERO HAS DE ESTABLECER LA CONEXIÓN");
                    break;

                case 5:
                    //introducir usuario
                    if(isConected) {
                        try {
                            statement = connection.prepareStatement("SELECT temporada, Nombre, Puntos_por_partido FROM jugadores \n" +
                                    "LEFT JOIN estadisticas ON jugadores.codigo=estadisticas.jugador\n" +
                                    "WHERE temporada= ? AND Puntos_por_partido>?;\n");
                            statement.setString(1,"04/05");
                            statement.setInt(2,10);
                            rs = statement.executeQuery();
                            while (rs.next()){
                                System.out.println(rs.getString(1) +" | "+ rs.getString(2)+" | "+rs.getInt(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else System.out.println("PRIMERO HAS DE ESTABLECER LA CONEXIÓN");
                    break;

                case 6:
                    //introducir usuario
                    if(isConected) {
                        try {
                            statement = connection.prepareStatement("SELECT equipo_local, equipo_visitante, puntos_local, puntos_visitante FROM partidos WHERE temporada='05/06'");
                            rs = statement.executeQuery();
                            int count=0;
                            while (rs.next()) {
                                if ((rs.getString(1).equals("Warriors") && (rs.getInt(3) - rs.getInt(4) > 15)) || rs.getString(2).equals("Warriors") && (rs.getInt(4) - rs.getInt(3) > 15))count++;
                            }
                            System.out.println(count);
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
        System.out.println("0 SALIR\n" +
                "1. Establecer conexion con la base de datos.\n" +
                "2. Mostrar procedencia y posición de los jugadores de los Cavaliers.\n" +
                "3. Mostrar número de jugadores españoles.\n" +
                "4. Añadir un JUGADOR.\n" +
                "5. Mostrar jugadores que en la temporada 04/05 tenían una media de puntos por partido superior a 10.\n" +
                "6. Mostrar número de partidos que han ganado los Warriors de más de 15 puntos en la temporada 05/06.\n");
    }

    //establece tu configuracion
    public static Connection getConection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.22.197:3306/NBA", "david", "*David98*");
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connection;
    }

}

//                        //equipo_local
//                        statement = connection.prepareStatement("SELECT puntos_local, puntos_visitante\n" +
//                                "FROM partidos\n" +
//                                "WHERE (equipo_local=\"Warriors\" AND temporada=\"05/06\")");
//                            rs = statement.executeQuery();
//                            int count=0;
//                            while (rs.next()){
//                                if ((rs.getInt(1)-rs.getInt(2))>15){
//                                    count++;
//                                }
//                            }
//
//                        //equipo_visitante
//                            statement = connection.prepareStatement("SELECT puntos_local, puntos_visitante\n" +
//                                    "FROM partidos\n" +
//                                    "WHERE (equipo_visitante=\"Warriors\" AND temporada=\"05/06\")");
//                            rs = statement.executeQuery();
//                            while (rs.next()){
//                                if ((rs.getInt(2)-rs.getInt(1))>15){
//                                    count++;
//                                }
//                            }
//                            System.out.println(count);