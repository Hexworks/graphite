plugins {
    kotlinMultiplatform
}

group = "org.hexworks.graphite"

kotlin {

    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    js {
        browser()
    }

    dependencies {

        with(Projects) {
            commonMainApi(graphiteCore)
        }
    }
}