package it.polito.tdp.poweroutages.model;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPowerOutages {

	private List<PowerOutage> eventi;
	private Map<Integer, PowerOutage> eventiMap;
	
	public ListPowerOutages() {
		super();
		this.eventi = new ArrayList<PowerOutage>();
		this.eventiMap = new HashMap<Integer, PowerOutage>();
	}

	public List<PowerOutage> getEventi() {
		return eventi;
	}

	public void setEventi(List<PowerOutage> eventi) {
		this.eventi = eventi;
	}

	public int getTotYears() {
		// sono ordinati per anno: id minore, anno venuto dopo
		if(this.eventi==null)
			return 0;
//		System.out.println("numero eventi"+this.eventi.size());
		if(this.eventi.size()<=1)
			return 0;
		else {
//			System.out.println("primo evento"+this.eventiMap.get(0).toString());
//			System.out.println("primo evento"+this.eventi.get(0).toString());
//			System.out.println("primo evento"+this.eventiMap.get(eventi.size()-1).toString());
//			System.out.println("ultimo evento"+this.eventi.get(eventi.size()-1).toString());
			return Period.between(this.eventi.get(0).getData_event_began().toLocalDate(), 
					this.eventi.get(eventi.size()-1).getData_event_began().toLocalDate()).getYears();
		}
			
	}
	


	public long getTotHours() {
		long totHours=0;
		for(PowerOutage po : this.eventi) {
    		totHours+=po.getDuration();
    	}
		return totHours;
	}

	public int getTotPeople() {
		int totPeople=0;
		if(this.eventi==null)
			return 0;
	 	for(PowerOutage po : this.eventi) {
    		totPeople = totPeople + po.getCustomers_affected();
    	}
		return totPeople;
	}

//	@Override
//	protected ListPowerOutages  clone() {
//		ListPowerOutages clone = new ListPowerOutages();
//		clone.setEventi(this.eventi);
//		return clone;
//	}

	// costruttore clonante
	protected ListPowerOutages(ListPowerOutages altro) {
		this.eventi = new ArrayList<>(altro.eventi);
		this.eventiMap = new HashMap<>(altro.eventiMap);
	}
	public void add(PowerOutage po) {
//		System.out.println(po.toString()+"PO\n");
		this.eventi.add(po);
		this.eventiMap.put(po.getId(), po);
//		System.out.println(this.eventiMap.get(po.getId()).toString()+"map\n");
	}

	public void remove(PowerOutage po) {
		this.eventi.remove(po);
		this.eventiMap.remove(po);
		
	}

	@Override
	public String toString() {
		String string= "";
		for(PowerOutage p : eventi) {
			string=string+p.toString()+"\n";
		}
		return string;
	}
	
	
	
}
