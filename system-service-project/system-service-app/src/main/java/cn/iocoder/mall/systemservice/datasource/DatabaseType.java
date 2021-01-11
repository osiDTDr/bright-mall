package cn.iocoder.mall.systemservice.datasource;

/**
 * 数据源 枚举
 *
 * @author zhengyuan
 * @since 2021/01/09
 */
public enum DatabaseType {
    FUNCTION("function", "1"),  // 功能环境
    REGRESSION("regression", "2");  // 回归环境

    private final String name;
    private final String value;

    DatabaseType(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
