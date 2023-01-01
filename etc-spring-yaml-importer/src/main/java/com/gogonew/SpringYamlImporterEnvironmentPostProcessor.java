package com.gogonew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

@Slf4j
public class SpringYamlImporterEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    static final String[] DEFAULT_LOADING = {"application-common.yml", "application-common.yaml"};

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String[] activeProfiles = environment.getActiveProfiles();
        Assert.notEmpty(activeProfiles, "Profiles should not empty");
        final String activeProfile = activeProfiles[0];
        final String resourcesFilePath = "classpath*:*-" + activeProfile;

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        try {
            ArrayList<Resource> resourceList = new ArrayList<Resource>();
            for (String dl : DEFAULT_LOADING) {
                resourceList.addAll(Arrays.stream(resourcePatternResolver.getResources(dl)).toList());
            }
            Resource[] resources1 = resourcePatternResolver.getResources(resourcesFilePath + ".yml");
            resourceList.addAll(Arrays.stream(resources1).toList());
            Resource[] resources2 = resourcePatternResolver.getResources(resourcesFilePath + ".yaml");
            resourceList.addAll(Arrays.stream(resources2).toList());
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            resourceList.forEach(r -> {
                List<PropertySource<?>> result = null;
                try {
                    if (r.exists()) {
                        result = loader.load(r.getDescription(), r);
                    }
                } catch (IOException e) {
                    log.error("err loading of " + r.getDescription());
                    throw new IllegalStateException("err loading of " + r.getDescription(), e);
                }
                if (result != null) {
                    result.forEach(environment.getPropertySources()::addLast);
                }
            });
        } catch (IOException e) {
            log.error("err loading of yaml files of " + resourcesFilePath);
            throw new IllegalStateException("err loading of yaml files of " + resourcesFilePath, e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}