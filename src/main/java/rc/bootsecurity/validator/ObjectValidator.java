package rc.bootsecurity.validator;


import org.springframework.stereotype.Service;
import rc.bootsecurity.model.ValidationErrorResponse;
import rc.bootsecurity.model.Violation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ObjectValidator {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public <T> ValidationErrorResponse validate(Object o, Class<T> clazz) {
        ValidationErrorResponse vrlist = new ValidationErrorResponse();
        List<Violation> violationList = new ArrayList<Violation>();
        if (clazz.isInstance(o)) {
            T request = ((T) clazz);
            Set<ConstraintViolation<T>> constraintViolations = validator.validate((T) o);
            for (ConstraintViolation<T> s : constraintViolations) {
                Violation va = new Violation();
                va.setFieldName(s.getPropertyPath().toString());
                va.setMessage(s.getMessage().toString());
                violationList.add(va);
                //System.out.println(s);
            }
            vrlist.setViolations(violationList);
            return vrlist;
            /*if (!constraintViolations.isEmpty()){

                throw new Exception();
            }*/
        } else {
            return null;
        }
    }
}