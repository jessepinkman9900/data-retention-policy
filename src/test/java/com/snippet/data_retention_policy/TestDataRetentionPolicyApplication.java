package com.snippet.data_retention_policy;

import org.springframework.boot.SpringApplication;

public class TestDataRetentionPolicyApplication {

	public static void main(String[] args) {
		SpringApplication.from(DataRetentionPolicyApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
