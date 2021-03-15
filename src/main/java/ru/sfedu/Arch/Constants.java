package ru.sfedu.Arch;

public class Constants {
    public static final String CUSTOM_CONFIG_PATH = "environmentFile";
    public static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";

    public static final String DEFAULT_HIBERNATE_CONFIG_PATH = "./src/main/resources/hibernate.cfg.xml";
    public static final String PROPERTY_CUSTOM_HIBERNATE_CONFIG_PATH = "hibernateFile";


    /*****         SQL query          *****/

    public static final String SQL_ALL_SCHEMAS = "SELECT schema_name FROM information_schema.schemata";
    public static final String SQL_ALL_TABLES = "SELECT TABLE_NAME FROM information_schema.TABLES";
    public static final String SQL_ALL_DOMAINS = "SELECT DOMAIN_NAME FROM information_schema.DOMAINS";
    public static final String SQL_ALL_PRIVILEGES = "SELECT TABLE_NAME FROM information_schema.TABLE_PRIVILEGES";
    public static final String EMPTY_VALUE = "";


    /*****       Table names         *****/

    public static final String TABLE_TEST_ENTITY = "test_entity";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "entityName";
    public static final String FIELD_DESCRIPTION = "description_data";
    public static final String FIELD_DATE_CREATED = "date_created";
    public static final String FIELD_CHECK = "is_check";


    public static final String FIELD_SPECIALIZATION_NAME = "specialization_name";
    public static final String FIELD_SPECIALIZATION_YEARS = "specialization_years";
    public static final String FIELD_SPECIALIZATION_LEVEL = "specialization_level";

    public static final String FIELD_PRESENTATION_ID = "presentation_id";
    public static final String FIELD_TEXT = "text";
    public static final String FIELD_DATETIME = "datetime";

    public static final String PARAM_SET = "Set cmd parameter %s: %s";
    public static final String PARAMS = "Cmd Parameters: %s";
    public static final String EQUALS_SIGN = "="
            ;
    public static final String ENTITY_COMMENT = "Comment";
    public static final String TABLE_COMMENTS = "comments";
    public static final String ENTITY_FEEDBACK = "Feedback";
    public static final String ENTITY_FEEDBACK_SINGLE_TABLE = "FeedbackSingleTable";
    public static final String ENTITY_COMMENT_MAPPED_SUPER_CLASS = "CommentMappedSuperClass";
    public static final String ENTITY_COMMENT_TABLE_PER_CLASS = "CommentTablePerClass";
    public static final String ENTITY_COMMENT_SINGLE_TABLE = "CommentSingleTable";
    public static final String ENTITY_COMMENT_JOINED_TABLE = "CommentJoinedTable";
    public static final String TABLE_COMMENTS_JOINED = "comments_joined";
    public static final String ENTITY_FEEDBACK_JOINED_TABLE = "FeedbackJoinedTable";
    public static final String FIELD_STRATEGY = "strategy";
    public static final String FIELD_ROLE = "role";
    public static final String ENTITY_PRESENTATION = "presentation_set_collection";
    public static final String TABLE_PRESENTATION_SET_COLLECTION = "presentation_set_collection";
    public static final String FIELD_FILL_COLOR = "fill_color";
    public static final String FIELD_FONT_FAMILY = "font_family";
    public static final String TABLE_SLIDES = "slides";
    public static final String FIELD_SLIDE_ID = "slide_id";
    public static final String ENTITY_SLIDE = "slide";
    public static final String TABLE_SLIDES_SET_COLLECTION = "slide_set_collection";
    public static final String FIELD_INDEX = "slide_index";
    public static final String ENTITY_SLIDE_SET_COLLECTION = "slide_set_collection";
    public static final String FIELD_SLIDES = "slides";
    public static final String TABLE_PRESENTATION_LIST_COLLECTION = "presentation_list_collection";
    public static final String ENTITY_PRESENTATION_LIST_COLLECTION = "presentation_list_collection";
    public static final String TABLE_SLIDES_LIST_COLLECTION = "slide_list_collection";
    public static final String TABLE_PRESENTATION_MAP_COLLECTION = "presentation_map_collection";
    public static final String ENTITY_PRESENTATION_MAP_COLLECTION = "presentation_map_collection";

    public static final String TABLE_SLIDES_MAP_COLLECTION = "slide_map_collection";
    public static final String FIELD_SLIDE_NAME = "slide_name";
    public static final String ENTITY_SLIDE_MAP_COLLECTION = "slide_map_collection";
    public static final String ENTITY_ASSESSMENT_JOINED_TABLE = "assessment_joined_table";
    public static final String TABLE_ASSESSMENT_JOINED = "assessment_joined_table";
    public static final String FIELD_MARK = "mark";
    public static final String TABLE_ASSESSMENT_MAPPED_SUPER_CLASS = "assessment_mapped_superclass";
    public static final String ENTITY_ASSESSMENT_MAPPED_SUPER_CLASS = "assessment_mapped_superclass";
    public static final String TABLE_ASSESSMENT_SINGE_TABLE = "assessment_single_table";
    public static final String ENTITY_ASSESSMENT_SINGLE_TABLE = "assessment_single_table";
    public static final String TABLE_ASSESSMENT_TABLE_PER_CLASS = "assessment_table_per_class";
    public static final String ENTITY_ASSESSMENT_TABLE_PER_CLASS_TABLE = "assessment_table_per_class";
    public static final String TABLE_COMMENTS_TABLE_PER_CLASS = "comment_table_per_class";
    public static final String ENTITY_FEEDBACK_TABLE_PER_CLASS = "feedback_table_per_class";
    public static final String FIELD_PRESENTATION = "presentation";
    public static final String PRESENTATION_MAIN = "presentation_main";
    public static final String TABLE_PRESENTATION_MAIN = "presentation_main";
    public static final String ENTITY_SLIDE_MAIN = "slide_main";
    public static final String TABLE_ENTITY_SLIDE_MAIN = "slide_main";
}
