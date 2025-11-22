## Micronaut 4.10.2 Documentation

- [User Guide](https://docs.micronaut.io/4.10.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.10.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.10.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://gradleup.com/shadow/)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
## Feature reactor documentation

- [Micronaut Reactor documentation](https://micronaut-projects.github.io/micronaut-reactor/snapshot/guide/index.html)


## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#nettyHttpClient)


## Feature retry documentation

- [Micronaut Retry documentation](https://docs.micronaut.io/latest/guide/#retry)


## Feature kotest documentation

- [Micronaut Test Kotest5 documentation](https://micronaut-projects.github.io/micronaut-test/latest/guide/#kotest5)

- [https://kotest.io/](https://kotest.io/)


## Feature ksp documentation

- [Micronaut Kotlin Symbol Processing (KSP) documentation](https://docs.micronaut.io/latest/guide/#kotlin)

- [https://kotlinlang.org/docs/ksp-overview.html](https://kotlinlang.org/docs/ksp-overview.html)


## Feature cache-caffeine documentation

- [Micronaut Caffeine Cache documentation](https://micronaut-projects.github.io/micronaut-cache/latest/guide/index.html)

- [https://github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine)


## Feature annotation-api documentation

- [https://jakarta.ee/specifications/annotations/](https://jakarta.ee/specifications/annotations/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


mn create-app --build=gradle_kotlin --jdk=21 --lang=kotlin --test=kotest --features=annotation-api,jackson-databind,retry,cache-caffeine,http-client,reactor-http-client,yaml,ksp,kotlin-extension-functions,logback,reactor com.ideazlab.jeie.muvmints.rapyd.rapyd_sdk
