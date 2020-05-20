package DBoperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBconnection
{
    public static final DBconnection instance = new DBconnection();
    private Connection connection;
    private Properties properties = new Properties();

    public Connection getConnection()
    {
        return connection;
    }

    private DBconnection()
    {
        try
        {
            properties.load(new FileInputStream("dbconnection.properties"));
        }
        catch (IOException e)
        {
            System.out.println("No config file found!");
            File f = new File("dbconnection.properties");
            try
            {
                FileOutputStream output = new FileOutputStream(f);
                properties.setProperty("url", "jdbc:postgresql://localhost:5432/employers");
                properties.setProperty("user", "postgres");
                properties.setProperty("password", "password123");
                properties.store(output, "Database connection paramaters");
                System.out.println("Default properties file created.");
            }
            catch (IOException er)
            {
                System.out.println("Cannot create default properties file.");
                er.printStackTrace();
                System.exit(1);
            }
        }

        String url = (String) properties.get("url");
        String user = (String) properties.get("user");
        String password = (String) properties.get("password");
        System.out.println("Connection properties loaded from file.");

        try
        {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to database successful.");
        }
        catch (SQLException e)
        {
            System.out.println("Connection to database failed.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
