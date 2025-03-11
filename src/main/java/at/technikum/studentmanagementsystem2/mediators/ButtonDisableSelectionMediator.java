package at.technikum.studentmanagementsystem2.mediators;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ButtonDisableSelectionMediator implements Mediator {
    private Button button;
    private TableView tableview;
    private boolean[] disable = {false, false, false};

    public ButtonDisableSelectionMediator(Button button, TableView tableview, boolean disableNoSelection, boolean disableOneSelection, boolean disableMultipleSelection) {
        this.button = button;
        this.tableview = tableview;
        this.disable[0] = disableNoSelection;
        this.disable[1] = disableOneSelection;
        this.disable[2] = disableMultipleSelection;
        this.tableview.getSelectionModel().getSelectedItems().addListener((ListChangeListener)change->updateButtonState());

    }

    public void updateButtonState() {
        int selectedCount = tableview.getSelectionModel().getSelectedItems().size();
        if (((selectedCount == 0) && (disable[0])) || ((selectedCount == 1) && (disable[1])) || ((selectedCount > 1) && (disable[2]))) {
            this.button.setDisable(true);
        } else {
            this.button.setDisable(false);
        }
    }


}
