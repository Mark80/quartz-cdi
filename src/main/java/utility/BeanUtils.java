package utility;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class BeanUtils {

    public final static <T> T getInstanceByType(final BeanManager manager, final Class<T> type) {
        try {
            final Bean<?> bean = manager.resolve(manager.getBeans(type));
            CreationalContext<?> ctx = manager.createCreationalContext(bean);
            if (bean != null) {
                return type.cast(manager.getReference(bean, type, ctx));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public final  static Annotation getAnnotation(final Collection<Annotation> qualifiers, Class<? extends Annotation> annotation) {
       if(annotation==null) {
           throw new IllegalArgumentException("Il parametro <annotation> non può essere null!!!");
       }
        for (Annotation ann : qualifiers) {
            if (annotation.isAssignableFrom(ann.getClass())) {
                return ann;
            }
        }
        return null;
    }
}
