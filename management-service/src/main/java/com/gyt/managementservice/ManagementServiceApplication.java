package com.gyt.managementservice;

import com.gyt.corepackage.annotations.EnableSecurity;
import com.gyt.corepackage.utils.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage,Paths.Management.ServiceBasePackage})
@EnableJpaAuditing
@EnableSecurity
public class ManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}

}
