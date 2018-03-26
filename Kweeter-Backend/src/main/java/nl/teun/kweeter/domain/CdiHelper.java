package nl.teun.kweeter.domain;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class CdiHelper {

    // Nicked from: http://docs.jboss.org/weld/reference/1.1.0.Final/en-US/html_single/#d0e5286

    public static <T> void programmaticInjection(Class clazz, T injectionObject) throws NamingException {

        InitialContext initialContext = new InitialContext();

        Object lookup = initialContext.lookup("java:comp/BeanManager");

        BeanManager beanManager = (BeanManager) lookup;

        AnnotatedType annotatedType = beanManager.createAnnotatedType(clazz);

        InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);

        CreationalContext creationalContext = beanManager.createCreationalContext(null);

        injectionTarget.inject(injectionObject, creationalContext);

        creationalContext.release();

    }

}
