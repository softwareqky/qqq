/**
 * 
 */
package project.edge.mobile.controller.common;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.UpdateFieldInfoDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.BatchUpdateBean;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.service.Service;
import project.edge.domain.entity.DataFields;
import project.edge.domain.mobile.view.AddEditFieldBean;
import project.edge.domain.mobile.view.FieldListWrapperBean;
import project.edge.domain.mobile.view.OptionBean;
import project.edge.service.system.DataFieldsService;

/**
 * 共通的Controller，实现CRUD等基本功能。
 * 
 * @param <T> 数据层实体
 */
public abstract class GenericController<T> {

    @Resource
    protected MessageSource messageSource;

    @Resource
    protected DataFieldsService dataFieldsService;

    /**
     * 获得子类的logger。
     * 
     * @return
     */
    protected abstract Logger getLogger();

    /**
     * 获取Controller管理的数据类型，参考[t_data_config].data_type，如COMPANY、FACTORY...
     * 
     * API的方法将根据数据类型从[t_data_fields]中获取相关的记录。
     * 
     * @return 数据类型
     */
    protected abstract String getDataType();

    /**
     * 获取数据类型对应的Service。
     * 
     * @return Service对象
     */
    protected abstract Service<T, String> getDataService();

    /**
     * 获取下拉列表项，用于构建新增/修改字段。有下拉列表的子类必须重写此方法。
     * 
     * @param paramMap 子类需要的参数
     * @param locale
     * @return
     */
    protected Map<String, List<OptionBean>> getOptionMap(Map<String, Object> paramMap,
            Locale locale) {
        return null;
    }

    /**
     * 转换数据库实体与ViewBean。
     * 
     * @return
     */
    protected abstract Object converter(T entity);
    
    /**
     * 转换数据库实体与ViewBean。
     * 
     * @return
     */
    protected abstract Object converter(T entity,MessageSource messageSource,Locale locale);

    /**
     * 获取待修改的字段列表。
     * 
     * @param dataId
     * @param locale
     * @return
     */
    protected MobileJsonResultBean getFieldsEdit(String userId, String dataId, String fieldGroup,
            Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 检查是否存在记录
            T entity = this.getDataService().find(dataId);
            if (entity == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("dataId", dataId);

            FieldListWrapperBean listBean = new FieldListWrapperBean(dataId);
            this.prepareAddEditFieldList(paramMap, listBean, this.converter(entity,this.messageSource,locale), fieldGroup,
                    locale);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setResult(listBean);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the fields for edit.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;
    }

    protected void prepareAddEditFieldList(Map<String, Object> paramMap,
            FieldListWrapperBean listBean, Object entity, String fieldGroup, Locale locale)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        // 下拉列表项
        Map<String, List<OptionBean>> optionMap = this.getOptionMap(paramMap, locale);

        // 根据数据类型获取全局可见的字段
        CommonFilter dataFieldsFilter = new CommonFilter().addExact("dataType", this.getDataType())
                .addExact("isEnabled", OnOffEnum.ON.value())
                .addExact("isCommonVisible", OnOffEnum.ON.value())
                .addExact("isEditVisible", OnOffEnum.ON.value())
                .addExact("isApp", OnOffEnum.ON.value());
        
//        if(!StringUtils.isEmpty(fieldGroup)) {
//            dataFieldsFilter.addExact("fieldGroup", fieldGroup);
//        }

        List<DataFields> fields = this.dataFieldsService.list(dataFieldsFilter, null); // 默认按fieldOrder字段排序
        for (DataFields f : fields) {

            // 转换
            AddEditFieldBean fieldBean = new AddEditFieldBean(f, this.messageSource, locale);

            if (!StringUtils.isEmpty(fieldBean.getOption()) && optionMap != null
                    && optionMap.containsKey(fieldBean.getOption())) {
                fieldBean.setOptions(optionMap.get(fieldBean.getOption()));
            }
            
            if((!StringUtils.isEmpty(fieldGroup))&&(!f.getFieldGroup().equals(fieldGroup))) {
                fieldBean.setReadonly(true);
            }

            if (entity != null) {
                fieldBean.setFieldValue(
                        BeanUtils.getSimpleProperty(entity, fieldBean.getFieldName()));
            }

            listBean.getFieldList().add(fieldBean);
        }
    }

    /**
     * 修改字段值时，判断是否违反唯一键约束。
     * 
     * @param userId
     * @param dataId
     * @param fieldInfo
     * @param locale
     * @return
     */
    protected MobileJsonResultBean checkUpdateUnique(String userId, String dataId,
            UpdateFieldInfoDto fieldInfo, Locale locale) {
        return null;
    }

    /**
     * 修改实体的某个字段值。
     * 
     * @param userId
     * @param dataId 实体的ID
     * @param fieldInfo 字段信息
     * @param locale
     * @return
     */
    protected MobileJsonResultBean update(String userId, String dataId,
            UpdateFieldInfoDto fieldInfo, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 参数检查
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(dataId)
                    || StringUtils.isEmpty(fieldInfo.getFieldName())
                    || fieldInfo.getFieldValue() == null) {

                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            // 检查是否存在记录
            if (this.getDataService().find(dataId) == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            // 转换外键字段名
            if (fieldInfo.getFieldName().indexOf(Constants.UNDERSCORE) != -1) {
                fieldInfo.setFieldName(
                        fieldInfo.getFieldName().replaceAll(Constants.UNDERSCORE, ".id"));

                // 外键值不能是空字符串，但可以是null
                if (StringUtils.isEmpty(fieldInfo.getFieldValue())) {
                    fieldInfo.setFieldValue(null);
                }
            }

            // 转换字段值
            fieldInfo.setFieldValue(FilterFieldInfoDto.parseValue(fieldInfo.getFieldValue(),
                    fieldInfo.getValueType(), true, this.getLogger()));

            // 检查是否存在相同的记录
            MobileJsonResultBean r = this.checkUpdateUnique(userId, dataId, fieldInfo, locale);
            if (r != null) {
                return r;
            }

            BatchUpdateBean batchUpdateBean = new BatchUpdateBean();

            batchUpdateBean.getUpdateFieldInfoList().add(fieldInfo);
            batchUpdateBean.getIdList().add(dataId);

            Date now = new Date();

            batchUpdateBean.getUpdateFieldInfoList()
                    .add(new UpdateFieldInfoDto("modifier", userId, FieldValueType.STRING));
            batchUpdateBean.getUpdateFieldInfoList()
                    .add(new UpdateFieldInfoDto("mDatetime", now, FieldValueType.DATE));

            this.getDataService().update(batchUpdateBean.getUpdateFieldInfoList(),
                    batchUpdateBean.getIdList());


            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            this.getLogger().error("Exception while updating the entity.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;
    }
}
