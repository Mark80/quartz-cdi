package quartzExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import triggerCreator.JobExecutable;
import utility.TriggerSupport;
import annotation.TriggerHandler;
import context.QuartzContext;
import fire.BaseScheduler;

/**
 * Classe che schedula {@link BaseScheduler}
 * 
 * @author marco
 */

@Named
@ApplicationScoped
public class ScheduledJob {

    private final List<TriggerSupport> scheduledNow = new ArrayList<TriggerSupport>();
    private final List<TriggerSupport> scheduledSpot = new ArrayList<TriggerSupport>();
    private final Scheduler scheduler;
    private final TriggerCreator triggerCreator;
    private final JobDetailCreator jobDetailCreator;

    public ScheduledJob(Scheduler sheduler,TriggerCreator triggerCreator,JobDetailCreator jobDetailCreator) throws SchedulerException {
        super();
        this.scheduler = sheduler;
        this.triggerCreator=triggerCreator;
        this.jobDetailCreator=jobDetailCreator;
    }

    public void startScheduling(QuartzContext context, EntityManager em) throws SchedulerException {
        scheduler.start();
        final List<String> nomi = getJobNameStoredOnDB(em);
        for (TriggerSupport triggerSupport : context.getContext()) {
            if (isSchedulableNow(triggerSupport, nomi)) {
                scheduledNow.add(triggerSupport);
                schedulaJob(triggerSupport);
            } else {
                scheduledSpot.add(triggerSupport);
            }
        }
    }

    private void schedulaJob(TriggerSupport triggerSupport) throws SchedulerException {      
        final JobDetail job = jobDetailCreator.createJobDetailFromTriggerSupport(triggerSupport);
        final Trigger trigger = triggerCreator.createQuartzTrigger(job,triggerSupport);
        scheduler.scheduleJob(job, trigger);
    }

    private boolean isSchedulableNow(TriggerSupport triggerSupport, Collection<String> nomi) {
        TriggerHandler annotation = triggerSupport.getAnnotation();
        return annotation.attivo() && !nomi.contains(triggerSupport.jobName());
    }

    @SuppressWarnings("unchecked")
    private List<String> getJobNameStoredOnDB(EntityManager em) {
        Query query = em.createNativeQuery("select job_name from qrtz_job_details");
        final List<String> nameOfJobsOnDB = (List<String>) query.getResultList();
        return nameOfJobsOnDB;
    }

    public void fireJobSpot() throws SchedulerException {
        TriggerSupport trigger = getSchedulerSpot();
        JobExecutable esecuzioneSpot = trigger.getJobInstance();
        esecuzioneSpot.execute(null);
    }

    private TriggerSupport getSchedulerSpot() {
        final String nameofJobSpot = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name");
        TriggerSupport result= getTriggerSupportFromSchedulerSpot(nameofJobSpot);
        return result;
    }

    private TriggerSupport getTriggerSupportFromSchedulerSpot(String nameofJobSpot) {
        TriggerSupport result = null;
        for (TriggerSupport triggerSupport : scheduledSpot) {
            if (triggerSupport.jobName().equals(nameofJobSpot)) {
                result = triggerSupport;
            }
        }
        return result;
    }

    public List<TriggerSupport> getScheduledNow() {
        return scheduledNow;
    }

    public List<TriggerSupport> getScheduledSpot() {
        return scheduledSpot;
    }

}
