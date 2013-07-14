package annotation;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

/**
 * 
 * Annotazione in cui settare il tempo di schedulazione e se deve essere schedulato o solo esecuzione spot
 * 
 * @author Marco
 */
@Qualifier
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface TriggerHandler {
    
    String startAt();
    boolean fire();
    boolean attivo();
}
