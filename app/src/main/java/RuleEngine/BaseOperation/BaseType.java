package RuleEngine.BaseOperation;

import java.util.Map;

/**
 * Created by ShengYang on 2017/2/22.
 */
public class BaseType<T> implements Expression {

    private T value;
    private Class<T> type;

    public BaseType(T value, Class<T> type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public static BaseType<?> getBaseType(String string) {

        if (string == null) {
            throw new IllegalArgumentException("The provided string must not be null");
        }

        if ("true".equals(string) || "false".equals(string)) {
            return new BaseType<>(Boolean.getBoolean(string), Boolean.class);
        } else if (string.startsWith("'")) {
            return new BaseType<>(string, String.class);
        } else if (string.contains(".")) {
            return new BaseType<>(Float.parseFloat(string), Float.class);
        } else {
            return new BaseType<>(Integer.parseInt(string), Integer.class);
        }
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        return true;
    }
}
