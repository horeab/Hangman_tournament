package libgdx.ui.services.game;

import com.google.gson.Gson;


import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import libgdx.ui.model.game.extrainfo.ExtraInfo;
import libgdx.ui.model.game.extrainfo.ExtraInfoItem;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.utils.DateUtils;

public class UserExtraInfoService {

    public static final int MILLIS_FROM_NOW__EXTRA_INFO_ITEM_EXPIRES = 6000;
    private UsersDbApiService usersDbApiService = new UsersDbApiService();

    public <TItemType> Set<TItemType> getExtraInfoItems(Integer userId, Class<TItemType> itemType) {
        Set<TItemType> resultList = new LinkedHashSet<>();
        ExtraInfo extraInfo = getExtraInfoFromDb(userId);
        for (ExtraInfoItem item : extraInfo.getItems()) {
            if (item.getItemTypeToString().equals(itemType.toString())) {
                resultList.add(new Gson().fromJson(item.getItemJson(), itemType));
            }
        }
        return resultList;
    }

    public <TItemType> TItemType getExtraInfoItem(ExtraInfoItem item, Class<TItemType> itemType) {
        TItemType result = null;
        if (item.getItemTypeToString().equals(itemType.toString())) {
            result = new Gson().fromJson(item.getItemJson(), itemType);
        }
        return result;
    }

    public void addExtraInfoItem(Integer userId, ExtraInfoItem extraInfoItem) {
        ExtraInfo extraInfo = getExtraInfoFromDb(userId);
        extraInfo.addItem(extraInfoItem);
        cleanExpiredItems(extraInfo);
        updateExtraInfo(userId, extraInfo);
    }

    public void removeExtraInfoItem(Integer userId, ExtraInfo extraInfo, ExtraInfoItem extraInfoItem) {
        extraInfo.removeItem(extraInfoItem);
        updateExtraInfo(userId, extraInfo);
    }

    private void updateExtraInfo(Integer userId, ExtraInfo extraInfo) {
        try {
            usersDbApiService.incrementColumnValue(userId, UsersDbApiService.COLUMN_NAME_EXTRAINFOCHANGE);
            usersDbApiService.updateColumnValue(userId, UsersDbApiService.COLUMN_NAME_EXTRAINFO, new Gson().toJson(extraInfo)).join();
        } catch (InterruptedException e) {
            //ignore
        }
    }

    public void removeExtraInfoItem(Integer userId, ExtraInfoItem extraInfoItem) {
        removeExtraInfoItem(userId, getExtraInfoFromDb(userId), extraInfoItem);
    }

    public ExtraInfo getExtraInfoFromDb(Integer userId) {
        ExtraInfo extraInfo = new Gson().fromJson(usersDbApiService.getColumnValue(userId, UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class), ExtraInfo.class);
        if (extraInfo == null) {
            extraInfo = new ExtraInfo();
        }
        cleanExpiredItems(extraInfo);
        return extraInfo;
    }

    private void cleanExpiredItems(ExtraInfo extraInfo) {
        Set<ExtraInfoItem> items = new HashSet<>(extraInfo.getItems());
        for (ExtraInfoItem item : items) {
            if (item.isExpirable() && itemCreationDateIsExpired(item.getCreationDate())) {
                extraInfo.removeItem(item);
            }
        }
    }

    public boolean itemCreationDateIsExpired(Long itemCreationDate) {
        return DateUtils.minusMillis(MILLIS_FROM_NOW__EXTRA_INFO_ITEM_EXPIRES).after(new Date(itemCreationDate));
    }

}
