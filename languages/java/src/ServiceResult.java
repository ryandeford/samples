import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class ServiceResult {

    private final UUID id;
    private final Date date;
    private final String message;

    private ServiceResult(UUID id, Date date, String message) {
        this.id = id;
        this.date = date;
        this.message = message;
    }

    public static ServiceResult createServiceResult() {
        return createServiceResult(null);
    }

    public static ServiceResult createServiceResult(String message) {
        return new ServiceResult(
                UUID.randomUUID(),
                Date.from(Instant.now()),
                message
        );
    }

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format(
                "%s: [id: %s, date: %s, message: %s]",
                this.getClass().getSimpleName(),
                this.getId(),
                this.getDate(),
                this.getMessage()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        ServiceResult that = (ServiceResult) o;

        return Objects.equals(this.getId(), that.getId()) &&
                Objects.equals(this.getDate(), that.getDate()) &&
                Objects.equals(this.getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getDate(), this.getMessage());
    }
}
