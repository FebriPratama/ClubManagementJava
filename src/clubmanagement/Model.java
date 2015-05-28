/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author serper
 */
public class Model {
    
    static Statement statement;
    private Connection connection;
    static ResultSet rst;
    /**
     *Define some variables
     */
    
    static boolean status;
    static String query;
    static int i;
    
    private static ResultSet executeQuery(String select__from_tb_transaksis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Model(){
        
        if (rst != null) try { rst.close(); } catch (SQLException ignore) {}
        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;user=loginclub;password=admin123;databaseName=db_bola2015";

            connection = DriverManager.getConnection(url);
            
            Model.statement = connection.createStatement();
            
        }catch(SQLException ex){
            
            System.out.println("Gatot"+ex.getMessage());
            
        } catch (ClassNotFoundException ex) {
            
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            
        }

    }
    
    public static Statement getStat(){

        return Model.statement;
        
    }
    
    /**
     *
     * @param username
     * @param password
     * @return status
     */    
    public static boolean userLogin(String username,String password){
		
		query = "SELECT user_name,user_password from tbl_user where user_name='"+username+"' AND user_password='"+password+"'";
		
                status = false;
                
		try{                    
                        rst = statement.executeQuery(query);
		
			i=0;

			while(rst.next()){

				status= true;
				i++;

			}			

		}catch(SQLException ex){

			System.out.println("Connection failed :"+ex);

		}catch(Exception ex){

			System.out.println("Connection failed :"+ex);

		}        
        
        return status;
    }
}
