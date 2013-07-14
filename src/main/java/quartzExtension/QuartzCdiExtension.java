package quartzExtension;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.persistence.EntityManager;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import triggerCreator.JobExecutable;
import utility.BeanUtils;
import context.QuartzContextCreator;
import context.QuartzContext;
import fire.Fire;

/**
 * Estensione per la gestione degli scheduler attraverso il CDI <br>
 * Questa estensione consente in pratica di utilizzare @Inject negli scheduler
 * 
 * @author marco
 */

public class QuartzCdiExtension implements Extension {

    private final Set<ProcessObserverMethod<Fire, ? extends JobExecutable>> observerMethod = new HashSet<ProcessObserverMethod<Fire, ? extends JobExecutable>>();

    public void storeObserverMethod(@Observes ProcessObserverMethod<Fire, JobExecutable> pom) {
        System.out.println("PROCESS OBSERVER METHOD-----------------------");
        observerMethod.add(pom);
    }

    public void initScheduler(@Observes AfterDeploymentValidation adv, BeanManager beanManager) throws SchedulerException {
        final QuartzContext context = QuartzContextCreator.creatContextOfJob(beanManager, observerMethod);
        final TriggerCreator triggerCreator=new TriggerCreator();
        final JobDetailCreator jobDetailCreator=new JobDetailCreator();
        final Scheduler quartzScheduler=StdSchedulerFactory.getDefaultScheduler();
        final ScheduledJob schedulazione = new ScheduledJob(quartzScheduler, triggerCreator, jobDetailCreator);
        EntityManager entityManager = BeanUtils.getInstanceByType(beanManager, EntityManager.class);
        schedulazione.startScheduling(context,entityManager);
    }
}