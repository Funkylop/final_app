package com.hramyko.finalapp.service.validator;

import com.hramyko.finalapp.service.util.ErrorMessage;
import com.hramyko.finalapp.service.util.ValidValue;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator {

    public void validateId(int id) {
        if (id < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    public void validateMessage(String message) {
        if (message.length() > ValidValue.MAX_MESSAGE_LENGTH || message.length() < ValidValue.MIN_MESSAGE_LENGTH) {
            parametersError(ErrorMessage.PARAMETER_MESSAGE);
        }
    }

    public void validateMark(int mark) {
        if (mark < ValidValue.MIN_MARK || mark > ValidValue.MAX_MARK) {
            parametersError(ErrorMessage.PARAMETER_MARK);
        }
    }

    private void parametersError(String messageError) {
        throw new RuntimeException("Error in validating the parameters of the game" + messageError);
    }
}