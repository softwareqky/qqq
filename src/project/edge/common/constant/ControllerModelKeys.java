package project.edge.common.constant;

/**
 * Web的Controller中Model使用到的key。
 * 
 */
public interface ControllerModelKeys {

    /**
     * 对应画面的[t_page]数据，Controller内部使用，JSP不引用，便于子类自定义功能栏按钮
     */
    String PAGES = "pages";

    /**
     * 增删改查的url Map。
     * 
     * 该Map的key由ControllerUrlMapKeys定义，value即对应的URL。
     */
    String URL_MAP = "urlMap";

    /**
     * 画面使用的HTML元素的id Map。
     * 
     * 该Map的key由ControllerIdMapKeys定义，value即元素的id值。
     */
    String ID_MAP = "idMap";

    /**
     * 画面使用的javascript函数的Map。
     * 
     * 该Map的key由ControllerJsMapKeys定义，value形如"FilterUtils.simpleSearch(...)"。
     */
    String JS_MAP = "jsMap";

    /**
     * 下拉项的Map
     */
    String OPTION_MAP = "optionMap";

    /**
     * easyui-combobox使用内嵌的data时使用，保存所有用到的combobox的内嵌data。 对应的是Map<String,
     * String>，key由[t_data_fields].control_info中的dataKey指定，value是combobox使用的data，json表示
     */
    String COMBOBOX_DATA_MAP = "comboboxDataMap";


    // Grid的相关Key --BEGIN--

    /**
     * 画面是双Grid的场合，主Grid的标题
     */
    String GRID_TITLE = "gridTitle";

    /**
     * 一览的默认排序字段名
     */
    String DEFAULT_ORDER = "defaultOrder";

    /**
     * Grid是否分页，不设置时，默认采用分页，对于不需要分页的场合，需要显示设置为false
     */
    String IS_PAGE = "isPage";

    /**
     * Grid是否单选，不设置时，默认多选
     */
    String IS_SINGLE_SELECT = "isSingleSelect";

    /**
     * 普通检索fields列表
     */
    String SEARCH_FIELDS = "searchFields";

    /**
     * 高级检索弹出画面的fields列表
     */
    String ADV_SEARCH_FIELDS = "advSearchFields";

    /**
     * 一览的非固定列fields列表
     */
    String LIST_FIELDS = "listFields";

    /**
     * 一览的固定列fields列表
     */
    String LIST_FROZEN_FIELDS = "listFrozenFields";

    /**
     * 一览双击行的事件处理器
     */
    String DBL_CLICK_ROW_HANDLER = "dblClickRowHandler";

    /**
     * 一览选择行的事件处理器
     */
    String SELECT_HANDLER = "selectHandler";
    
    /**
     * Grid行数据选择事件
     */
    String CLICK_CELL_HANDLER = "clickCellHandler";

    /**
     * 新建弹出画面的fields列表
     */
    String ADD_FIELDS = "addFields";

    /**
     * 修改弹出画面的fields列表
     */
    String EDIT_FIELDS = "editFields";

    /**
     * 查看弹出画面的fields列表
     */
    String VIEW_FIELDS = "viewFields";

    /**
     * 字段编辑弹出画面的fields列表
     */
    String EDIT_DATA_FIELDS = "fieldEditFields";

    /**
     * 批量修改弹出画面的fields列表
     */
    String BATCH_EDIT_FIELDS = "batchEditFields";

    /**
     * 是否包含连续新增时复制的field
     */
    String HAS_COPY_RESERVE = "hasCopyReserve";

    /**
     * field是否分组，影响增改查弹出画面的样式
     */
    String IS_FIELD_GROUPED = "isFieldGrouped";

    /**
     * 是否可以修改数据，影响datagrid的双击弹出修改画面
     */
    String CAN_EDIT_DATA = "canEditData";
    
    /**
     * 查看页是否带有明细Grid
     */
    String IS_VIEW_HASGRID = "isViewHasGrid";

    /**
     * 功能栏按钮列表
     */
    String FUNCTIONS = "functions";

    /**
     * 检索按钮列表
     */
    String SEARCH_FUNCTIONS = "searchFunctions";

    // Grid的相关Key --END--


    // SubGrid的相关Key --BEGIN--

    /**
     * 画面是双Grid的场合，SubGrid的标题
     */
    String SUB_GRID_TITLE = "subGridTitle";

    /**
     * 画面是双Grid的场合，SubGrid的一览的默认排序字段名
     */
    String SUB_GRID_DEFAULT_ORDER = "subGridDefaultOrder";

    /**
     * 画面是双Grid的场合，SubGrid是否分页，不设置时，默认采用分页，对于不需要分页的场合，需要显示设置为false
     */
    String IS_SUB_GRID_PAGE = "isSubGridPage";

