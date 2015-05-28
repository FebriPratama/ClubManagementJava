/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubmanagement;

import static clubmanagement.Model.statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author serper
 */
public class Controller {
    
    Model Model;
    static int i;
    public Controller(){
        //get the connection
        Model = new Model();
        
    }
    
    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean getLogin(String username,String password){
        
        return Model.userLogin(username,password);
        
    }
    
    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public String[][] getPlayer() throws SQLException{
        
        ResultSet rst = Model.getStat().executeQuery("select pl.player_name,t.team_name "
                + "from tbl_player as pl inner join tbl_team as t on t.team_id = pl.player_team_id");
        i = 0;
        while (rst.next()) {
            i++;
       }
        String[][] players = new String[i][];
        i=0;
        
        while (rst.next()) {
            
            players[i] =new String[]{rst.getString("player_name"),rst.getString("player_team_id")};
            
            i++;
        }
       
        return players;
        
    }
    
    /**
     *
     * @param txtUser
     * @param txtAddress
     * @param id
     * @return
     */
    public boolean updatePlayer(String txtUser,String txtAddress, Object id){
        
        try{
            
            Model.getStat().executeUpdate("update tbl_player set player_name='"+txtUser+"',player_address='"+txtAddress+"' where player_id="+id);

            return true; 
            
        }catch(SQLException ex){
        
            return false;
            
        }

        
    }
    
    public boolean createPlayer(String txtUser,String txtAddress,String txtNo,String txtAge){
        try{
            
            Model.getStat().executeUpdate("insert into tbl_player(player_name,player_address,player_no,player_age) values(player_name='"+txtUser+"',player_address='"+txtAddress+"',player_no='"+txtNo+"',player_age='"+txtAge+"')");

            return true; 
            
        }catch(SQLException ex){
        
            return false;
            
        }
    }
    
}
