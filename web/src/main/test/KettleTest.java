import com.selegant.WebApplication;
import com.selegant.kettle.mapper.XxlJobInfoMapper;
import com.selegant.kettle.model.TableDesc;
import com.selegant.kettle.model.XxlJobInfo;
import com.selegant.kettle.service.TableDescService;
import com.selegant.xxljob.service.XxlJobService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class KettleTest {

    @Autowired
    private TableDescService tableDescService;


    @Autowired
    private XxlJobInfoMapper xxlJobInfoMapper;


    @Test
    public void tableDesc(){
//        final String s = "V_PR_PATIMEDIA\n" +
//                "V_PR_PATIRECORD\n" +
//                "v_pr_patiallergy\n" +
//                "V_CD_CHECKIN\n" +
//                "V_CD_TREATINFO\n" +
//                "v_CD_RECIPELMASTER\n" +
//                "v_CD_RECIPELDETAIL\n" +
//                "v_CD_HOSPITALTREAT\n" +
//                "v_CD_DOCTORADVICE\n" +
//                "v_CD_DOCTORADVICE_EXECUTE\n" +
//                "v_CD_TREATDIAGNOSIS\n" +
//                "v_CD_HOSTREATDIAGNOSIS\n" +
//                "v_treat_record\n" +
//                "v_cd_doctoradvice_checkinfo\n" +
//                "v_FP_OUTCHARGE\n" +
//                "v_FP_OUTCHARGEDETAIL\n" +
//                "v_FP_OUTHOSPBALANCE\n" +
//                "v_FP_HOSCHARGE\n" +
//                "v_FP_HOSCHARGEDETAIL\n" +
//                "v_FP_INHOSPBALANCE\n" +
//                "v_SA_SURGERYREQUEST\n" +
//                "v_rm_schedual\n" +
//                "v_rm_appointment\n" +
//                "V_AB_DEPTDICT\n" +
//                "V_AB_WORKERDICT\n" +
//                "V_ab_diagdict\n" +
//                "v_ab_publicdict\n" +
//                "v_cd_workergroup\n" +
//                "v_cd_worker2hosdept\n" +
//                "v_ab_drugspec\n" +
//                "ab_drugfactory\n" +
//                "ab_drugattrib\n" +
//                "v_ab_orderitem\n" +
//                "v_ab_orderitemsub\n" +
//                "v_ab_orderitemtype\n" +
//                "v_ab_orgoperation\n" +
//                "V_LT_TESTREQUEST\n" +
//                "V_LT_TESTREPMASTER\n" +
//                "V_LT_TESTREPRESULT\n" +
//                "V_LT_TESTMICROBERESULT\n" +
//                "v_lt_testcriticalvalue\n" +
//                "V_LT_TESTEXECUTEINFO\n" +
//                "v_lt_testitem\n" +
//                "v_lt_testtypeitem\n" +
//                "V_IE_EXAMREQUEST\n" +
//                "V_IE_EXAMINERESULT\n" +
//                "v_lt_pathology_report\n" +
//                "V_ER_HOSPITALTREAT\n" +
//                "v_er_consultation\n" +
//                "V_ER_PINGGUBGPICK\n" +
//                "v_er_medical_home_page\n" +
//                "v_er_progress_note\n" +
//                "v_er_emergency_observation\n" +
//                "v_change_hospital_record\n" +
//                "V_change_dept_record\n" +
//                "v_outhospital_record\n" +
//                "v_dead_record\n" +
//                "v_preoperativesummary\n" +
//                "v_SA_SURGERYRECORD\n" +
//                "v_NI_VITALSIGN\n" +
//                "v_ni_execute_record\n" +
//                "v_ni_execute_record\n" +
//                "v_ni_blood_sugar\n" +
//                "V_cn_nursing_record\n" +
//                "v_IN_physicalexam\n" +
//                "v_in_physicaldeptresult\n" +
//                "v_IN_physicalexamresult\n";
//        Set<String> collect = tableDescService.lambdaQuery().select(TableDesc::getTableName).list().stream().map(TableDesc::getTableName).collect(Collectors.toSet());

//        List<String> list = Arrays.stream(s.split("\n")).map(String::toLowerCase).filter(v -> !collect.contains(v)).collect(Collectors.toList());
//        System.out.println("list = " + list);
//        final String trans_cd_worker2hosdept = tableDescService.getTableDesc("trans_cd_worker2hosdept", 1);
//        System.out.println("trans_cd_worker2hosdept = " + trans_cd_worker2hosdept);
        List<XxlJobInfo> xxlJobInfos = xxlJobInfoMapper.selectList(null);
        for (XxlJobInfo xxlJobInfo : xxlJobInfos) {
            xxlJobInfo.setCnDesc(tableDescService.getTableDesc(xxlJobInfo.getJobDesc(), xxlJobInfo.getObjectType()));
            xxlJobInfoMapper.updateById(xxlJobInfo);
        }
    }
}