    /**
     * 画面是双Grid的场合，SubGrid是否单选，不设置时，默认单选
     */
    String IS_SUB_GRID_SINGLE_SELECT = "isSubGridSingleSelect";

    /**
     * 画面是双Grid的场合，SubGrid的普通检索fields列表
     */
    String SUB_GRID_SEARCH_FIELDS = "subGridSearchFields";

    /**
     * 画面是双Grid的场合，SubGrid的高级检索弹出画面的fields列表
     */
    String SUB_GRID_ADV_SEARCH_FIELDS = "subGridAdvSearchFields";

    /**
     * 画面是双Grid的场合，SubGrid的一览的非固定列fields列表
     */
    String SUB_GRID_LIST_FIELDS = "subGridListFields";

    /**
     * 画面是双Grid的场合，SubGrid的一览的固定列fields列表
     */
    String SUB_GRID_LIST_FROZEN_FIELDS = "subGridListFrozenFields";

    /**
     * 画面是双Grid的场合，SubGrid的一览双击行的事件处理器
     */
    String SUB_GRID_DBL_CLICK_ROW_HANDLER = "subGridDblClickRowHandler";

    /**
     * 画面是双Grid的场合，SubGrid的一览选择行的事件处理器
     */
    String SUB_GRID_SELECT_HANDLER = "subGridSelectHandler";

    /**
     * 画面是双Grid的场合，SubGrid的新建弹出画面的fields列表
     */
    String SUB_GRID_ADD_FIELDS = "subGridAddFields";

    /**
     * 画面是双Grid的场合，SubGrid的修改弹出画面的fields列表
     */
    String SUB_GRID_EDIT_FIELDS = "subGridEditFields";

    /**
     * 画面是双Grid的场合，SubGrid的查看弹出画面的fields列表
     */
    String SUB_GRID_VIEW_FIELDS = "subGridViewFields";

    /**
     * 画面是双Grid的场合，SubGrid的字段编辑弹出画面的fields列表
     */
    String SUB_GRID_EDIT_DATA_FIELDS = "subGridFieldEditFields";

    /**
     * 画面是双Grid的场合，SubGrid的批量修改弹出画面的fields列表
     */
    String SUB_GRID_BATCH_EDIT_FIELDS = "subGridBatchEditFields";

    /**
     * 画面是双Grid的场合，SubGrid的是否包含连续新增时复制的field
     */
    String SUB_GRID_HAS_COPY_RESERVE = "subGridHasCopyReserve";

    /**
     * 画面是双Grid的场合，SubGrid的field是否分组，影响增改查弹出画面的样式
     */
    String IS_SUB_GRID_FIELD_GROUPED = "isSubGridFieldGrouped";

    /**
     * 画面是双Grid的场合，SubGrid的是否可以修改数据，影响datagrid的双击弹出修改画面
     */
    String CAN_SUB_GRID_EDIT_DATA = "canSubGridEditData";

    /**
     * 画面是双Grid的场合，SubGrid的功能栏按钮列表
     */
    String SUB_GRID_FUNCTIONS = "subGridFunctions";

    /**
     * 画面是双Grid的场合，SubGrid的检索按钮列表
     */
    String SUB_GRID_SEARCH_FUNCTIONS = "subGridSearchFunctions";

    /**
     * 画面是双Grid的场合，SubGrid点击行时，刷新MainGrid时使用的检索字段名
     */
    String SUB_GRID_LINKED_FILTER_FIELD_NAME = "subGridLinkedFilterFieldName";

    // SubGrid的相关Key --END--


    // Grid的弹出画面UI布局相关的Key --BEGIN--

    /**
     * 新增弹出画面的宽度
     */
    String ADD_DIALOG_WIDTH = "addDialogWidth";

    /**
     * 新增弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String ADD_DIALOG_CONTROL_COLS = "addDialogControlCols";

    /**
     * 新增弹出画面的最大高度
     */
    String ADD_DIALOG_MAX_HEIGHT = "addDialogMaxHeight";

    /**
     * 修改弹出画面的宽度
     */
    String EDIT_DIALOG_WIDTH = "editDialogWidth";

    /**
     * 修改弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String EDIT_DIALOG_CONTROL_COLS = "editDialogControlCols";

    /**
     * 修改弹出画面的最大高度
     */
    String EDIT_DIALOG_MAX_HEIGHT = "editDialogMaxHeight";

    /**
     * 查看弹出画面的宽度
     */
    String VIEW_DIALOG_WIDTH = "viewDialogWidth";

    /**
     * 查看弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String VIEW_DIALOG_CONTROL_COLS = "viewDialogControlCols";

    /**
     * 查看弹出画面的最大高度
     */
    String VIEW_DIALOG_MAX_HEIGHT = "viewDialogMaxHeight";

