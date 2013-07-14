package context;

import java.util.HashSet;
import java.util.Set;

import utility.TriggerSupport;

/**
 * 
 * Implementazione di {@link QuartzContext}
 * 
 * @author marco
 */


 class QuartzContextImpl implements QuartzContext {
     
     private static final long serialVersionUID = -4079942751003555692L;
     private Set<TriggerSupport> context = new HashSet<TriggerSupport>();

    public QuartzContextImpl(Set<TriggerSupport> context) {
        super();
        this.context = context;
    }

    public Set<TriggerSupport> getContext() {
        return context;
    }

}
