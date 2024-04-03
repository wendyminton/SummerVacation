import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummerVacationDAL
{
    private Connection connection;

    // Notice that the databaseName, user and password are passed into this method. We are in the DAL,
    // and we cannot prompt the user for this information. That should be done in the presentation layer
    private void InitializeConnection(String databaseName, String user, String password)
    {
        try
        {
            if(connection == null)
            {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
            }
        } 
        catch (SQLException exception)
        {
            System.out.println("Failed to connect to the database" + exception.getMessage());
        }
    }

    public SummerVacationDAL(String databaseName, String userName, String password)
    {
        InitializeConnection(databaseName, userName, password);
    }

    public List<String> TryGetDestinationForActivity(String activityName)
    {
        List<String> parks = new ArrayList<String>();
        try
        {
            PreparedStatement myStatement = connection.prepareStatement("Select * From Plan Where ActivityName = ?");
            myStatement.setString(1, activityName);
            ResultSet myRelation = myStatement.executeQuery();
            while(myRelation.next())
            {
               parks.add(myRelation.getString("ParkName"));                
            }
            return parks;
        }
        catch(SQLException ex)
        {
            System.out.println("Failed to get activity destinations" + ex.getMessage());
            return parks;
        }
      }
}