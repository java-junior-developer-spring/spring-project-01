package com.itmo.springproject01.info.profiles;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!default") // бин будет создан для любых профилей, кроме default
@Service
@ConfigurationProperties(prefix = "inject")
public class ServiceToInject {
    @Value("${service.title}")
    private String titleFromProperties;
    @Value("${service.number}")
    private int numberFromProperties;
    private String fromPropertiesByName;
    private Runnable runnable;


    @Autowired
    public ServiceToInject(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setFromPropertiesByName(String fromPropertiesByName) {
        this.fromPropertiesByName = fromPropertiesByName;
    }
}
