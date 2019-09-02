package libgdx.ui.services.game.constants;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import libgdx.preferences.PreferencesService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.model.game.ConstantKeyNameVersionDb;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.mainmenu.popup.ForceUpdatePopup;
import libgdx.ui.services.dbapi.ConstantsDbApiService;
import libgdx.dbapi.DbApiService;

public class ConstantsService {

    private static final String BASE_PACKAGE = "libgdx.ui.constants.overridable";

    private static final String VERSION = "_VERSION";
    private static final String VALUE = "_VALUE";
    private PreferencesService preferencesService;

    private ConstantsDbApiService constantsDbApiService = new ConstantsDbApiService();

    public ConstantsService() {
        preferencesService = new PreferencesService("ConstantsService");
    }

    public <TType extends Enum<TType>> void loadConstantsInMemory() {
        List<ConstantKeyNameVersionDb> constantsKeyNameVersionDbs = constantsDbApiService.getAllKeyWithVersion();
        for (ConstantKeyNameVersionDb keyNameVersionDb : constantsKeyNameVersionDbs) {
            if (!Integer.valueOf(keyNameVersionDb.getVersion()).equals(preferencesService.getPreferences().getInteger(keyNameVersionDb.getKeyname() + VERSION))) {
                preferencesService.putInteger(keyNameVersionDb.getKeyname() + VERSION, keyNameVersionDb.getVersion());
                String value = constantsDbApiService.getValue(keyNameVersionDb.getKeyname());
                preferencesService.putString(keyNameVersionDb.getKeyname() + VALUE, value);
            }

            try {
                Class<TType> constantEnumType = (Class<TType>) Class.forName(BASE_PACKAGE + "." + keyNameVersionDb.getKeyname());
                JsonParser parser = new JsonParser();
                String prefValue = preferencesService.getPreferences().getString(keyNameVersionDb.getKeyname() + VALUE);
                if (StringUtils.isNotBlank(prefValue)) {
                    JsonArray array = parser.parse(prefValue).getAsJsonArray();

                    for (final JsonElement json : array) {
                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapterFactory(new EnumAdapterFactory<TType>(constantEnumType));
                        Gson gson = builder.create();
                        gson.fromJson(json, constantEnumType);
                    }
                }
            } catch (ClassNotFoundException e) {
                //ignore
            }
        }
    }

    public void processApiVersionValue(final AbstractScreen screen) {
        new Thread(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                String value = constantsDbApiService.getValue("API_VERSION_VALUE_NAME");
                if (StringUtils.isNotBlank(value) && DbApiService.API_VERSION_VALUE < Integer.parseInt(value)) {
                    Gdx.app.postRunnable(new ScreenRunnable(screen) {
                        @Override
                        public void executeOperations() {
                            new ForceUpdatePopup(TournamentGame.getInstance().getAbstractScreen()).addToPopupManager();
                        }
                    });
                }
            }
        }).start();
    }

    public <TType extends Enum<TType>> void createJsonString(Class<TType> returnType) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new EnumAdapterFactory<TType>(returnType));
        Gson gson = builder.create();
        System.out.println(gson.toJson(TransactionAmountEnum.values()));
    }
}
