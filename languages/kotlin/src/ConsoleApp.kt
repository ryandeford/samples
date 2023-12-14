class ConsoleApp {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Starting sample application created in Kotlin")

            val service: ServiceInterface = DefaultServiceImpl()
            val serviceResult = service.executeRequest()

            println("Result: $serviceResult")

        }
    }
}