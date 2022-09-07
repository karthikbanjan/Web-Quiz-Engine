package engine.model.completion;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CompletionSerializer extends StdSerializer<Completion> {

    public CompletionSerializer() {
        this(null);
    }

    public CompletionSerializer(Class<Completion> t) {
        super(t);
    }

    @Override
    public void serialize(Completion value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getQuiz().getId());
        gen.writeStringField("completedAt", value.getCompletedAt().toString());
        gen.writeEndObject();
    }
}
