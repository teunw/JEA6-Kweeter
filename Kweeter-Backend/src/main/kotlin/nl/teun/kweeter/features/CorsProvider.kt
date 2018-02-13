import org.jboss.resteasy.plugins.interceptors.CorsFilter
import javax.ws.rs.core.Feature
import javax.ws.rs.core.FeatureContext
import javax.ws.rs.ext.Provider

@Provider
open class CorsProvider : Feature {

    @Override
    override fun configure(context: FeatureContext): Boolean {
        val corsFilter = CorsFilter()
        corsFilter.getAllowedOrigins().add("*")
        context.register(corsFilter)
        return true
    }
}