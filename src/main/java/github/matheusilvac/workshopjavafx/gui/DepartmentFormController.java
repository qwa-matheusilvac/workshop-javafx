package github.matheusilvac.workshopjavafx.gui;

import github.matheusilvac.workshopjavafx.db.DBException;
import github.matheusilvac.workshopjavafx.gui.listeners.DataChangeListener;
import github.matheusilvac.workshopjavafx.gui.util.Alerts;
import github.matheusilvac.workshopjavafx.gui.util.Constraints;
import github.matheusilvac.workshopjavafx.gui.util.Utils;
import github.matheusilvac.workshopjavafx.model.exceptions.ValidationException;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Department;
import github.matheusilvac.workshopjavafx.model.services.DepartmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    @FXML
    private TextField txtId;

   @FXML
   private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    public void setService(DepartmentService service) {
        this.service = service;
    }


    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Department is null");
        }
        if (service == null) {
            throw new IllegalStateException("Department service is null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DBException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            Alerts.showAlert("Error", e.getMessage(), e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener dataChangeListener : dataChangeListeners) {
            dataChangeListener.onDataChange();
        }
    }

    private Department getFormData() {

        Department obj = new Department();
        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInteger(txtId.getText()));

        if(txtName.getText() == null || txtName.getText().trim().isEmpty()){
            exception.addError("Name", "Field Name cannot be empty");
        } obj.setName(txtName.getText());

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
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Department is null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }

    private void setErrorMessages(Map<String,String> errors){
        Set<String> keys = errors.keySet();

        if(keys.contains("Name")){
            labelErrorName.setText(errors.get("Name"));
        }
    }
}
