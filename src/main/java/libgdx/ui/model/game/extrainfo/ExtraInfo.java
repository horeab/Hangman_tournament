package libgdx.ui.model.game.extrainfo;

import java.util.LinkedHashSet;
import java.util.Set;

public class ExtraInfo {

    private Set<ExtraInfoItem> items = new LinkedHashSet<>();

    public void addItem(ExtraInfoItem item) {
        items.add(item);
    }

    public void removeItem(ExtraInfoItem item) {
        items.remove(item);
    }

    public Set<ExtraInfoItem> getItems() {
        return items;
    }
}
