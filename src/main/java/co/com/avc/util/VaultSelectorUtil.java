package co.com.avc.util;

import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.parameter.ParamActiveVault;
import co.com.avc.models.parameter.ParamVaultUpload;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VaultSelectorUtil {

    /**
     *
     */
    private final ParamActiveVault paramActiveVault;

    public ParamVaultUpload selectorVault() {

        ParamVaultUpload paramVaultUpload = paramActiveVault.getVaultsUpload().stream()
                .filter(data -> data.getVaultName().equalsIgnoreCase(ConstantsEnum.REDEBAN_PERSON.getValue()))
                .findFirst().orElse(null);
 
        return paramVaultUpload;
    }

}
