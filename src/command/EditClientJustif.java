package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class EditClientJustif implements DatabaseCommand {
	
	private int id;
	private String type;
	private String justif;
	
	public EditClientJustif(int id, String type, String justif){
		this.id = id;
		this.type = type;
		this.justif = justif;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		PreparedStatement sta = null;
		
		if (type.equalsIgnoreCase("titular")){
			System.out.println("titular" + id + " " + justif) ;
			sta = conn.prepareStatement("UPDATE CLIENT SET EDIT_JUSTIFICATION = ?  WHERE ID = ?");
		}else{
			System.out.println("benef" + id + " " + justif) ;
			sta = conn.prepareStatement("UPDATE CLIENT_BENEFICIARY SET EDIT_JUSTIFICATION = ?  WHERE ID = ?");
		}	
		sta.setString(1, justif);
		sta.setInt(2, id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
