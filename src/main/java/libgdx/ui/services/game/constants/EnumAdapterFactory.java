package libgdx.ui.services.game.constants;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;

public class EnumAdapterFactory<TType extends Enum<TType>> implements TypeAdapterFactory {

    private Class<TType> returnType;

    EnumAdapterFactory(Class<TType> returnType) {
        this.returnType = returnType;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (rawType.isEnum()) {
            return new EnumTypeAdapter<T>();
        }
        return null;
    }

    public class EnumTypeAdapter<T> extends TypeAdapter<T> {

        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            Enum<?> realEnums = Enum.valueOf(value.getClass().asSubclass(Enum.class), value.toString());
            Field[] enumFields = realEnums.getClass().getDeclaredFields();
            out.beginObject();
            out.name("name");
            out.value(realEnums.name());
            for (Field enumField : enumFields) {
                if (enumField.isEnumConstant() || enumField.getName().equals("$VALUES")) {
                    continue;
                }
                enumField.setAccessible(true);
                try {
                    out.name(enumField.getName());
                    out.value(enumField.get(realEnums).toString());
                } catch (Throwable th) {
                    out.value("");
                }
            }
            out.endObject();
        }

        public T read(JsonReader in) throws IOException {
            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                if (name.equals("name")) {
                    processEnum(in);
                } else {
                    in.skipValue();
                }
            }
            in.endObject();

            return null;
        }

        private void processEnum(JsonReader in) throws IOException {
            String enumName = in.nextString();
            for (TType item : returnType.getEnumConstants()) {
                if (item.name().equals(enumName)) {
                    while (in.hasNext()) {
                        String fieldName = in.nextName();
                        String fieldValue = in.nextString();
                        for (Field enumField : returnType.getDeclaredFields()) {
                            if (enumField.isEnumConstant() || enumField.getName().equals("$VALUES")) {
                                continue;
                            }
                            enumField.setAccessible(true);
                            if (fieldName.equals(enumField.getName())) {
                                setValue(item, enumField, fieldValue);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }

    }

    private void setValue(TType item, Field enumField, String fieldValue) {
        try {
            if (enumField.getType().equals(Integer.TYPE)) {
                enumField.setInt(item, Integer.parseInt(fieldValue));
            } else if (enumField.getType().equals(Boolean.TYPE)) {
                enumField.setBoolean(item, Boolean.parseBoolean(fieldValue));
            } else if (enumField.getType().equals(Long.TYPE)) {
                enumField.setLong(item, Long.parseLong(fieldValue));
            } else {
                enumField.set(item, fieldValue);
            }
        } catch (Throwable th) {
            //Ignore
        }
    }
}