package nl.teun.kweeter

import java.time.LocalDateTime
import java.util.*
import javax.enterprise.inject.spi.AnnotatedType
import javax.enterprise.inject.spi.BeanManager
import javax.naming.InitialContext


class Utilities {
    companion object {
        fun localDateTimeToJavaDate(ldate:LocalDateTime):Date {
            return ldate.toJavaUtilDate()
        }

        inline fun <reified T> programmaticInjection(clazz: Class<T>, injectionObject: T) {
            val initialContext = InitialContext()
            val lookup = initialContext.lookup("java:comp/BeanManager")
            val beanManager = lookup as BeanManager
            val annotatedType = beanManager.createAnnotatedType<T>(clazz) as AnnotatedType<Any>
            val injectionTarget = beanManager.createInjectionTarget<Any>(annotatedType)
            val creationalContext = beanManager.createCreationalContext<Any>(null)
            injectionTarget.inject(injectionObject, creationalContext)
            creationalContext.release()
        }
    }
}

