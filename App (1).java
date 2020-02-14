package xmlToBD;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Connection connection = getConection();
        String consulta=null;

        //Creamos documento XML
        Document doc = createDocXML();

        try {
            Statement statement = connection.createStatement();
            //cogemos todas las peliculas
            NodeList films = doc.getElementsByTagName("Film");
            for (int i = 0; i <films.getLength() ; i++) {
                Element element = (Element) films.item(i);
                if (element.getNodeType() == Node.ELEMENT_NODE){
                    //año
                    String año = (element.getAttribute("produced"));
                    //hijos de film
                    String titulo = element.getElementsByTagName("Title").item(0).getTextContent();
                    String director = element.getElementsByTagName("Director").item(0).getTextContent();
                    String country = element.getElementsByTagName("Country").item(0).getTextContent();
                    consulta = "INSERT INTO film (titulo, año, director, pais) VALUES ('" + titulo + "', '" + año + "', '"+director+"', '"+country+"')";
                    statement.executeUpdate(consulta);
                }
            }
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void insertBD(String consulta, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(consulta);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    //crearDocumento xml
    private static Document createDocXML(){
        Document doc = null;
        try{
            //crear documento a partir de uno que ya existe:
            doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("films.xml");
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
}
