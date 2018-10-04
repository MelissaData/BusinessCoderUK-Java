package melissadata.businesscoderuk.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import melissadata.businesscoderuk.Main;
import melissadata.businesscoderuk.model.BusinessCoderUKTransaction;

public class BusinessCoderUKController {
    private Main main;
    private BusinessCoderUKTransaction Transaction;

    @FXML
    private Button buttonSend;
    @FXML
    private Button buttonClear;
    @FXML
    private TabPane tabPane;
    private final int CONFIGURATION_TAB = 0;
    private final int RESPONSE_TAB = 1;

    @FXML
    private TextField inputLicenseKeyText;
    @FXML
    private TextField inputCompanyText;
    @FXML
    private TextField inputAddressLine1Text;
    @FXML
    private TextField inputAddressLine2Text;
    @FXML
    private TextField inputAddressLine3Text;
    @FXML
    private TextField inputAddressLine4Text;
    @FXML
    private TextField inputLocalityText;
    @FXML
    private TextField inputAdministrativeAreaText;
    @FXML
    private TextField inputPostalCodeText;
    @FXML
    private TextField inputCountryText;

    @FXML
    private TextArea RequestTextArea;
    @FXML
    private TextArea ResponseTextArea;

    @FXML
    private RadioButton jsonResponseFormatRadio;
    @FXML
    private RadioButton xmlResponseFormatRadio;

    @FXML
    private CheckBox selectAllColumnsCheckbox;
    @FXML
    private CheckBox groupAddressDetails;
    @FXML
    private CheckBox groupFirmographics;
    @FXML
    private CheckBox groupGeoCode;

    public BusinessCoderUKController() {
        Transaction = new BusinessCoderUKTransaction();
    }

    @FXML
    private void initialize() {
        setColumns();
        initializeFormatRadioButtons();
        initializeTextFields();
        sendButtonAction();
        clearButtonAction();
        updateRequestText();
    }
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * //@param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

    public void sendButtonAction() {
        buttonSend.setOnAction((event) -> {
            ResponseTextArea.setText(Transaction.processTransaction(RequestTextArea.getText()));
            tabPane.getSelectionModel().select(RESPONSE_TAB);
        });
    }

    public void clearButtonAction(){
        buttonClear.setOnAction((event) -> {
            inputCompanyText.clear();
            inputAddressLine1Text.clear();
            inputAddressLine2Text.clear();
            inputAddressLine3Text.clear();
            inputAddressLine4Text.clear();
            inputLocalityText.clear();
            inputAdministrativeAreaText.clear();
            inputPostalCodeText.clear();
            inputCountryText.clear();
            returnToConfiguration();
        });
    }

    public void initializeTextFields(){
        inputLicenseKeyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setIdentNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCompanyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCompanyName(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine1Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine1(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine2Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine2(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine3Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine3(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine4Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine4(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputLocalityText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setLocality(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAdministrativeAreaText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAdministrativeArea(newvalue);
            updateRequestText();
            returnToConfiguration();

        });

        inputPostalCodeText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPostalCode(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCountryText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCountry(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

    }

    private void initializeFormatRadioButtons(){
        jsonResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("JSON");
            xmlResponseFormatRadio.setSelected(false);
            updateRequestText();
        });

        xmlResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("XML");
            jsonResponseFormatRadio.setSelected(false);
            updateRequestText();
        });
    }

    public void setColumns() {
        selectAllColumnsCheckbox.setOnAction((event) -> {
            if(!Transaction.isSelectAll()){
                Transaction.setGrpAddressDetails(true);
                groupAddressDetails.setSelected(true);

                Transaction.setGrpFirmographics(true);
                groupFirmographics.setSelected(true);

                Transaction.setGrpGeoCode(true);
                groupGeoCode.setSelected(true);
            } else {
                Transaction.setGrpAddressDetails(false);
                groupAddressDetails.setSelected(false);

                Transaction.setGrpFirmographics(false);
                groupFirmographics.setSelected(false);

                Transaction.setGrpGeoCode(false);
                groupGeoCode.setSelected(false);
            }
            Transaction.setSelectAll(selectAllColumnsCheckbox.isSelected());
            updateRequestText();
        });

        groupAddressDetails.setOnAction((event) -> {
            Transaction.setGrpAddressDetails(groupAddressDetails.isSelected());
            updateRequestText();
        });

        groupFirmographics.setOnAction((event) -> {
            Transaction.setGrpFirmographics(groupFirmographics.isSelected());
            updateRequestText();
        });

        groupGeoCode.setOnAction((event) -> {
            Transaction.setGrpGeoCode(groupGeoCode.isSelected());
            updateRequestText();
        });
    }



    private void returnToConfiguration(){
        if(tabPane.getSelectionModel().getSelectedIndex() != 0)	tabPane.getSelectionModel().select(CONFIGURATION_TAB);

    }

    private void updateRequestText(){
        RequestTextArea.setText(Transaction.generateRequestString());
    }


}
