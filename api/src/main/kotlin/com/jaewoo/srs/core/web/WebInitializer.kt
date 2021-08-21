package com.jaewoo.srs.core.web

import com.jaewoo.srs.core.config.WebServiceConfig
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import javax.servlet.ServletContext
import javax.servlet.ServletRegistration


class WebInitializer : WebApplicationInitializer {
    override fun onStartup(servletContext: ServletContext) {
        val context = AnnotationConfigWebApplicationContext()
        context.register(WebServiceConfig::class.java)
        servletContext.addListener(ContextLoaderListener(context));
        
        val dispatcher: ServletRegistration.Dynamic = servletContext.addServlet("dispatcher", CXFServlet())
        dispatcher.addMapping("/services");
    }
}