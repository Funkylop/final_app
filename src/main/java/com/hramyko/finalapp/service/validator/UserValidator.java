package com.hramyko.finalapp.service.validator;

import com.hramyko.finalapp.persistence.entity.Role;
import com.hramyko.finalapp.persistence.entity.Status;
import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.service.util.ErrorMessage;
import com.hramyko.finalapp.service.util.ValidValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public static final String EMAIL_PATTERN = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public static final String NAME_PATTERN = "^([A-Z][a-z]{3,100})";
    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9\\\\d]{8,16}$";

    public void validate(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateName(user.getFirstName());
        validateName(user.getLastName());
    }

    public void validateEmail(String email) {
        if (StringUtils.isBlank(email) || !email.matches(EMAIL_PATTERN) && !"admin".equals(email)) {
            parametersError(ErrorMessage.PARAMETER_EMAIL);
        }
    }

    public void validatePassword(String password) {
        if (StringUtils.isBlank(password) || !password.matches(PASSWORD_PATTERN) || !"admin".equals(password)) {
            parametersError(ErrorMessage.PARAMETER_PASSWORD);
        }
    }

    public void validateId(int id) {
        if (id < ValidValue.MID_ID) {
            parametersError(ErrorMessage.PARAMETER_ID);
        }
    }

    public void validateStatus(String status) {
        if (StringUtils.isBlank(status)) {
            parametersError(ErrorMessage.PARAMETER_STATUS);
        } else {
            try {
                Status.valueOf(status);
            } catch (IllegalArgumentException e) {
                parametersError(ErrorMessage.PARAMETER_STATUS);
            }
        }
    }

    public void validateName(String name) {
        if (StringUtils.isBlank(name) || !name.matches(NAME_PATTERN)) {
            parametersError(ErrorMessage.PARAMETER_NAME);
        }
    }

    public void validateRole(String role) {
        if (StringUtils.isBlank(role)){
            parametersError(ErrorMessage.PARAMETER_ROLE);
        } else {
            try {
                Role.valueOf(role);
            } catch (IllegalArgumentException e) {
                parametersError(ErrorMessage.PARAMETER_ROLE);
            }
        }
    }

    private void parametersError(String messageError) {
        throw new RuntimeException("Error in validating the parameters of the user" + messageError);
    }
}
