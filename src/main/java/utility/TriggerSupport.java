package utility;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.inject.spi.BeanManager;

import triggerCreator.JobExecutable;
import annotation.TriggerHandler;
import fire.Fire;

/**
 * Classe contenitore per poter creare i trigger e lanciare l'evento {@link Fire}
 * 
 * @author marco
 */

public class TriggerSupport implements Serializable {

    private static final long serialVersionUID = 3573108510687191782L;

    private TriggerHandler annotation;
    private Set<Annotation> qualifiers;
    private BeanManager manager;
    private JobExecutable jobInstance;

    public TriggerSupport() {
        super();
    }

    private TriggerSupport(Builder builder) {
        this.annotation = (TriggerHandler) builder.annotation;
        this.qualifiers = builder.qualifiers;
        this.manager = builder.manager;
        this.jobInstance = builder.jobInstance;
    }
    
    public String jobName() {
        return getJobInstance().getName();
    }

    public static class Builder {

        private Annotation annotation;
        private Set<Annotation> qualifiers;
        private BeanManager manager;
        private JobExecutable jobInstance;

        public Builder() {

        }

        public Builder whithAnnotation(Annotation ann) {
            annotation = ann;
            return this;
        }

        public Builder storeQualifier(Set<Annotation> set) {
            qualifiers = set;
            return this;
        }

        public Builder forJob(JobExecutable job) {
            jobInstance = job;
            return this;
        }

        public Builder useBeanManager(BeanManager bm) {
            manager = bm;
            return this;
        }

        public TriggerSupport build() {
            return new TriggerSupport(this);
        }
    }

    @Override
    public String toString() {
        return "TriggerSupport [annotation=" + annotation.toString() + ", qualifiers=" + qualifiers.size() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((annotation == null) ? 0 : annotation.hashCode());
        result = prime * result + ((jobInstance == null) ? 0 : jobInstance.hashCode());
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
        result = prime * result + ((qualifiers == null) ? 0 : qualifiers.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TriggerSupport other = (TriggerSupport) obj;
        if (annotation == null) {
            if (other.annotation != null)
                return false;
        } else if (!annotation.equals(other.annotation))
            return false;
        if (jobInstance == null) {
            if (other.jobInstance != null)
                return false;
        } else if (!jobInstance.equals(other.jobInstance))
            return false;
        if (manager == null) {
            if (other.manager != null)
                return false;
        } else if (!manager.equals(other.manager))
            return false;
        if (qualifiers == null) {
            if (other.qualifiers != null)
                return false;
        } else if (!qualifiers.equals(other.qualifiers))
            return false;
        return true;
    }
    


    public TriggerHandler getAnnotation() {
        return annotation;
    }

    public Set<Annotation> getQualifiers() {
        return qualifiers;
    }

    public BeanManager getManager() {
        return manager;
    }

    public JobExecutable getJobInstance() {
        return jobInstance;
    }

 
}
