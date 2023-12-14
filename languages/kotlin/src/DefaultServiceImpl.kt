class DefaultServiceImpl : ServiceInterface {
    override fun executeRequest(): ServiceResult {
        return ServiceResult(
            message = String.format("Default service result from %s", this::class.simpleName)
        )
    }
}