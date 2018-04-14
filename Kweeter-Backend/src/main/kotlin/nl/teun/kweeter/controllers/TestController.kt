package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.EmailService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class TestController {

    @Inject
    private lateinit var mailService: EmailService

    @GET
    @Path("/")
    fun getTest() {
        this.mailService.sendMail("test@test.nl", "test@test.nl", "Hello", "test")
    }
}