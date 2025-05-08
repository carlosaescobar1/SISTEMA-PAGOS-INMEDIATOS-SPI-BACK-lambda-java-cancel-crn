package co.com.avc.models.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(VaultServicesTimeOut.class)
public class VaultServicesTimeOut {

    private int redEnrollmentTimeOut;
}
