package it.polito.tdp.poweroutages;

import java.net.URL;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import it.polito.tdp.poweroutages.model.ListPowerOutages;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.ModelAltro;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesController {
	
//	private Model model;
	private ModelAltro model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Nerc> comboBoxNerc;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button btnWorstCaseAnalysis;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleWorstCaseAnalaysis(ActionEvent event) {
    	
    	Nerc nerc =  this.comboBoxNerc.getValue();
//    	int years = Integer.parseInt(this.txtYears.getText());
    	Period years = Period.ofYears(Integer.parseInt(this.txtYears.getText()));
    	Duration hours = Duration.ofHours(Integer.parseInt(this.txtHours.getText()));
    	
//    	ListPowerOutages worstPowerOutages = new ListPowerOutages(); 
//    	worstPowerOutages = model.cercaWorstBlackout(nerc, years, hours);
    	List<PowerOutage> worstPowerOutages = new ArrayList<>(); 
    	worstPowerOutages = model.cercaWorstBlackout(nerc, years, hours);
    	
//    	Long totHours=worstPowerOutages.getTotHours();
//    	int totPeople=worstPowerOutages.getTotPeople();
    	Long totHours=model.getTotHours(worstPowerOutages);
    	int totPeople=model.getTotPeople(worstPowerOutages);
    	
    	this.txtResult.appendText("Tot people affected: "+totPeople+"\n"+
    			"Tot hours of outage: "+totHours+"\n"+
    			worstPowerOutages.toString());
     }

    @FXML
    void initialize() {
        assert comboBoxNerc != null : "fx:id=\"comboBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnWorstCaseAnalysis != null : "fx:id=\"btnWorstCaseAnalysis\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }
    


//	public void setModel(Model model) {
//
//        this.comboBoxNerc.getItems().addAll(model.getNercList());
//		this.model = model;		
//	}
    
	public void setModel(ModelAltro model) {

        this.comboBoxNerc.getItems().addAll(model.getNercList());
		this.model = model;		
	}
}