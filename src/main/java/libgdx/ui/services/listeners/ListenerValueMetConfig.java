package libgdx.ui.services.listeners;

public class ListenerValueMetConfig extends ListenerConfig {

    private Object expectedValue;

    public ListenerValueMetConfig(String fieldName, Object expectedValue, Class<?> resultClassType) {
        super(fieldName, resultClassType);
        this.expectedValue = expectedValue;
    }

    public Object getExpectedValue() {
        return expectedValue;
    }

}
