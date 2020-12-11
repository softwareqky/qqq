package project.edge.domain.business;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import garage.origin.common.constant.Constants;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.entity.DataConfig;
import project.edge.domain.entity.SystemConfig;


/**
 * 系统参数设定，使用单例，用来在内存中保存系统的设置。
 * 
 */
public class SystemConfigSettings {

    // private static final Logger logger = LoggerFactory.getLogger(SystemConfigSettings.class);

    private static SystemConfigSettings instance = null;

    private SystemConfigSettings() {}

    public synchronized static SystemConfigSettings getInstance() {
        if (instance == null) {
            instance = new SystemConfigSettings();
        }
        return instance;
    }

    private String platform; // DI by Spring

    /** 系统版本 */
    private String version;

    /** web服务器地址 */
    private String mobileServerUri;

    /** 证书地址 */
    private String mobileCertUri;

    /** 轮询间隔（秒） */
    private int mobilePollingInterval;

    /** App最新版本 */
    private String mobileVserion;

    /** App更新地址 */
    private String mobileDownUrl;

    // 基础配置

    /** Locale */
    private String localeText = Constants.DEFAULT_LOCALE;
    private Locale locale = null;

    /** 2-letter language codes defined in ISO 639. */
    private String lang = "zh";

    /** Used by easyui. */
    private String langEasyui = "zh_CN";

    /** 根据locale生成，国家信息 */
    private String country = "CN";

    /** Theme */
    private String theme = "origin";

    private ConcurrentHashMap<String, DataConfig> dataConfigMap =
            new ConcurrentHashMap<String, DataConfig>();


    /**
     * 更新系统参数设定。
     * 
     * @param settings 新的系统参数列表
     * @throws ParseException
     */
    public void update(Collection<SystemConfig> settings) {
        if (settings == null || settings.size() == 0) {
            return;
        }
        for (SystemConfig s : settings) {
            if (SystemConfigKeys.LOCALE.equals(s.getId())) {
                this.localeText = s.getConfigValue();

                String[] localeArray = StringUtils.delimitedListToStringArray(this.localeText,
                        Constants.UNDERSCORE);
                if (localeArray.length > 1) {
                    this.lang = localeArray[0];
                    this.country = localeArray[1];
                } else {
                    localeArray = StringUtils.delimitedListToStringArray(Constants.DEFAULT_LOCALE,
                            Constants.UNDERSCORE);
                    this.lang = localeArray[0];
                    this.country = localeArray[1];
                    this.localeText = Constants.DEFAULT_LOCALE;
                }
                this.locale = StringUtils.parseLocaleString(this.localeText);

                // easyui使用的language，除了zh_CN、zh_TW、pt_BR、sv_SE，其他只指定locale中的语言部分
                if ("zh_CN".equals(this.localeText) || "zh_TW".equals(this.localeText)
                        || "pt_BR".equals(this.localeText) || "sv_SE".equals(this.localeText)) {
                    this.langEasyui = this.localeText;
                } else {
                    this.langEasyui = this.lang;
                }

            } else if (SystemConfigKeys.VERSION.equals(s.getId())) {
                if (!StringUtils.isEmpty(s.getConfigValue())) {
                    this.version = s.getConfigValue();
                }
            } else if (SystemConfigKeys.MOBILE_SERVER_URI.equals(s.getId())) {
                if (!StringUtils.isEmpty(s.getConfigValue())) {
                    this.mobileServerUri = s.getConfigValue();
                }
            } else if (SystemConfigKeys.MOBILE_CERT_URI.equals(s.getId())) {
                if (!StringUtils.isEmpty(s.getConfigValue())) {
                    this.mobileCertUri = s.getConfigValue();
                }
            } else if (SystemConfigKeys.MOBILE_POLLING_INTERVAL.equals(s.getId())) {
                if (!StringUtils.isEmpty(s.getConfigValue())) {
                    try {
                        this.mobilePollingInterval = Integer.parseInt(s.getConfigValue());
                    } catch (Exception e) {
                        // to do log huang 20191105
                        this.mobilePollingInterval = 5;
                    }
                }
            } else if (SystemConfigKeys.MOBILE_VERSION.equals(s.getId())) {
                if (!StringUtils.isEmpty(s.getConfigValue())) {
                    this.mobileVserion = s.getConfigValue();
                }
            } else if (SystemConfigKeys.MOBILE_DOWN_URI.equals(s.getId())) {
                if (!StringUtils.isEmpty(s.getConfigValue())) {
                    this.mobileDownUrl = s.getConfigValue();
                }
            }
        }
    }

    /**
     * 更新系统参数设定
     * 
     * @param dataConfig 新的系统参数列表
     */
    public void update(DataConfig dataConfig) {

        if (dataConfig == null || dataConfig.getDataType() == null
                || dataConfig.getDataType().equals("")) {
            return;
        }
        this.dataConfigMap.put(dataConfig.getDataType(), dataConfig);
    }

    /**
     * 根据数据类型，获取数据配置。
     * 
     * @param dataType 数据类型
     * @return 数据配置对象，如果没有找到相应的配置，则返回null
     */
    public DataConfig getDataConfig(String dataType) {

        if (dataType == null || dataType.equals("")) {
            return null;
        }

        if (this.dataConfigMap.containsKey(dataType)) {
            return this.dataConfigMap.get(dataType);
        }

        return null;
    }

    /**
     * 系统运行的平台
     * 
     * @return
     */
    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    // 以下对应[t_system_config]

    /** 系统版本 */
    public String getVersion() {
        return this.version;
    }

    /** Locale */
    public String getLocaleText() {
        return this.localeText;
    }

    public Locale getLocale() {
        return this.locale;
    }

    /** 2-letter language codes defined in ISO 639. */
    public String getLang() {
        return this.lang;
    }

    /** Used by easyui. */
    public String getLangEasyui() {
        return this.langEasyui;
    }

    /** 根据locale生成的国家信息 */
    public String getCountry() {
        return this.country;
    }

    /** Theme */
    public String getTheme() {
        return this.theme;
    }

    /** 服务器地址 */
    public String getMobileServerUri() {
        return this.mobileServerUri;
    }


    public void setMobileServerUri(String mobileServerUri) {
        this.mobileServerUri = mobileServerUri;
    }

    /** 证书地址 */
    public String getMobileCertUri() {
        return this.mobileCertUri;
    }


    public void setMobileCertUri(String mobileCertUri) {
        this.mobileCertUri = mobileCertUri;
    }

    /** 轮询时间 */
    public int getMobilePollingInterval() {
        return this.mobilePollingInterval;
    }


    public void setMobilePollingInterval(int mobilePollingInterval) {
        this.mobilePollingInterval = mobilePollingInterval;
    }

    /** 移动到版本 */
    public String getMobileVserion() {
        return this.mobileVserion;
    }


    public void setMobileVserion(String mobileVserion) {
        this.mobileVserion = mobileVserion;
    }

    /** 下载地址 */
    public String getMobileDownUrl() {
        return this.mobileDownUrl;
    }


    public void setMobileDownUrl(String mobileDownUrl) {
        this.mobileDownUrl = mobileDownUrl;
    }


}
