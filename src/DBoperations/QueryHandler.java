package DBoperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryHandler
{
    public static ArrayList<ArrayList<String>> select(String query)
    {
        //System.out.println("executing query: " + query);
        ArrayList<ArrayList<String>> returned = new ArrayList<>();

        try
        {
            Connection connection = DBconnection.instance.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            ResultSetMetaData metadata = resultSet.getMetaData();
            int columnCount = metadata.getColumnCount();

            while (resultSet.next())
            {
                ArrayList<String> row = new ArrayList<>(columnCount);
                int i = 1;
                while (i <= columnCount)
                {
                    row.add(resultSet.getString(i++));
                }
                returned.add(row);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Incorrect query!");
            e.printStackTrace();
        }
        return returned;
    }

    public static void execute(String query)
    {
        //System.out.println("executing query: " + query);
        try
        {
            Connection connection = DBconnection.instance.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println("Incorrect query!");
            e.printStackTrace();
        }
    }
}
