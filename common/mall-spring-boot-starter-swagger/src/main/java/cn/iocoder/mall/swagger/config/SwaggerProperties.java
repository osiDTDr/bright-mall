package cn.iocoder.mall.swagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private String basePackageForToken;
    private String basePackageForNoToken;

    public String getTitle() {
        return title;
    }

    public String getBasePackageForNoToken() {
        return basePackageForNoToken;
    }

    public void setBasePackageForNoToken(String basePackageForNoToken) {
        this.basePackageForNoToken = basePackageForNoToken;
    }

    public SwaggerProperties setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerProperties setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SwaggerProperties setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getBasePackageForToken() {
        return basePackageForToken;
    }

    public SwaggerProperties setBasePackageForToken(String basePackageForToken) {
        this.basePackageForToken = basePackageForToken;
        return this;
    }
}
