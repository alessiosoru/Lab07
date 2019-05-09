package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	Map<Integer, Nerc> nercMap = new HashMap<Integer, Nerc>();

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
				nercMap.put(n.getId(), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<PowerOutage> getPowerOutageSByNerc(Nerc nerc) {
		String sql = "SELECT id, nerc_id, date_event_began, date_event_finished, customers_affected " + 
				"FROM poweroutages " + 
				"WHERE nerc_id = ? ";
		List<PowerOutage> poList =  new ArrayList<>();
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, nerc.getId());
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				LocalDateTime inizio = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime fine = res.getTimestamp("date_event_finished").toLocalDateTime();
				long duration = Duration.between(inizio, fine).toHours();
				poList.add(new PowerOutage(res.getInt("id"), nercMap.get(res.getInt("nerc_id")),
						res.getInt("customers_affected"), inizio,
						fine, duration));
//				System.out.println(duration+" nel dao");
			}
			
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return poList;
	}

}
