package context;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessObserverMethod;

import annotation.TriggerHandler;

import triggerCreator.JobExecutable;
import utility.BeanUtils;
import utility.TriggerSupport;
import fire.Fire;

/**
 * 
 * Classe creator per la generazione del {@link QuartzContext}
 * 
 * @author marco
 */

public final class QuartzContextCreator {

    private QuartzContextCreator() {
    }

    public final static QuartzContext creatContextOfJob(final BeanManager beanManager,
            final Set<ProcessObserverMethod<Fire, ? extends JobExecutable>> allObserverMeth) {
        final Set<TriggerSupport> triggerSupports = new HashSet<TriggerSupport>();
        for (ProcessObserverMethod<Fire, ? extends JobExecutable> pom : allObserverMeth) {
            final Set<Annotation> qualifiers = getQualifierOfObserverMethod(pom);
            final Annotation annotation = BeanUtils.getAnnotation(qualifiers, TriggerHandler.class);
            final JobExecutable job = BeanUtils.getInstanceByType(beanManager, getJobClassForExecution(pom));
            final TriggerSupport triggerSupport = new TriggerSupport.Builder().forJob(job).storeQualifier(qualifiers)
                    .whithAnnotation(annotation).useBeanManager(beanManager).build();
            triggerSupports.add(triggerSupport);
        }
        return new QuartzContextImpl(triggerSupports);
    }

    private static Class<? extends JobExecutable> getJobClassForExecution(ProcessObserverMethod<Fire, ? extends JobExecutable> pom) {
        final AnnotatedType<? extends JobExecutable> type = pom.getAnnotatedMethod().getDeclaringType();
        final Class<? extends JobExecutable> jobClass = type.getJavaClass();
        return jobClass;
    }

    private static Set<Annotation> getQualifierOfObserverMethod(ProcessObserverMethod<Fire, ? extends JobExecutable> pom) {
        final ObserverMethod<Fire> method = pom.getObserverMethod();
        final Set<Annotation> qualifiers = method.getObservedQualifiers();
        return qualifiers;
    }
}
