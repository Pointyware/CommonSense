plugins {

}

tasks.named("dokkaHtmlMultiModule") {
    dependsOn(":dokkaHtmlMultiModule")
}
