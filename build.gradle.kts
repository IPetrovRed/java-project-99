plugins {
	application
	id("checkstyle")
	id("jacoco")
	id("io.freefair.lombok") version "8.6"
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.github.ben-manes.versions") version "0.50.0"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

application {
	applicationName = "app"
	mainClass.set("hexlet.code.AppApplication") }


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	runtimeOnly("com.h2database:h2:2.2.224")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.1"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

jacoco {
	toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
	}
}