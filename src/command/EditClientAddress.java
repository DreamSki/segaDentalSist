package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.ClientAddress;


public class EditClientAddress implements DatabaseCommand {
	
	private int id;
	private ClientAddress address;
	
	public EditClientAddress(int id, ClientAddress address){
		this.id = id;
		this.address = address;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		PreparedStatement sta;
		
		if (address.getPropertyTypeId() == 1 || address.getPropertyTypeId() == 3 || address.getPropertyTypeId() == 5){
			sta = conn.prepareStatement("UPDATE CLIENT_ADDRESS SET STATE = ?, CITY = ?, MUNICIPALITY = ?, URBANIZATION = ?," +
				" STREET = ?, PROPERTY_NAME = ?, TOWER = ?, FLOOR = ?,  APARTMENT = ? WHERE CLIENT_ID = ?");
			System.out.println("aqui complto" + address.getState() + "-" + address.getCity() + "-" + address.getMunicipality() 
					+ "-" + address.getMunicipality() + address.getUrbanization() + address.getStreet()  + address.getPropertyName() +
					address.getTower() +  address.getFloor() + address.getApartment() + " id " +id);
			
			sta.setString(1, address.getState());
			sta.setString(2, address.getCity());
			sta.setString(3, address.getMunicipality());
			sta.setString(4, address.getUrbanization());
			sta.setString(5, address.getStreet());
			sta.setString(6, address.getPropertyName());
			sta.setString(7, address.getTower());
			sta.setInt(8, address.getFloor());
			sta.setString(9, address.getApartment());
			sta.setInt(10, id);

		
		}else{
			sta = conn.prepareStatement("UPDATE CLIENT_ADDRESS SET STATE = ?, CITY = ?, MUNICIPALITY = ?, URBANIZATION = ?," +
					" STREET = ?, PROPERTY_NAME = ? WHERE CLIENT_ID = ?");
			
			System.out.println("aqui solo" + address.getCity() + address.getState());
			sta.setString(1, address.getState());
			sta.setString(2, address.getCity());
			sta.setString(3, address.getMunicipality());
			sta.setString(4, address.getUrbanization());
			sta.setString(5, address.getStreet());
			sta.setString(6, address.getPropertyName());
			sta.setInt(7, id);
		}
		
		
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
