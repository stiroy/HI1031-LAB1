import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import db.DBManager;

//DEPRICATED
/* 
public class ItemDB extends Item{
    public static Collection searchItems(String group) {
        Vector searchResult = new Vector<>();
        try{
            Connection connection = DBManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select id, name, description, category from T_ITEM");
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                searchResult.addElement(new ItemDB(id, name, description, category));
            }

        }catch(SQLException | ClassNotFoundException e ){e.printStackTrace();}
        return searchResult;
    }

    private ItemDB(int id, String name, String description, String category){
        super(id, name, description, category);
    }
}
*/