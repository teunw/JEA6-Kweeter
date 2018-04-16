package nl.teun.kweeter.services

import nl.teun.kweeter.controllers.KweetJaxApplication
import javax.ws.rs.ApplicationPath
import javax.ws.rs.Path

typealias DomainClass = Any
typealias ControllerClass = Any

@Deprecated("Maybe use this later")
class FacadeUrlService {

    private val ApplicationClass = KweetJaxApplication::class

    private val classesAndUrls = mutableMapOf<DomainClass, String>()

    fun getApplicationRoot(): String {
        val possibleApplicationPath = this.ApplicationClass.annotations.filterIsInstance<ApplicationPath>()
        if (possibleApplicationPath.size != 1) {
            throw Exception("Invalid amount of @ApplicationPath annotations ${possibleApplicationPath.size}, should be 1 in ${this.ApplicationClass.simpleName}!")
        }
        return possibleApplicationPath[0].value
    }

    fun addHateoasCorrelation(domain: DomainClass, controller: ControllerClass) {
        val possiblePath = controller.javaClass.annotations.iterator().asSequence().filterIsInstance<Path>()
        if (possiblePath.count() != 1) {
            throw Exception("Invalid amount of @Path annotations (${possiblePath.count()}), should be 1 in ${controller::class.simpleName}!")
        }
        val url = "http://localhost:8080/Kweeter-Backend/${this.getApplicationRoot()}/${possiblePath.first()}"
        this.classesAndUrls.put(domain, "")
    }

}