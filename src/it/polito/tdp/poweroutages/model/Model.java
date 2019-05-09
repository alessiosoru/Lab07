package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	ListPowerOutages best;
	int totPeopleBest=0;
	
	public Model() {
		podao = new PowerOutageDAO();
		best = new ListPowerOutages();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public ListPowerOutages cercaWorstBlackout(Nerc nerc, Period years, Duration hours) {
		// livello
		ListPowerOutages parziale = new ListPowerOutages();
		ListPowerOutages powerOutagesNerc = new ListPowerOutages();
		powerOutagesNerc.setEventi(getPowerOutagesByNerc(nerc));
//		System.out.println(powerOutagesNerc.toString());
		// livello: posizione di powerOutagesNerc
		cerca(0, parziale, powerOutagesNerc, years, hours);
		return best;
	}


	private void cerca(int livello, ListPowerOutages parziale, ListPowerOutages powerOutagesNerc, Period years, Duration hours) {
		int currPeople=0;
//		System.out.println("anni parziale"+parziale.getTotYears()+"\nanni years max"+best.getTotYears());
		if(parziale.getTotYears()>years.getYears() ||
				livello == powerOutagesNerc.getEventi().size())
			return;
		
		if(( currPeople = parziale.getTotPeople())>totPeopleBest&&
				parziale.getTotYears()<=years.getYears())  {
			totPeopleBest = currPeople;
			best = new ListPowerOutages(parziale);
		}
		
		for(PowerOutage po : powerOutagesNerc.getEventi()) {
//			System.out.println(po.toString()+powerOutagesNerc.getEventi().size());
			if(po.getDuration()<=hours.toHours() &&
					(!parziale.getEventi().contains(po))
					&& parziale.getTotYears()<years.getYears()) {
//				System.out.println(po.toString()+"PO\n");
				parziale.add(po);
				cerca(livello+1, parziale, powerOutagesNerc, years, hours);
				parziale.remove(po);
				
			}
		}
		
	}

	

	private List<PowerOutage> getPowerOutagesByNerc(Nerc nerc) {
		return podao.getPowerOutageSByNerc(nerc);
	}
}
