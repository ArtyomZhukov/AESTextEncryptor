import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch


class Application: App(){
    override val primaryView = AESText::class

    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    launch<Application>(args)
}
