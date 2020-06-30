package andrii.romanyshyn.shopsoftserve.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

import static andrii.romanyshyn.shopsoftserve._4_Service.FileService.IMG_DIR;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class StaticResourcesConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations(Paths.get(IMG_DIR).toUri().toString());
    }
}
