/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.FieldListWrapperBean;

/**
 * @author angel_000
 *
 */
public class ProjectPersonBeanConverter {

    /**
     * 
     * 使用人员配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public FieldListWrapperBean fromEntity(ProjectPerson p, MessageSource messageSource,
            Locale locale) {

        if (p == null) {
            return null;
        }

        FieldListWrapperBean bean = new FieldListWrapperBean();
        bean.setDataId(p.getPerson().getId());

        List<FieldBean> fields = bean.getFieldList();

        // 姓名
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.person.person.name", null, locale),
                p.getPerson().getPersonName()));

        // 项目角色
        if (p.getProjectRole() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.person.project.role", null, locale),
                    p.getProjectRole().getProjectRoleName()));
        }

//        if (p.getPerson().getDept() != null) {
//            // 部门
//            fields.add(new FieldBean(
//                    messageSource.getMessage("ui.fields.dept.dept.name", null, locale),
//                    p.getPerson().getDept().getDeptName()));
//        }

//        if (p.getPerson().getPost() != null) {
//            // 岗位
//            fields.add(new FieldBean(
//                    messageSource.getMessage("ui.fields.project.person.post", null, locale),
//                    p.getPerson().getPost().getPostName()));
//        }


        // 移动电话
        fields.add(new FieldBean(messageSource.getMessage("ui.fields.person.mobile", null, locale),
                p.getPerson().getMobile()));

        if (p.getPerson().getPhoto() != null) {
            // 照片
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.person.photo", null, locale),
                            p.getPerson().getPhoto().getPath()));
        }


        return bean;
    }
}
