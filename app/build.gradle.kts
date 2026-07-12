plugins {
  application
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  implementation(libs.guava)
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

application {
  mainClass = "omerpeled.collegemgmt.Main"
  tasks.named<JavaExec>("run") {
    standardInput = System.`in`
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
