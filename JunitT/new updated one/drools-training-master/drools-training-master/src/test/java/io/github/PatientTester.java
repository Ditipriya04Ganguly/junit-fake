package io.github;


import model.Patient;
import model.PatientDisease;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

import java.time.LocalDate;
import java.util.*;

public class PatientTester {
    static Map<Integer,Patientd> map= new HashMap<>();



    static  {

        map.put(1,new Patientd(Arrays.asList(
                new Patient(1,"R13.1", "Active",LocalDate.of(2022,06,30), LocalDate.of(2000,9,04))),
                new MapCreator(Arrays.asList(new Pair(1, new HashSet<String>(Arrays.asList("Dysphagia"))))),
                null));
        map.put(2,new Patientd(Arrays.asList(
                new Patient(2,"R63.4", "Active",LocalDate.of(2022,06,30), LocalDate.of(2000,9,04))),
                new MapCreator(Arrays.asList(new Pair(2, new HashSet<String>(Arrays.asList("Weight Loss"))))),
                null));
    }


    @Test
    public void testForPatientDisease() {
        PatientTester pt= new PatientTester();
        PatientDisease patientDisease = new PatientDisease();
        KieSession ksession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("diseaserules");
        for (int tid : map.keySet()) {
            ksession.insert(map.get(tid).pt);
            ksession.insert(LocalDate.now());
            ksession.insert(patientDisease);
            ksession.fireAllRules();
            ksession.dispose();

            pt.validatorDisease(patientDisease, map.get(tid).dismp, map.get(tid).riskmp);

        }

    }

    public void validatorDisease(PatientDisease pd,MapCreator disease, MapCreator risk){
        for (Patient patient: pd.getMaplist().keySet()){
            if (patient.getId()==disease.map)
            assert(pd.getMaplist().get(patient).containsAll(disease.map.get(patient.getId())));
            //assert pd.getMaplist().get()

        }
    }
}
