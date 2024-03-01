package com.htsml.dutnotif;

import com.htsml.dutnotif.back.notification.NotificationUpdateWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class AppLauncher {
    public static void main(String[] args) {
        String appMode = System.getProperty("appMode");
        if (appMode == null) {
            appMode = "default";
        }

        if (appMode.equals("worker")) {
            runWorkerJobApplication(args);
        } else {
            SpringApplication.run(DutnotifApplication.class, args);
        }
    }

    private static void runWorkerJobApplication(String[] args) {
        ApplicationContext applicationContext =
                new SpringApplicationBuilder(WorkerJobApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);

        applicationContext.getBean(NotificationUpdateWorker.class).updateGroupNotifications();
    }
}
