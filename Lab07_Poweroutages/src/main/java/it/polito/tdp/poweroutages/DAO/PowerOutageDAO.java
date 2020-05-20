package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.*;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<Blackout> getBlackout(String nerc){
		
		String sql ="select customers_affected, date_event_began, date_event_finished " +
				"from PowerOutages as p, Nerc as n "+
				"where n.value = ? and n.id=p.nerc_id "+
				"order by date_event_began asc";

		List<Blackout> blackList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nerc);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Blackout n = new Blackout(res.getInt("customers_affected"), 
								res.getTimestamp("date_event_began").toLocalDateTime(), 
								res.getTimestamp("date_event_finished").toLocalDateTime());
				blackList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return blackList;
		
	}

}
