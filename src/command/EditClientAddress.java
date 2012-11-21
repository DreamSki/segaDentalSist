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
				" STREET = ?, PROPERTY_NAME = ?, TOWER = ?, FLOOR = ?,  APARTMENT = ?, REFERENCE_POINT = ?, POSTAL_CODE = ? WHERE CLIENT_ID = ?");
			System.out.println("aqui complto" + address.getState() + "-" + address.getCity() + "-" + address.getMunicipality() 
					+ "-" + address.getMunicipality() + address.getUrbanization() + address.getStreet()  + address.getPropertyName() +
					address.getTower() +  address.getFloor() + address.getApartment() + address.getReferencePoint() + address.getPostalCode() +" id " +id);
			
			sta.setString(1, address.getState());
			sta.setString(2, address.getCity());
			sta.setString(3, address.getMunicipality());
			sta.setString(4, address.getUrbanization());
			sta.setString(5, address.getStreet());
			sta.setString(6, address.getPropertyName());
			sta.setString(7, address.getTower());
			sta.setInt(8, address.getFloor());
			sta.setString(9, address.getApartment());
			
			if(address.getReferencePoint()==null || address.getReferencePoint().equalsIgnoreCase("")){
				sta.setNull(10, java.sql.Types.VARCHAR);
			} else {
				sta.setString(10, address.getReferencePoint());
			}
			
			if(address.getPostalCode()==null || address.getPostalCode().equalsIgnoreCase("")){
				sta.setNull(11, java.sql.Types.INTEGER);
			} else {
				sta.setString(11, address.getPostalCode());
			}			
			
			sta.setInt(12, id);

		
		}else{
			sta = conn.prepareStatement("UPDATE CLIENT_ADDRESS SET STATE = ?, CITY = ?, MUNICIPALITY = ?, URBANIZATION = ?," +
					" STREET = ?, PROPERTY_NAME = ?, REFERENCE_POINT = ?, POSTAL_CODE = ? WHERE CLIENT_ID = ?");
			
			System.out.println("aqui solo" + address.getCity() + address.getState());
			sta.setString(1, address.getState());
			sta.setString(2, address.getCity());
			sta.setString(3, address.getMunicipality());
			sta.setString(4, address.getUrbanization());
			sta.setString(5, address.getStreet());
			sta.setString(6, address.getPropertyName());
			
			if(address.getReferencePoint()==null){
				sta.setNull(7, java.sql.Types.VARCHAR);
			} else {
				sta.setString(7, address.getReferencePoint());
			}
			
			if(address.getPostalCode()==null){
				sta.setNull(8, java.sql.Types.VARCHAR);
			} else {
				sta.setString(8, address.getPostalCode());
			}	
			
			sta.setInt(9, id);
		}
		
		
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
