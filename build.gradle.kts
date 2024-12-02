plugins {
    id("java")
}

group = "id.medihause"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.keycloak:keycloak-services:26.0.6")
    implementation("org.keycloak:keycloak-server-spi:26.0.6")
    implementation("org.keycloak:keycloak-server-spi-private:26.0.6")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}