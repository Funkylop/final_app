package com.hramyko.finalapp.service.validator;

import com.hramyko.finalapp.service.util.ErrorMessage;
import com.hramyko.finalapp.service.util.ValidValue;

public class WaitingListValidator {

    void validateId(int id) {
        if (id < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    private void parametersError(String messageError) {
        throw new RuntimeException("Error in validating the parameters of the user" + messageError);
    }
}
