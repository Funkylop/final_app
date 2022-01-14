package com.hramyko.finalapp.service.validator;

import com.hramyko.finalapp.service.util.ErrorMessage;
import com.hramyko.finalapp.service.util.ValidValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GameValidator {

    public void validateId(int id) {
        if (id < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    public void validateTitle(String title) {
        if (StringUtils.isBlank(title)) {
            parametersError(ErrorMessage.PARAMETER_TITLE);
        }
    }

    private void parametersError(String messageError) {
        throw new RuntimeException("Error in validating the parameters of the game" + messageError);
    }
}
