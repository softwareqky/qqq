package project.edge.common.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import garage.origin.common.constant.FileEncodingTypeEnum;
import garage.origin.common.constant.FilterSearchBy;
import garage.origin.common.util.CheckUtils;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ListUtils;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import project.edge.domain.business.SimpleCache;
import project.edge.domain.entity.DataFields;
import project.edge.domain.entity.Page;

/**
 * Demo的数据工具类。
 *
 */
public class DemoDataUtils {

    private static final Logger logger = LoggerFactory.getLogger(DemoDataUtils.class);

    /**
     * 解析并转换功能按钮的Page。
     * 
     * @param dataFile
     * @return
     */
    public static List<Page> parsePageList(File dataFile) {

        List<Page> list = new ArrayList<>();

        try {
            CSVFormat format = CSVFormat.TDF.withIgnoreEmptyLines(true).withFirstRecordAsHeader()
                    .withSkipHeaderRecord();
            Charset charset = Charset.defaultCharset();
            if (Charset.isSupported(FileEncodingTypeEnum.UTF8)) {
                charset = Charset.forName(FileEncodingTypeEnum.UTF8);
            }

            try (CSVParser parser = CSVParser.parse(dataFile, charset, format)) {

                for (CSVRecord record : parser) {

                    Page p = new Page();
                    p.setId(record.get(0).trim());
                    p.setPageName(record.get(1).trim());
                    p.setIcon(record.get(3).trim());
                    p.setUrl(record.get(5).trim());
                    p.setPid(record.get(6).trim());
                    p.setIsLeafPage(Short.valueOf(record.get(7).trim()));

                    list.add(p);
                }
            }

        } catch (Exception e) {
            logger.error("Exption parsing demo page list.", e);
        }

        return list;
    }



    /**
     * 解析并转换DataFields。
     * 
     * @param dataFile
     * @return
     */
    public static List<DataFields> parseDataFieldsList(File dataFile) {

        List<DataFields> list = new ArrayList<>();

        try {
            CSVFormat format = CSVFormat.TDF.withIgnoreEmptyLines(true).withFirstRecordAsHeader()
                    .withSkipHeaderRecord();
            Charset charset = Charset.defaultCharset();
            if (Charset.isSupported(FileEncodingTypeEnum.UTF8)) {
                charset = Charset.forName(FileEncodingTypeEnum.UTF8);
            }

            try (CSVParser parser = CSVParser.parse(dataFile, charset, format)) {

                for (CSVRecord record : parser) {

                    DataFields f = new DataFields();
                    f.setId(record.get(1).trim());
                    f.setFieldName(record.get(3).trim());
                    f.setFieldNameView(record.get(4).trim());
                    f.setCaptionName(record.get(5).trim());
                    f.setIsMandatory(Short.valueOf(record.get(6).trim()));
                    f.setIsCustomizable(Short.valueOf(record.get(8).trim()));
                    f.setIsCommonVisible(Short.valueOf(record.get(9).trim()));
                    f.setIsListVisible(Short.valueOf(record.get(10).trim()));
                    f.setIsEditVisible(Short.valueOf(record.get(11).trim()));
                    f.setIsReadonly(Short.valueOf(record.get(12).trim()));
                    f.setIsBatchEditable(Short.valueOf(record.get(13).trim()));
                    f.setFilterType(CheckUtils.checkStringToInteger(record.get(14).trim()));
//del by huang 20191010
//                    f.setFilterInfo(CheckUtils.checkStringToInteger(record.get(15).trim()));
//                    f.setSearchMode(CheckUtils.checkStringToInteger(record.get(16).trim())); // TODO
//del by huang 20191010
                    f.setValueType(CheckUtils.checkStringToInteger(record.get(18).trim()));
                    f.setValidationInfo(record.get(19).trim());
                    f.setHasDefaultValue(Short.valueOf(record.get(20).trim()));
                    f.setDefaultValue(record.get(21).trim());
                    f.setControlInfo(record.get(22).trim());
                    f.setIsCopyReserve(Short.valueOf(record.get(23).trim()));
                    f.setListColumnWidth(CheckUtils.checkStringToInteger(record.get(24).trim()));
                    f.setFieldGroup(record.get(25).trim());
                    f.setIsListFrozen(Short.valueOf(record.get(32).trim()));

                    String isGroupRoot = record.get(34).trim();
                    if (!StringUtils.isEmpty(isGroupRoot)) {
                        f.setIsGroupRoot(Short.valueOf(isGroupRoot));
                    }

                    list.add(f);
                }
            }

        } catch (Exception e) {
            logger.error("Exption parsing demo data fields list.", e);
        }

        return list;
    }

