package triggerCreator;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.lang.annotation.Annotation;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import annotation.TriggerHandler;

/**
 * 
 * Classe ti utilità che crea un Trigger Quartz prendendo i dati neccessari dall'anotatione {@link TriggerHandler}
 * 
 * @author marco
 */
public final class TriggerBuilder {

    private JobDetail job;
    private Annotation annotation;
    private String nome;

    private TriggerBuilder() {}

    public static TriggerBuilder newTriggerCustom() {
        return new TriggerBuilder();
    }

    public TriggerBuilder forJob(JobDetail job) {
        this.job = job;
        return this;
    }

    public TriggerBuilder whithAnnotation(Annotation annotation) {
        this.annotation = annotation;
        return this;
    }

    public TriggerBuilder whithName(String name) {
        this.nome = name;
        return this;
    }

    public Trigger create() {
        final TriggerHandler datiAnnotation = (TriggerHandler) annotation;
        final boolean fire = datiAnnotation.fire();
        String startAt = datiAnnotation.startAt();
        Trigger trigger = newTrigger().withIdentity(nome).withSchedule(cronSchedule(startAt)).usingJobData("fire", fire)
                .forJob(job).build();
        return trigger;
    }

}
