package libgdx.ui.services.listeners;

import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.dbapi.DbApiService;

public abstract class ListenerValueChange<TScreen extends AbstractScreen, TDbApiService extends DbApiService> extends Listener<TScreen> {

    private Object previousValue;
    private int entityId;
    private TDbApiService dbApiService;

    public ListenerValueChange(int entityId, TScreen screen, ListenerConfig fieldValueChangeConfig) {
        super(screen, fieldValueChangeConfig);
        this.entityId = entityId;
        this.dbApiService = createDbApiService();
        previousValue = getColumnValueForConfig(entityId, fieldValueChangeConfig);
    }

    @Override
    protected void execute() {
        Object value = getColumnValueForConfig(getEntityId(), getConfigs()[0]);
        if (valueIsChanged(value)) {
            previousValue = value;
            executeOperations(value);
        }
    }

    private Object getColumnValueForConfig(int entityId, ListenerConfig config) {
        return getDbApiService().getColumnValue(entityId, config.getFieldName(), config.getResultClassType());
    }

    private boolean valueIsChanged(Object value) {
        return value != null && !value.equals(previousValue);
    }

    public Object getPreviousValue() {
        return previousValue;
    }

    protected int getEntityId() {
        return entityId;
    }

    public abstract void executeOperations(Object val);

    protected TDbApiService getDbApiService() {
        return dbApiService;
    }

    protected abstract TDbApiService createDbApiService();


}
