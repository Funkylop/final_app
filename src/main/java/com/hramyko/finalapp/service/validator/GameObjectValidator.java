package com.hramyko.finalapp.service.validator;

import com.hramyko.finalapp.persistence.entity.GameObjectStatus;
import com.hramyko.finalapp.service.util.ErrorMessage;
import com.hramyko.finalapp.service.util.ValidValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GameObjectValidator {

    public void validateId(int id) {
        if (id < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    public void validateTextField(String title) {
       if (StringUtils.isBlank(title)) {
           parametersError(ErrorMessage.PARAMETER_TITLE);
       }
    }

    public void validateStatus(String status) {
        if (StringUtils.isBlank(status)) {
            parametersError(ErrorMessage.PARAMETER_STATUS);
        } else {
            try {
                GameObjectStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                parametersError(ErrorMessage.PARAMETER_STATUS);

            }
        }
    }

    private void parametersError(String messageError) {
        throw new RuntimeException ("Error in validating the parameters of the gameObject"  + messageError);
    }
}
