package com.web.blog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final String uploadImagespath;

	public WebConfig(@Value("${file.profile.upload.directory}") String uploadImagesPath) {
		this.uploadImagespath = uploadImagesPath;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/static/");
		
		List<String> imageFolders = Arrays.asList("profile", "etc");
		
		for(String imageFolder : imageFolders) {
			registry.addResourceHandler("/image/" + imageFolder + "/**")
			.addResourceLocations("file:///" + uploadImagespath + imageFolder + "/")
			.setCachePeriod(3600)
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
		}
	}

}
