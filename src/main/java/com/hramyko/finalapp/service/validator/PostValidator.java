package com.hramyko.finalapp.service.validator;

import com.hramyko.finalapp.persistence.entity.Post;
import com.hramyko.finalapp.service.util.ErrorMessage;
import com.hramyko.finalapp.service.util.ValidValue;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    public void validate(Post post) {
        validatePrice(post.getPrice());
    }

    public void validateGameObjId(int gameObjId) {
        if (gameObjId < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    public void validatePrice(double price) {
        if (price < ValidValue.MIN_PRICE) {
            parametersError(ErrorMessage.PARAMETER_PRICE);
        }
    }

    public void validateId(int id) {
        if (id < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    private void parametersError(String messageError) {
        throw new RuntimeException("Error in validating the parameters of the post" + messageError);
    }
}