    /**
     * 高级检索弹出画面的宽度
     */
    String ADV_SEARCH_DIALOG_WIDTH = "advSearchDialogWidth";

    /**
     * 高级检索弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String ADV_SEARCH_DIALOG_CONTROL_COLS = "advSearchDialogControlCols";

    /**
     * 高级检索弹出画面的最大高度
     */
    String ADV_SEARCH_DIALOG_MAX_HEIGHT = "advSearchDialogMaxHeight";

    // Grid的弹出画面UI布局相关的Key --END--


    // SubGrid的弹出画面UI布局相关的Key --BEGIN--

    /**
     * 画面是双Grid的场合，SubGrid的新增弹出画面的宽度
     */
    String SUB_GRID_ADD_DIALOG_WIDTH = "subGridAddDialogWidth";

    /**
     * 画面是双Grid的场合，SubGrid的新增弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String SUB_GRID_ADD_DIALOG_CONTROL_COLS = "subGridAddDialogControlCols";

    /**
     * 画面是双Grid的场合，SubGrid的新增弹出画面的最大高度
     */
    String SUB_GRID_ADD_DIALOG_MAX_HEIGHT = "subGridAddDialogMaxHeight";

    /**
     * 画面是双Grid的场合，SubGrid的修改弹出画面的宽度
     */
    String SUB_GRID_EDIT_DIALOG_WIDTH = "subGridEditDialogWidth";

    /**
     * 画面是双Grid的场合，SubGrid的修改弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String SUB_GRID_EDIT_DIALOG_CONTROL_COLS = "subGridEditDialogControlCols";

    /**
     * 画面是双Grid的场合，SubGrid的修改弹出画面的最大高度
     */
    String SUB_GRID_EDIT_DIALOG_MAX_HEIGHT = "subGridEditDialogMaxHeight";

    /**
     * 画面是双Grid的场合，SubGrid的查看弹出画面的宽度
     */
    String SUB_GRID_VIEW_DIALOG_WIDTH = "subGridViewDialogWidth";

    /**
     * 画面是双Grid的场合，SubGrid的查看弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String SUB_GRID_VIEW_DIALOG_CONTROL_COLS = "subGridViewDialogControlCols";

    /**
     * 画面是双Grid的场合，SubGrid的查看弹出画面的最大高度
     */
    String SUB_GRID_VIEW_DIALOG_MAX_HEIGHT = "subGridViewDialogMaxHeight";

    /**
     * 画面是双Grid的场合，SubGrid的高级检索弹出画面的宽度
     */
    String SUB_GRID_ADV_SEARCH_DIALOG_WIDTH = "subGridAdvSearchDialogWidth";

    /**
     * 画面是双Grid的场合，SubGrid的高级检索弹出画面中字段输入控件的格栅数(Bootstrap的12格栅)
     */
    String SUB_GRID_ADV_SEARCH_DIALOG_CONTROL_COLS = "subGridAdvSearchDialogControlCols";

    /**
     * 画面是双Grid的场合，SubGrid的高级检索弹出画面的最大高度
     */
    String SUB_GRID_ADV_SEARCH_DIALOG_MAX_HEIGHT = "subGridAdvSearchDialogMaxHeight";

    // SubGrid的弹出画面UI布局相关的Key --END--

    /**
     * <jsp:include/>嵌入js脚本的JSP路径，子类JSP的第1个扩展点
     */
    String INCLUDE_JSP_JS_PATH = "includeJspJsPath";

    /**
     * <jsp:include/>嵌入的隐藏区JSP路径，用来实现子类特有的弹出画面功能，子类JSP的第2个扩展点
     */
    String INCLUDE_JSP_HIDDEN_CONTENT_PATH = "includeJspHiddenContentPath";

    /**
     * 独立的查看和修改画面的数据记录
     */
    String RECORD = "record";

    /**
     * 独立的新建、查看和修改画面的标题
     */
    String PAGE_TITLE = "pageTitle";

    /**
     * 左侧部分的数据类型
     */
    String SIDE_DATA_TYPE = "sideDataType";

    /**
     * 是否包含提交审核弹出界面
     */
    String IS_HAS_FLOWABLE = "isHasFlowable";

    /**
     * 审核相关的taskId
     */
    String FLOWABLE_AUDIT_TASK_ID = "flowableAuditTaskId";

    /**
     * 审核相关的关联ID
     */
    String FLOWABLE_CORRELATE_DATA_ID = "flowableCorrelateDataId";

    /**
     * 审核相关的业务实体
     */
    String FLOWABLE_BUSINESS_ENTITY = "flowableBusinessEntity";
}
