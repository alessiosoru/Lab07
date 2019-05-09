package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class ModelAltro {

	PowerOutageDAO podao;
	List<PowerOutage> best;
	List<PowerOutage> powerOutagesNerc;
	int totPeopleBest=0;
	
	public ModelAltro() {
		podao = new PowerOutageDAO();
		best = new ArrayList<>();
		powerOutagesNerc = new ArrayList<>();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> cercaWorstBlackout(Nerc nerc, Period years, Duration hours) {
		// livello
//		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
//		ListPowerOutages powerOutagesNerc = new ListPowerOutages();
//		powerOutagesNerc.setEventi(getPowerOutagesByNerc(nerc));
		powerOutagesNerc = getPowerOutagesByNerc(nerc);
		best = new ArrayList<>();
//		System.out.println(powerOutagesNerc.toString());
		// livello: posizione di powerOutagesNerc
		cerca(0, new ArrayList<PowerOutage>(), /*powerOutagesNerc,*/ years, hours);
		return best;
	}


	private void cerca(int livello, List<PowerOutage> parziale, /*ListPowerOutages powerOutagesNerc,*/ Period years, Duration hours) {
		int currPeople=0;
//		System.out.println("anni parziale"+parziale.getTotYears()+"\nanni years max"+best.getTotYears());
//		if(getTotYears(parziale)>years.getYears() ||
//				livello == powerOutagesNerc.size())//powerOutagesNerc.getEventi().size())
//			return;
//		
		if(( currPeople = getTotPeople(parziale))>totPeopleBest) {//&&
				//getTotYears(parziale)<=years.getYears())  {
			totPeopleBest = currPeople;
			best = new ArrayList<PowerOutage>(parziale);
		}
		
		for(PowerOutage po : powerOutagesNerc)//powerOutagesNerc.getEventi()) {
//			System.out.println(po.toString()+powerOutagesNerc.getEventi().size());
			if(!parziale.contains(po)) {
//				System.out.println(po.toString()+"PO\n");
				parziale.add(po);
				if(po.getDuration()<=hours.toHours() 
						&& getTotYears(parziale)<years.getYears()) {
					System.out.println("gira");
					cerca(livello+1, parziale,/* powerOutagesNerc, */years, hours);
				}
				parziale.remove(po);
				
			}
	}
		

	public int getTotYears(List<PowerOutage> parziale) {
		// sono ordinati per anno: id minore, anno venuto dopo
//		if(parziale==null)
//			return 0;
//		System.out.println("numero eventi"+this.eventi.size());
		if(parziale.size()<=1)
			return 0;
		else {
			int y1 = parziale.get(0).getData_event_began().toLocalDate().getYear();
			int y2 = parziale.get(parziale.size()-1).getData_event_began().toLocalDate().getYear();
			return y2-y1+1;
//			System.out.println("primo evento"+this.eventiMap.get(0).toString());
//			System.out.println("primo evento"+this.eventi.get(0).toString());
//			System.out.println("primo evento"+this.eventiMap.get(eventi.size()-1).toString());
//			System.out.println("ultimo evento"+this.eventi.get(eventi.size()-1).toString());
//			return Period.between(parziale.get(0).getData_event_began().toLocalDate(), 
//					parziale.get(parziale.size()-1).getData_event_began().toLocalDate()).getYears();
		}
			
	}
	


	public long getTotHours(List<PowerOutage> parziale) {
		long totHours=0;
		for(PowerOutage po : parziale) {
    		totHours+=po.getDuration();
    	}
		return totHours;
	}

	public int getTotPeople(List<PowerOutage> parziale) {
		int totPeople=0;
//		if(parziale==null)
//			return 0;
	 	for(PowerOutage po : parziale) {
    		totPeople = totPeople + po.getCustomers_affected();
    	}
		return totPeople;
	}


	private List<PowerOutage> getPowerOutagesByNerc(Nerc nerc) {
		return podao.getPowerOutageSByNerc(nerc);
	}
}
