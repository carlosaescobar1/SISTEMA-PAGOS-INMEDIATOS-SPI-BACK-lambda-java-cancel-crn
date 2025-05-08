package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusDirectoryEnum {

    /**
     * Indicate the status directory on AVAL
     */
    STATUS_AVAL_DIR("DIRAVAL"),

    /**
     * Indicate the status directory on FEDERATE
     */
    STATUS_FEDERATE_DIR("DIFE"),

    /**
     * Indicate the status directory on CENTRALIZED
     */
    STATUS_CENTRALIZED_DIR("DICE"),

    ;


    private final String value;
}