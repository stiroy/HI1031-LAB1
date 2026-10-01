package kth.lab1.DB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;



//Hanterar Items
public class ItemDAO{

    //Commits current transaction
    public static void commit(Connection connection) throws ItemDBException{
        try{
            connection.commit();
        }
        catch(SQLException e){handleException(connection, "Failed to commit", e);}
    }

    //Rollbacks current transaction
    public static void rollback(Connection connection){
        try{
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Problem when rollback: " + e.getMessage());
        }
    }

    //Retrieves all items in item table, could be used to present item catalog
    public static ArrayList<ItemDTO> retrieveItems(){
        ArrayList<ItemDTO> retrievedItems = new ArrayList<>();
        String closeMessage = "Could not close result set for all item fetch";
        try {
            Connection connection = DBManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet retrieveSet = st.executeQuery("SELECT * FROM T_ITEM ORDER BY T_ITEM.name DESC");
            while (retrieveSet.next()){
                ItemDTO item = new ItemDTO(
                    retrieveSet.getInt("id"),
                    retrieveSet.getString("name"),
                    retrieveSet.getString("description"),
                    retrieveSet.getString("category"),
                    retrieveSet.getInt("quantity"),
                    retrieveSet.getDouble("price")
                );
                retrievedItems.add(item);
            }
            closeResultSet(closeMessage, retrieveSet);            
        } catch(SQLException | ClassNotFoundException | ItemDBException e ){e.printStackTrace();}
        return retrievedItems;
    }

    //Searches Item table for itemName, supports itemsearch by either customer or employee, not case sensitive
    public static ArrayList<ItemDTO> searchItems(String itemName) {
        ArrayList<ItemDTO> searchItemResult = new ArrayList<>();
        String closeMessage = "Could not close result set for search item fetch";

        try{
            Connection connection = DBManager.getConnection();
            String searchItemQuery = "SELECT id, name, description, category, price FROM T_ITEM WHERE name ILIKE ?";
            PreparedStatement ps = connection.prepareStatement(searchItemQuery);
            ps.setString(1,"%"+ itemName + "%");
            ResultSet searchResultSet = ps.executeQuery();

            while (searchResultSet.next()){
                ItemDTO item = new ItemDTO(
                    searchResultSet.getInt("id"),
                    searchResultSet.getString("name"),
                    searchResultSet.getString("description"),
                    searchResultSet.getString("category"),
                    searchResultSet.getDouble("price")
                );
                searchItemResult.add(item);
            }
            closeResultSet(closeMessage, searchResultSet);
        }catch(SQLException | ClassNotFoundException | ItemDBException e ){e.printStackTrace();}
        return searchItemResult;
    }
    //Insert Info about item name, description, category and quantity, price item id is created upon insert by database, for employee use only
    public static void addItem(ItemDTO itemDTO) throws ItemDBException {
        String failureMsg = "Could not insert new item " + itemDTO.getName();
        Connection connection = null;
        try{   
            if(itemDTO.getPrice()<=0 || itemDTO.getQuantity()<0 ){
                
            }
        connection = DBManager.getConnection();
        String createItemStatement = "INSERT INTO T_ITEM(name, description, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(createItemStatement);
            ps.setString(1, itemDTO.getName());
            ps.setString(2, itemDTO.getDescription());
            ps.setString(3, itemDTO.getCategory());
            ps.setInt(4, itemDTO.getQuantity());
            ps.setDouble(5, itemDTO.getPrice());

        int updatedRows = ps.executeUpdate();
        if(updatedRows == 0){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
//Updates information about an items, such as its name, description, category, price
    public static void updateItem(ItemDTO itemDTO)throws ItemDBException{
        String failureMsg = "Could not update item " + itemDTO.getName();
        Connection connection = null;
        try{   
        connection = DBManager.getConnection();
        String createItemStatement = "INSERT INTO T_ITEM(name, description, category, quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(createItemStatement);
            ps.setString(1, itemDTO.getName());
            ps.setString(2, itemDTO.getDescription());
            ps.setString(3, itemDTO.getCategory());
            ps.setInt(4, itemDTO.getQuantity());

        int updatedRows = ps.executeUpdate();
        if(updatedRows != 1){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
//updates the quantity of a given item
    public static void updateQuantity(ItemDTO itemDTO, int quantity)throws ItemDBException{
        String failureMsg = "Could not update "+itemDTO.getName()+" to: " + quantity;
        Connection connection = null;
        try{
        connection = DBManager.getConnection();
        String updateQuantityStatement = "UPDATE T_ITEM SET quantity = ? WHERE name = ?";    
         PreparedStatement ps = connection.prepareStatement(updateQuantityStatement);
        ps.setInt(1, quantity); 
        ps.setString(2, itemDTO.getName());

        int updatedRows = ps.executeUpdate();
            if (updatedRows != 1) {
                handleException(connection ,failureMsg, null);
            }
            commit(connection);
        } catch (SQLException | ClassNotFoundException e) {
            handleException(connection ,failureMsg, e);
        }
    }

    public static void placeOrder(ArrayList<ItemDTO> orderedItems){

    }

    private static void handleException(Connection connection, String failureMsg, Exception cause) throws ItemDBException {
        String completeFailureMsg = failureMsg;
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackExc) {
                completeFailureMsg = completeFailureMsg +
                        ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
            }
        }
        throw new ItemDBException(completeFailureMsg, cause);
    }    

    private static void closeResultSet(String failureMsg, ResultSet result) throws ItemDBException {
        try {
            result.close();
        } catch (Exception e) {
            throw new ItemDBException(failureMsg + " Could not close result set.", e);
        }
    }    
}
