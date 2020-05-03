package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Nerc> boxNERC;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button btnAnalyze;

    @FXML
    private TextArea txtResult;

    @FXML
    void doWorstCaseAnalysis(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	try {
    		int anni = Integer.parseInt(this.txtYears.getText());
    		int ore = Integer.parseInt(this.txtHours.getText());
    		Nerc nerc = this.boxNERC.getValue();
    		List<PowerOutage> soluzione = this.model.trovaWorstCase(nerc, anni, ore);
    		this.txtResult.appendText("Tot people affected: " + this.model.getMaxTotalAffected() + "\n");
    		this.txtResult.appendText("Tot hours of outage: " + this.model.getTotalHours() + "\n");
    		for(PowerOutage po : soluzione) {
    			this.txtResult.appendText(po.toString() + "\n");
    		}
    		
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Errore! Nelle caselle di testo 'Max Years' e 'Max Hours' puoi inserire solo valori numerici!\n");
    	}

    }

    @FXML
    void initialize() {
        assert boxNERC != null : "fx:id=\"boxNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalyze != null : "fx:id=\"btnAnalyze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxNERC.getItems().addAll(this.model.getNercList());
		
	}
}
