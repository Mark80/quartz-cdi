package context;

import java.io.Serializable;
import java.util.Set;

import utility.TriggerSupport;

/**
 * 
 * Interfaccia che rappresenta l'insieme dei {@link TriggerSupport}
 * 
 * @author marco
 */

public interface QuartzContext extends Serializable {

    public Set<TriggerSupport> getContext();

}
