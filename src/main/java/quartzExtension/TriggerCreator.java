package quartzExtension;

import static triggerCreator.TriggerBuilder.newTriggerCustom;

import java.lang.annotation.Annotation;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import utility.TriggerSupport;

class TriggerCreator {

    Trigger createQuartzTrigger(JobDetail job,TriggerSupport triggerSupport) {
        Annotation annotation=triggerSupport.getAnnotation();
        String nameJob=triggerSupport.jobName();
        final Trigger trigger = newTriggerCustom().forJob(job).whithAnnotation(annotation).whithName(nameJob).create();
        return trigger;
    }

}
