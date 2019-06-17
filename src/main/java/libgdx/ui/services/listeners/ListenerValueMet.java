package libgdx.ui.services.listeners;

import java.util.HashMap;
import java.util.Map;

import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.dbapi.DbApiService;

public abstract class ListenerValueMet<TScreen extends AbstractScreen, TDbApiService extends DbApiService> extends Listener<TScreen> {

    private Map<ListenerValueMetConfig, Boolean> fieldsHaveExpectedValue = new HashMap<>();
    private int entityId;
    private TDbApiService dbApiService;
    private boolean operationsAlreadyExecuted = false;

    public ListenerValueMet(int entityId, TScreen screen, ListenerValueMetConfig... fieldsWithExpectedValue) {
        super(screen, fieldsWithExpectedValue);
        this.entityId = entityId;
        this.dbApiService = createDbApiService();
        for (ListenerValueMetConfig field : fieldsWithExpectedValue) {
            fieldsHaveExpectedValue.put(field, false);
        }
    }

    /**
     * The executeOperations method will be executed only after all expected values have been met.
     */
    @Override
    protected synchronized void execute() {
        for (ListenerValueMetConfig field : fieldsHaveExpectedValue.keySet()) {
            Object value = getDbApiService().getColumnValue(getEntityId(), field.getFieldName(), field.getResultClassType());
            if (value != null && field.getExpectedValue().equals(value)) {
                fieldsHaveExpectedValue.put(field, true);
                if (allFieldHaveExpectedValue() && !operationsAlreadyExecuted) {
                    operationsAlreadyExecuted = true;
                    executeOperations();
                    getExecutorService().shutdown();
                }
            }
        }
    }

    private boolean allFieldHaveExpectedValue() {
        for (Map.Entry<ListenerValueMetConfig, Boolean> entry : fieldsHaveExpectedValue.entrySet()) {
            if (!entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public int getEntityId() {
        return entityId;
    }

    protected abstract void executeOperations();

    public TDbApiService getDbApiService() {
        return dbApiService;
    }

    protected abstract TDbApiService createDbApiService();


}
