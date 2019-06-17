package libgdx.ui.model.game.extrainfo;

import com.google.gson.Gson;

import java.util.Objects;

public class ExtraInfoItem {

    private long creationDate;
    private String itemJson;
    private String itemTypeToString;
    private boolean expirable;

    public ExtraInfoItem(long creationDate, Object itemToEncode, boolean expirable) {
        this.expirable = expirable;
        this.creationDate = creationDate;
        this.itemJson = new Gson().toJson(itemToEncode);
        this.itemTypeToString = itemToEncode.getClass().toString();
    }

    public boolean isExpirable() {
        return expirable;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getItemJson() {
        return itemJson;
    }

    public String getItemTypeToString() {
        return itemTypeToString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraInfoItem item = (ExtraInfoItem) o;
        return creationDate == item.creationDate &&
                expirable == item.expirable &&
                Objects.equals(itemJson, item.itemJson) &&
                Objects.equals(itemTypeToString, item.itemTypeToString);
    }

    @Override
    public int hashCode() {

        return Objects.hash(creationDate, itemJson, itemTypeToString, expirable);
    }
}
