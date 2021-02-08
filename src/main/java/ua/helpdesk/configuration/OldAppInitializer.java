package ua.helpdesk.configuration;

public class OldAppInitializer //extends AbstractAnnotationConfigDispatcherServletInitializer
{
    //@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{OldAppConfig.class};
    }

    //@Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    //@Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        super.onStartup(servletContext);
//    }
}
