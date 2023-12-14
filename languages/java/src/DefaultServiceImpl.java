public class DefaultServiceImpl implements ServiceInterface {

    @Override
    public ServiceResult executeRequest() {
        return ServiceResult.createServiceResult(
                String.format("Default service result from %s", this.getClass().getName())
        );
    }
}
