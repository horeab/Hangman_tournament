package libgdx.ui.services.listeners;

public class ListenerConfig {

    private String fieldName;
    private Class<?> resultClassType;

    public ListenerConfig(String fieldName, Class<?> resultClassType) {
        this.fieldName = fieldName;
        this.resultClassType = resultClassType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getResultClassType() {
        return resultClassType;
    }
}
