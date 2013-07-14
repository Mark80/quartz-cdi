package fire;

import java.lang.annotation.Annotation;

import javax.ejb.Asynchronous;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import utility.TriggerSupport;

/**
 * Un Job quartz farlocco!!! <br>
 * Viene invocato da quartz quando viene chiamato scheduleJob, poi lancia un evento di tipo {@link Fire}
 * 
 * @author marco
 */
public class BaseScheduler implements Job {

    @Asynchronous
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TriggerSupport dettagli = (TriggerSupport) context.getJobDetail().getJobDataMap().get("dati");
        dettagli.getManager().fireEvent(new Fire(), dettagli.getQualifiers().toArray(new Annotation[] {}));
    }
}
