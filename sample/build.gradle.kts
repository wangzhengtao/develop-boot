


val querydslVersion = "4.2.1"

repositories{
    mavenLocal()
}

dependencies{
    compile(project(":common"))
    compile(project(":data-jpa"))
    compile(project(":security-normal"))
//    compile(project(":data-redis"))

    kapt("javax.persistence:javax.persistence-api")
    kapt("com.querydsl:querydsl-apt:${querydslVersion}:jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("Aplus:jx-rediscluster:0.0.1-SNAPSHOT")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
    testCompile("org.junit.jupiter:junit-jupiter-api")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

