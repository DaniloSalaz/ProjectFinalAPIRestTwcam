package es.uv.twcam.projects.airporject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

@Configuration
public class DateTimeConfig {

	
	@Bean
	public FormattingConversionService conversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
	
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyyMMdd"));
		//registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
	    registrar.registerFormatters(conversionService);
	    
	    return conversionService;
	}
	
}
