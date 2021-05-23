import org.asciidoctor.gradle.AsciidoctorTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("com.palantir.docker") version "0.26.0"

    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.4.31"
    kotlin("kapt") version "1.4.31"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

group = "com.jaewoo"
version = "0.0.6-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val jjwtVersion = "0.10.7"
val swaggerVersion = "3.0.0"
val queryDslVersion = "4.2.1"

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val profile = if (project.hasProperty("profile")) project.property("profile") else "local"

sourceSets {
    main {
        resources {
            srcDirs(listOf("src/main/resources", "src/main/resources-$profile"))
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-test")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    // Swagger (API Document)
    implementation("io.springfox:springfox-boot-starter:$swaggerVersion")
    implementation("io.springfox:springfox-swagger-ui:$swaggerVersion")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Query DSL
    implementation("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jpa")
    annotationProcessor(
        group = "com.querydsl", name = "querydsl-apt", classifier = "jpa"
    )

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")

    // p6spy:p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.6.2")

    // mariadb
    implementation("org.mariadb.jdbc:mariadb-java-client:2.4.1")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

var snippetsDir = file("build/generated-snippets")

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.bootJar {
    layered {
        isEnabled = false
    }
}

//task<Copy>("unpack") {
//    val bootJar = tasks.getByName<BootJar>("bootJar")
//    dependsOn(bootJar)
//    from(zipTree(bootJar.outputs.files.singleFile))
//    into("build/dependency")
//}
//
//docker {
//    val archiveBaseName = tasks.getByName<BootJar>("bootJar").archiveBaseName.get()
//    name = "${project.group}/$archiveBaseName"
//    copySpec.from(tasks.getByName<Copy>("unpack").outputs).into("dependency")
//    buildArgs(mapOf("DEPENDENCY" to "dependency"))
//}

// Gradle Docker plugin configuration
// Please make sure you login to Docker registry before running Docker related tasks
docker {
    // All the build process should be passed before we run Docker related tasks
    dependsOn(tasks.getByName("build"))

    val bootJar: BootJar by tasks
    val archiveBaseName = bootJar.archiveBaseName.get()

    // Please specify the image metadata here
    name = "${project.group}/$archiveBaseName:${project.version}"

    // Please add the tags if you need more registries/userNames/tags.
    // Accordingly this plugin will create a corresponding task to tag/push it.
    //
    // By default, the registry to which it will push when you run "dockerPush" task is "docker.io".
    // So practically the following are not needed.
    //
    // val registry = "docker.io"
    // tag("DockerIO", "$registry/$name")

    // Set the path to Dockerfile
    setDockerfile(file("Dockerfile"))

    // Add the built jar file to Docker's build context
    files(bootJar.archiveFile)

    // Set buildArgs of Dockerfile
    buildArgs(mapOf(
        "JAR_FILE" to bootJar.archiveFileName.get()
    ))
}

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
}

tasks.withType<AsciidoctorTask> {
    inputs.dir(snippetsDir)
    val test by tasks
    dependsOn(test)
}