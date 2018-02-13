package nl.teun.kweeter.controllers

import javax.servlet.Servlet
import javax.servlet.ServletConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("api")
open class KweetJaxApplication : Application()