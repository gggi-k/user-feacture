package kr.submit.userfeature.core.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.FieldError;

import java.io.IOException;

@JsonComponent
public class FieldErrorComponent {

    public static class Serializer extends JsonSerializer<FieldError> {

        @Override
        public void serialize(FieldError fieldError, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("defaultMessage", fieldError.getDefaultMessage());
            gen.writeObjectField("rejectedValue", fieldError.getRejectedValue());
            gen.writeBooleanField("bindingFailure", fieldError.isBindingFailure());
            gen.writeStringField("objectName", fieldError.getObjectName());
            gen.writeStringField("field", fieldError.getField());
            gen.writeStringField("code", fieldError.getCode());
            gen.writeEndObject();
        }
    }
}