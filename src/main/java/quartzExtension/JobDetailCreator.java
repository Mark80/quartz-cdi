package quartzExtension;

import static org.quartz.JobBuilder.newJob;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import utility.TriggerSupport;
import fire.BaseScheduler;

class JobDetailCreator {

     JobDetail createJobDetailFromTriggerSupport(TriggerSupport triggerSup) {
        final JobDataMap map = new JobDataMap();
        map.put("dati", triggerSup);
        final JobDetail job = newJob(BaseScheduler.class).usingJobData(map).withIdentity(triggerSup.jobName()).build();
        return job;
    }   
}
