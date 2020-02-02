allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        kotlinx()
        jitpack()
        ktor()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}