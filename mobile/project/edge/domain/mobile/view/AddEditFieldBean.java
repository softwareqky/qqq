package project.edge.domain.mobile.view;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.entity.AbstractDataFields;

/**
 * 用于APP的API中，获取新增、修改的各个字段的bean。
 * 
 */
public class AddEditFieldBean extends FieldBean {

    private static final String CONTROL_INFO_WIDGET = "widget";
    private static final String CONTROL_INFO_MULTILINE = "multiline";
    private static final String CONTROL_INFO_MULTISELECTION = "multiselection";
    private static final String CONTROL_INFO_OPTION = "option";
    private static final String CONTROL_INFO_PASSWORD = "password";
    private static final String CONTROL_INFO_VALID_TYPE = "validType";

    private String id;

    /** 对应数据库存储的字段名 */
    private String fieldName;

    /** 是否必填 */
    private boolean isMandatory;

    /** 对应[t_data_fields].control_info的widget */
    private String widget;

    /** 指定combobox类型的widget的option名 */
    private String option;

    /** 指定textbox类型的widget是否多行文本 */
    private boolean isMultiLine;

    /** 指定combobox类型的widget是否支持多选 */
    private boolean isMultiSelection;

    /** 指定textbox类型的widget是否是密码类型 */
    private boolean isPassword;

    /** 指定控件的验证类型 */
    private String validType;

    // 以下是修改专用

    /** 是否在修改时只读 */
    private boolean isReadonly;

    // 以下是新建专用

    /** 是否有默认值 */
    private boolean hasDefaultValue;

    /** 默认值 */
    private String defaultValue;

    // 以下是检索和批量修改用

    /** 字段的类型，使用FieldValueType */
    private int valueType;

    // 以下是验证相关的字段

    /** 文本类型字段的最大长度 */
    private String maxLength;

    /** 数值类型字段的最小值 */
    private String min;

    /** 数值类型字段的最大值 */
    private String max;

    /** 数值类型字段的精度 */
    private String precision;

    /** 合法范围提示信息 */
    private String validationPrompt;

    /** combobox类型的字段的下拉选项 */
    List<OptionBean> options = new ArrayList<>();

    public AddEditFieldBean(AbstractDataFields f, MessageSource messageSource, Locale locale) {
        if (f == null || messageSource == null || locale == null) {
            return;
        }

        this.id = f.getId();
        this.fieldName = f.getFieldName();
        this.captionName = messageSource.getMessage(f.getCaptionName(), null, locale);
        this.isMandatory = OnOffEnum.ON.value().equals(f.getIsMandatory());
        this.isReadonly = OnOffEnum.ON.value().equals(f.getIsReadonly());
        this.hasDefaultValue = OnOffEnum.ON.value().equals(f.getHasDefaultValue());
        this.defaultValue = f.getDefaultValue();

        this.valueType = f.getValueType();

        // 验证信息
        String v = f.getValidationInfo();
        if (!StringUtils.isEmpty(v)) {
            String[] vArray = StringUtils.commaDelimitedListToStringArray(v);
            if (vArray.length == 1) { // 表示是文本的长度
                this.maxLength = vArray[0];
                this.validationPrompt =
                        messageSource.getMessage("ui.common.maxlength", null, locale) + " "
                                + this.maxLength;
            } else if (vArray.length == 2) { // 表示是整型的取值范围
                this.min = vArray[0];
                this.max = vArray[1];
                this.validationPrompt = this.min + " ~ " + this.max;
            } else if (vArray.length == 3) { // 表示是浮点型的取值范围以及精度
                this.min = vArray[0];
                this.max = vArray[1];
                this.validationPrompt = this.min + " ~ " + this.max;
                this.precision = vArray[2];
            }
        }

        // control info
        String c = f.getControlInfo();
        if (!StringUtils.isEmpty(c)) {
            String[] cArray = StringUtils.delimitedListToStringArray(c, Constants.COMMA);
            if (cArray.length != 0) {
                for (String control : cArray) {

                    String[] itemArray =
                            StringUtils.delimitedListToStringArray(control, Constants.COLON);
                    String key = itemArray[0];
                    if (key.equals(CONTROL_INFO_WIDGET)) {
                        this.widget = itemArray[1];
                    } else if (key.equals(CONTROL_INFO_OPTION)) {
                        this.option = itemArray[1];
                    } else if (key.equals(CONTROL_INFO_MULTILINE)) {
                        this.isMultiLine = OnOffEnum.ON.value().toString().equals(itemArray[1]);
                    } else if (key.equals(CONTROL_INFO_MULTISELECTION)) {
                        this.isMultiSelection =
                                OnOffEnum.ON.value().toString().equals(itemArray[1]);
                    } else if (key.equals(CONTROL_INFO_PASSWORD)) {
                        this.isPassword = OnOffEnum.ON.value().toString().equals(itemArray[1]);
                    } else if (key.equals(CONTROL_INFO_VALID_TYPE)) {
                        this.validType = itemArray[1];
                    }
                }
            }
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isMandatory() {
        return this.isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getWidget() {
        return this.widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isMultiLine() {
        return this.isMultiLine;
    }

    public void setMultiLine(boolean isMultiLine) {
        this.isMultiLine = isMultiLine;
    }

    public boolean isPassword() {
        return this.isPassword;
    }

    public void setPassword(boolean isPassword) {
        this.isPassword = isPassword;
    }

    public String getValidType() {
        return this.validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public boolean isReadonly() {
        return this.isReadonly;
    }

    public void setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    public boolean isHasDefaultValue() {
        return this.hasDefaultValue;
    }

    public void setHasDefaultValue(boolean hasDefaultValue) {
        this.hasDefaultValue = hasDefaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMin() {
        return this.min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return this.max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getPrecision() {
        return this.precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getValidationPrompt() {
        return this.validationPrompt;
    }

    public void setValidationPrompt(String validationPrompt) {
        this.validationPrompt = validationPrompt;
    }

    public List<OptionBean> getOptions() {
        return this.options;
    }

    public void setOptions(List<OptionBean> options) {
        this.options = options;
    }

    public boolean isMultiSelection() {
        return this.isMultiSelection;
    }

    public void setMultiSelection(boolean isMultiSelection) {
        this.isMultiSelection = isMultiSelection;
    }
}
