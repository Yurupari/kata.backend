plugins {
	java
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
}

group = "com.haiilo"
version = "0.0.1-SNAPSHOT"
description = "Demo for checkout car system \"Coding Kata\""

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val springdocVersion = "2.5.0"
val mapstructVersion = "1.6.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation ("org.mapstruct:mapstruct:${mapstructVersion}")
	implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("com.h2database:h2")
}

jacoco {
	toolVersion = "0.8.12"
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
	finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<JacocoReport> {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
	}
}

tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}
