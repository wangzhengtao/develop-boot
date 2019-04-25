import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
    val repos by extra { listOf("http://maven.aliyun.com/nexus/content/groups/public",
            "https://jcenter.bintray.com/","http://repo1.maven.org/maven2","http://repo.spring.io/milestone") }
    repositories {
        for (u in repos) { maven(u) }
    }
}


plugins{
    val springBootVersion = "2.1.4.RELEASE"
    val kotlinVersion = "1.3.30"
    idea
    kotlin ("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion

    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    `maven-publish`
}



val repos:List<String> by extra
allprojects{
    apply(plugin = "io.spring.dependency-management")

    val springCloudVersion = "Greenwich.RELEASE"//"Finchley.SR3"
    dependencyManagement{
        imports{
            mavenBom("io.spring.platform:platform-bom:Cairo-SR7")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }

    apply(plugin = "kotlin")
    repositories {
        mavenLocal()
        for (u in repos) { maven(u) }
    }


}
subprojects{
    apply(plugin = "idea")
    apply(plugin  = "org.springframework.boot")

    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")

    tasks{
        withType(JavaCompile::class) {
            options.encoding = "UTF-8"
        }

        "bootJar"(BootJar::class){
            enabled = false
        }
        "jar"(Jar::class){
            enabled = true
        }
        "test"(Test::class) {
            failFast = true
            useJUnitPlatform()
            systemProperties["refreshDb"] = true
            systemProperties["spring.jpa.hibernate.ddl-auto"] = "create-drop"
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    val coroutineVersion = "1.2.0"
    val kotlinVersion = "1.3.30"
    dependencies{
        implementation(kotlin("stdlib-jdk8"))
        api("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
        testImplementation("org.springframework.boot:spring-boot-starter-test"){exclude("junit") }
        implementation("org.junit.jupiter:junit-jupiter-api")
//        implementation("org.junit.jupiter:junit-jupiter-engine")
    }

    idea {
        module {
            inheritOutputDirs = false
            outputDir = file("$buildDir/classes/kotlin/main/")
            testOutputDir = file("$buildDir/classes/kotlin/test/")
        }
    }

    noArg{
        annotation("vip.codemonkey.common.annotation.NoArg")
        invokeInitializers = true
    }
}

configure(subprojects.filter{it.name != "sample"}){
    apply(plugin  = "maven-publish")

    tasks{
        create("sourcesJar",Jar::class){
            archiveClassifier.set("sources")
            from(sourceSets.main.get().allJava)
        }

        withType(PublishToMavenLocal::class){
            onlyIf {
                publication == publishing.publications["binaryAndSources"]
            }
        }

        withType(PublishToMavenRepository::class){
            onlyIf {
                (repository == publishing.repositories["hhkj"] &&
                        publication == publishing.publications["binary"])
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("binary") {
                version = "${project.version}"
                from(components["java"])
            }
            create<MavenPublication>("binaryAndSources") {
                from(components["java"])
                artifact(tasks["sourcesJar"])
            }
        }
        repositories {
            maven {
                name = "inner"
                url = uri("http://192.168.1.222:8092/repository/maven-releases/")
                credentials{
                    username = "admin"
                    password = "am@hao1!2"
                }
            }
        }
    }
}



project(":common"){

    dependencies{
        api("com.google.code.gson:gson:2.8.5")
    }

}

project(":data-jpa"){
    val querydslVersion = "4.2.1"
    dependencies{
        compile(project(":common"))

        kapt("javax.persistence:javax.persistence-api")
        kapt("com.querydsl:querydsl-apt:${querydslVersion}:jpa")
        api("org.springframework.boot:spring-boot-starter-data-jpa")
        api("com.querydsl:querydsl-jpa")
        api("org.springframework.boot:spring-boot-starter-test")
        api("org.dbunit:dbunit:2.5.4")
        api("org.apache.ant:ant:1.8.2")
        implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.8")
        api("org.hibernate.validator:hibernate-validator:6.0.13.Final")
        api("com.h2database:h2")
        api("mysql:mysql-connector-java")
    }

}

//project(":data-redis"){
//    dependencies{
//        compile(project(":common"))
//
//        api("org.springframework.boot:spring-boot-starter-data-redis")
//        api("com.alibaba:fastjson:1.2.51")
//        api("redis.clients:jedis:2.9.0")
//        api("com.fasterxml.jackson.core:jackson-core:2.9.8")
//        api("com.fasterxml.jackson.core:jackson-databind:2.9.8")
//        api("com.fasterxml.jackson.core:jackson-annotations:2.9.8")
//    }
//
//}

project(":security-core"){
    dependencies{
        compile(project(":data-jpa"))

        api("org.springframework.cloud:spring-cloud-starter-oauth2")
        api("org.springframework.social:spring-social-config")
        api("org.springframework.social:spring-social-core")
//        api("org.springframework.social:spring-social-autoconfigure")
        api("org.springframework.social:spring-social-security")
        api("org.springframework.social:spring-social-web")
        api("commons-lang:commons-lang")
        api("commons-io:commons-io")
        api("javax:javaee-api:8.0")

    }
}

project(":security-normal"){
    dependencies{
        compile(project(":security-core"))

        implementation("com.squareup.retrofit2:converter-jackson:2.5.0")
        implementation("com.squareup.retrofit2:retrofit:2.5.0")
    }
}

project(":security-oauth"){
    dependencies{
        compile(project(":security-core"))
    }
}