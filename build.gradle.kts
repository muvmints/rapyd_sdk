plugins {
    val kotlinVersion: String by System.getProperties()
    val ksp: String by System.getProperties()
    val micronautPluginVersion: String by System.getProperties()
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("com.google.devtools.ksp") version ksp
    id("io.micronaut.library") version micronautPluginVersion
    id("io.micronaut.aot") version micronautPluginVersion
    `maven-publish`
}

val mavenSecret: String by System.getProperties()
val kotlinVersion = project.properties.get("kotlinVersion")
val appVersion: String by System.getProperties()
val group: String by System.getProperties()
repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "reposiliteRepositoryReleases"
        url = uri("https://maven.ideazlab.co.uk/releases")
        isAllowInsecureProtocol = true
        credentials {
            username = "gradle"
            password = mavenSecret
        }
        authentication {
            create<BasicAuthentication>("basic")
        }
    }
    maven {
        name = "reposiliteRepositorySnapshots"
        url = uri("https://maven.ideazlab.co.uk/snapshots")
        isAllowInsecureProtocol = true
        credentials {
            username = "admin"
            password = mavenSecret
        }
        authentication {
            create<BasicAuthentication>("basic")
        }
    }
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    implementation(platform("com.ideazlab.jeie.muvmints:muvmints-bom:0.1-SNAPSHOT"))

    implementation("io.micrometer:context-propagation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-retry")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("io.micronaut.serde:micronaut-serde-jackson")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito.kotlin:mockito-kotlin")
    testImplementation("org.mockito:mockito-inline")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}


java {
    sourceCompatibility = JavaVersion.toVersion("24")
    targetCompatibility = JavaVersion.toVersion("24")
    withSourcesJar()
}

graalvmNative.toolchainDetection = false

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.ideazlab.jeie.muvmints.rapyd.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                name = "MuvMints Rapdy SDK"
                description = "SDK for Rapyd payment platform API"
                url = "https://www.ideazlab.co.uk"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "justinemina"
                        name = "Justin Emina"
                        email = "Justin.Justin@ideazlab.co.uk"
                    }
                }
                scm {
                    connection = "scm:git@github.com:muvmints/rapdy-kotlin-sdk.git"
                    developerConnection = "scm:git@github.com:muvmints/rapdy-kotlin-sdk.git"
                    url = "https://www.ideazlab.co.uk"
                }
            }
            groupId = group
            artifactId = "rapdy-sdk"
            version = appVersion
            repositories {
                maven {
                    val releasesRepoUrl = "https://maven.ideazlab.co.uk/releases"
                    val snapshotsRepoUrl = "https://maven.ideazlab.co.uk/snapshots"
                    url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
                    credentials {
                        username = "admin"
                        password = mavenSecret
                    }
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}