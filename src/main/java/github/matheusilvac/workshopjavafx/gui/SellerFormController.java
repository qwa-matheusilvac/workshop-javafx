package github.matheusilvac.workshopjavafx.gui;

import github.matheusilvac.workshopjavafx.db.DBException;
import github.matheusilvac.workshopjavafx.gui.listeners.DataChangeListener;
import github.matheusilvac.workshopjavafx.gui.util.Alerts;
import github.matheusilvac.workshopjavafx.gui.util.Constraints;
import github.matheusilvac.workshopjavafx.gui.util.Utils;
import github.matheusilvac.workshopjavafx.model.exceptions.ValidationException;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Department;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Seller;
import github.matheusilvac.workshopjavafx.model.services.DepartmentService;
import github.matheusilvac.workshopjavafx.model.services.SellerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    @FXML
    private TextField txtId;

   @FXML
   private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private Label labelErrorName;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthdate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Label labelErrorDepartment;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private ObservableList<Department> obsList;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setServices(SellerService service, DepartmentService departmentService ) {
        this.service = service;
        this.departmentService = departmentService;
    }


    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Seller is null");
        }
        if (service == null) {
            throw new IllegalStateException("Seller service is null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DBException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
            Alerts.showAlert("Error", e.getMessage(), e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener dataChangeListener : dataChangeListeners) {
            dataChangeListener.onDataChange();
        }
    }

    private Seller getFormData() {

        Seller obj = new Seller();
        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInteger(txtId.getText()));

        if(txtName.getText() == null || txtName.getText().trim().isEmpty()){
            exception.addError("Name", "Field Name cannot be empty");
        } obj.setName(txtName.getText());

        if(txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()){
            exception.addError("Email", "Field Name cannot be empty");
        } obj.setEmail(txtEmail.getText());

        if(dpBirthdate.getValue() == null){
            exception.addError("Birthdate", "Field birthdate cannot be empty");
        } else {
            Instant instant = Instant.from(dpBirthdate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        if(txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().isEmpty()){
            exception.addError("BaseSalary", "Field Name cannot be empty");
        } obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        dataChangeListeners.add(dataChangeListener);
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 50);
        Utils.formatDatePicker(dpBirthdate, "dd/MM/yyyy");
        initializeComboBoxDepartment();

    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Seller is null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        txtBaseSalary.setText(String.valueOf(entity.getBaseSalary()));

        if (entity.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects() {
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String,String> errors){
        Set<String> keys = errors.keySet();

        labelErrorName.setText((keys.contains("Name") ? errors.get("Name") : ""));

        labelErrorEmail.setText((keys.contains("Email") ? errors.get("Email") : ""));

        labelErrorBirthdate.setText((keys.contains("BirthDate") ? errors.get("BirthDate") : ""));

        labelErrorBaseSalary.setText((keys.contains("BaseSalary") ? errors.get("BaseSalary") : ""));

        labelErrorDepartment.setText((keys.contains("Department") ? errors.get("Department") : ""));


    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
