package ua.helpdesk.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Deprecated
//@Configuration
//@EnableWebMvc
//@EnableTransactionManagement
@ComponentScan(basePackages = "ua.helpdesk")
public class OldAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    SessionFactory sessionFactory;


    /**
     * Configure ViewResolvers to deliver preferred views.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);

    }

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");

        registry.addResourceHandler("/favicon.ico").addResourceLocations("/images/logo_16.ico");

        //registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/musialnote.ico");
        //registry.addResourceHandler("/**/favicon.ico").addResourceLocations("classpath:/static/musicalnote.ico");
    }

    /**
     * Configure Converter to be used.
     * In our example, we need a converter to convert string values[Roles] to UserProfiles in newUser.jsp
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        /*registry.addConverter(objectToServiceConverter);
        registry.addConverter(objectToGroupConverter);
        registry.addConverter(objectToCategoryConverter);
        registry.addConverter(objectToPriorityConverter);
        registry.addConverter(objectToTicketTypeConverter);
        registry.addConverter(objectToTicketStateConverter);
        registry.addConverter(objectToUserConverter);*/
    }

    /**
     * Configure MessageSource to lookup any validation/error message in internationalized property files
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    /**
     * Optional. It's only required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in @PathVaidables argument.
     * It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164], still present in Spring 4.1.7.
     * This is a workaround for this issue.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }

    // for lazy for User.getGroup
    // https://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception
    // https://www.javacodegeeks.com/2012/07/four-solutions-to-lazyinitializationexc_05.html
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenSessionInViewInterceptor osiv = new OpenSessionInViewInterceptor();
        osiv.setSessionFactory(sessionFactory);
        registry.addWebRequestInterceptor(osiv);
    }
}

