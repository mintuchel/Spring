package springmvc2.typeconverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springmvc2.typeconverter.converter.IntegerToStringConverter;
import springmvc2.typeconverter.converter.IpPortToStringConverter;
import springmvc2.typeconverter.converter.StringToIntegerConverter;
import springmvc2.typeconverter.converter.StringToIpPortConverter;
import springmvc2.typeconverter.formatter.MyNumberFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {

        // Converter 등록
        //registry.addConverter(new StringToIntegerConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        // Formatter 등록
        registry.addFormatter(new MyNumberFormatter());
    }
}