    /**
     * 解析并转换一览的datagrid中的list数据。
     * 
     * @param dataFile
     * @param cacheKey
     * @param filter
     * @param orders
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> parseDataList(File dataFile, String cacheKey,
            CommonFilter filter, List<OrderByDto> orders, PageCtrlDto page) {

        try {

            List<Map<String, Object>> cacheList = null;
            if (StringUtils.isEmpty(cacheKey) || !SimpleCache.instance().containsKey(cacheKey)) {

                try (Workbook workbook = WorkbookFactory.create(dataFile)) {

                    Sheet sheet = workbook.getSheetAt(0);

                    cacheList = new ArrayList<>();

                    List<String> headerList = new ArrayList<>();
                    for (Row row : sheet) {

                        if (row.getRowNum() == 0) {

                            // 首行作为字段名
                            for (Cell cell : row) {
                                headerList.add(DemoDataUtils.parseCellToString(cell));
                            }
                        } else {

                            Map<String, Object> record = new HashMap<String, Object>();
                            int n = headerList.size();
                            for (int i = 0; i < n; i++) {
                                record.put(headerList.get(i),
                                        DemoDataUtils.removeZero(
                                                (DemoDataUtils.parseCellToString(row.getCell(i,
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)))));
                            }
                            record.put("id", UUID.randomUUID().toString());
                            cacheList.add(record);
                        }
                    }
                    if (!StringUtils.isEmpty(cacheKey)) {
                        SimpleCache.instance().put(cacheKey, cacheList);
                    }
                }
            } else {
                cacheList = (List<Map<String, Object>>) SimpleCache.instance().get(cacheKey);
            }

            // TODO 过滤与排序
            if (filter != null) {
                for (FilterFieldInfoDto f : filter.getFilterFieldList()) {

                    if (f.getFilterSearchBy() == FilterSearchBy.BY_VALUE && f.getValue() != null
                            && !StringUtils.isEmpty(f.getValue().toString())) {

                        cacheList = cacheList.stream().filter(m -> {
                            if (m.containsKey(f.getFieldName())) {
                                Object v = m.get(f.getFieldName());
                                if (v != null && v.toString().contains(f.getValue().toString())) {
                                    return true;
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                    }
                }
            }

            int recordAmount = cacheList.size();
            if (recordAmount == 0) {
                return new ArrayList<Map<String, Object>>();
            }

            // 根据总数据条数初始化分页DTO
            page.initPage(recordAmount);

            List<List<Map<String, Object>>> pagedList =
                    ListUtils.partition(cacheList, page.getPageSize());

            return pagedList.get(page.getCurrentPage() - 1);

        } catch (Exception e) {
            logger.error("Exption parsing demo data list.", e);
        }

        return new ArrayList<Map<String, Object>>();
    }

    @SuppressWarnings("deprecation")
    private static String parseCellToString(Cell cell) {

        if (cell == null) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {

            case STRING:
                return cell.getRichStringCellValue().getString();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return DateUtils.date2String(cell.getDateCellValue());
                } else {
                    return "" + cell.getNumericCellValue();
                }

            case BOOLEAN:
                return "" + cell.getBooleanCellValue();

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
                return "";

            default:
                return cell.getStringCellValue();
        }
    }

    private static String removeZero(String defaultValue) {

        if (StringUtils.isEmpty(defaultValue)) {
            return null;
        }
        if (defaultValue.length() > 2 && ".0"
                .equals(defaultValue.substring(defaultValue.length() - 2, defaultValue.length()))) {
            // excel取出的值为1.0，截去后面的.0
            defaultValue = defaultValue.substring(0, defaultValue.length() - 2);
        }
        return defaultValue;
    }
}
