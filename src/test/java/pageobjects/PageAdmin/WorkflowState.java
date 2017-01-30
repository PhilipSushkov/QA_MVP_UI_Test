package pageobjects.PageAdmin;

/**
 * Created by philipsushkov on 2017-01-30.
 */

public enum WorkflowState {
    LIVE("Live"),
    FOR_APPROVAL("For Approval"),
    IN_PROGRESS("In Progress"),
    DELETE_PENDING("Delete Pending"),
    NEW_ITEM("New Item");

    private String state;

    WorkflowState(String state) {
        this.state = state;
    }

    public String state() {
        return state;
    }
}
