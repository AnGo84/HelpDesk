package ua.helpdesk.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class AppConfig {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/messages/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/*
	 * @Bean(name = "localeResolver")
	 * public LocaleResolver getLocaleResolver()
	 * { CookieLocaleResolver resolver = new CookieLocaleResolver();
	 * resolver.setCookieDomain("vetalAppLocaleCookie");
	 * resolver.setDefaultLocale(new Locale("ru")); // 60 minutes
	 * resolver.setCookieMaxAge(60 * 60); return resolver; }
	 */

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());

		return bean;
	}

}
