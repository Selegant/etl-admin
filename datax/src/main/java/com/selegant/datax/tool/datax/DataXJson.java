package com.selegant.datax.tool.datax;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class DataXJson {


    /**
     * job : {"setting":{"speed":{"channel":3},"errorLimit":{"record":0,"percentage":0.02}},"content":[{"reader":{"name":"oraclereader","parameter":{"username":"nbods","password":"nbods","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"splitPk":"","connection":[{"table":["CD_DOCTORADVICE_EXECUTE"],"jdbcUrl":["jdbc:oracle:thin:@//192.168.10.107:1521/orcl"]}]}},"writer":{"name":"mysqlwriter","parameter":{"writeMode":"replace","username":"root","password":"wowjoy@123","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"preSql":[""],"connection":[{"table":["cd_doctoradvice_execute"],"jdbcUrl":"jdbc:mysql://192.168.10.107:3306/nbods?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC"}]}}}]}
     */

    private JobBean job;

    @NoArgsConstructor
    @Data
    public static class JobBean {
        /**
         * setting : {"speed":{"channel":3},"errorLimit":{"record":0,"percentage":0.02}}
         * content : [{"reader":{"name":"oraclereader","parameter":{"username":"nbods","password":"nbods","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"splitPk":"","connection":[{"table":["CD_DOCTORADVICE_EXECUTE"],"jdbcUrl":["jdbc:oracle:thin:@//192.168.10.107:1521/orcl"]}]}},"writer":{"name":"mysqlwriter","parameter":{"writeMode":"replace","username":"root","password":"wowjoy@123","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"preSql":[""],"connection":[{"table":["cd_doctoradvice_execute"],"jdbcUrl":"jdbc:mysql://192.168.10.107:3306/nbods?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC"}]}}}]
         */

        private SettingBean setting;
        private List<ContentBean> content;

        @NoArgsConstructor
        @Data
        public static class SettingBean {
            /**
             * speed : {"channel":3}
             * errorLimit : {"record":0,"percentage":0.02}
             */

            private SpeedBean speed;
            private ErrorLimitBean errorLimit;

            @NoArgsConstructor
            @Data
            public static class SpeedBean {
                /**
                 * channel : 3
                 */

                private int channel;
            }

            @NoArgsConstructor
            @Data
            public static class ErrorLimitBean {
                /**
                 * record : 0
                 * percentage : 0.02
                 */

                private int record;
                private double percentage;
            }
        }

        @NoArgsConstructor
        @Data
        public static class ContentBean {
            /**
             * reader : {"name":"oraclereader","parameter":{"username":"nbods","password":"nbods","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"splitPk":"","connection":[{"table":["CD_DOCTORADVICE_EXECUTE"],"jdbcUrl":["jdbc:oracle:thin:@//192.168.10.107:1521/orcl"]}]}}
             * writer : {"name":"mysqlwriter","parameter":{"writeMode":"replace","username":"root","password":"wowjoy@123","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"preSql":[""],"connection":[{"table":["cd_doctoradvice_execute"],"jdbcUrl":"jdbc:mysql://192.168.10.107:3306/nbods?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC"}]}}
             */

            private ReaderBean reader;
            private WriterBean writer;

            @NoArgsConstructor
            @Data
            public static class ReaderBean {
                /**
                 * name : oraclereader
                 * parameter : {"username":"nbods","password":"nbods","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"splitPk":"","connection":[{"table":["CD_DOCTORADVICE_EXECUTE"],"jdbcUrl":["jdbc:oracle:thin:@//192.168.10.107:1521/orcl"]}]}
                 */

                private String name;
                private ParameterBean parameter;

                @NoArgsConstructor
                @Data
                public static class ParameterBean {
                    /**
                     * username : nbods
                     * password : nbods
                     * column : ["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"]
                     * splitPk :
                     * connection : [{"table":["CD_DOCTORADVICE_EXECUTE"],"jdbcUrl":["jdbc:oracle:thin:@//192.168.10.107:1521/orcl"]}]
                     */

                    private String username;
                    private String password;
                    private String splitPk;
                    private List<String> column;
                    private List<ConnectionBean> connection;

                    @NoArgsConstructor
                    @Data
                    public static class ConnectionBean {
                        private List<String> table;
                        private List<String> jdbcUrl;
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class WriterBean {
                /**
                 * name : mysqlwriter
                 * parameter : {"writeMode":"replace","username":"root","password":"wowjoy@123","column":["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"],"preSql":[""],"connection":[{"table":["cd_doctoradvice_execute"],"jdbcUrl":"jdbc:mysql://192.168.10.107:3306/nbods?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC"}]}
                 */

                private String name;
                private ParameterBeanX parameter;

                @NoArgsConstructor
                @Data
                public static class ParameterBeanX {
                    /**
                     * writeMode : replace
                     * username : root
                     * password : wowjoy@123
                     * column : ["ID","EXECUTE_NO","DOCADVISE_NO","ORG_CODE","MEDI_RECORD_NO","INPATIENT_SERIAL_NO","INPATIENT_TIMES","ADMINISTRATION_TIME","REQUEST_AMOUNT","EXECUTE_DEPT_CODE","EXECUTE_DEPT_NAME","EXECUTE_USER_CODE","EXECUTE_USER_NAME","NURSE_EXECUTE_TIME","DOCADVISE_GENERATE_TIME","DOCADVISE_REQUEST_NO","DISPENSING_CODE","DISPENSING_NAME","DOCADVISE_DISPENSING_NO","INPAT_WARD_NUM","INPAT_WARD_NAME","CURR_BED_NUM","EXECUTE_UPDATE_TIME","COLLECT_TIME","COLLECT_SERIAL_NO","CREATE_TIME","UPDATE_TIME","REMARK"]
                     * preSql : [""]
                     * connection : [{"table":["cd_doctoradvice_execute"],"jdbcUrl":"jdbc:mysql://192.168.10.107:3306/nbods?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC"}]
                     */

                    private String writeMode;
                    private String username;
                    private String password;
                    private List<String> column;
                    private List<String> preSql;
                    private List<ConnectionBeanX> connection;

                    @NoArgsConstructor
                    @Data
                    public static class ConnectionBeanX {
                        /**
                         * table : ["cd_doctoradvice_execute"]
                         * jdbcUrl : jdbc:mysql://192.168.10.107:3306/nbods?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC
                         */

                        private String jdbcUrl;
                        private List<String> table;
                    }
                }
            }
        }
    }
}
