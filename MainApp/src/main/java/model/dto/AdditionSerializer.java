package model.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.entities.Addition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdditionSerializer extends JsonSerializer<AdditionDto> {
    private static Logger logger = LoggerFactory.getLogger(AdditionSerializer.class);

    @Override
    public void serialize(AdditionDto value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        logger.debug("serialize() : {}, {}, {}", value, gen, serializers);
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("parameter", value.getParameter().toString());
        gen.writeNumberField("value", value.getValue());
        gen.writeNumberField("price", value.getPrice());
        gen.writeNumberField("additionActivationCost", value.getAdditionActivationCost());
        List<String> mandNames = new ArrayList<>(value.getMandatoryOptions().size());
        for (Addition ad : value.getMandatoryOptions()) {
            mandNames.add(ad.getName());
        }
        gen.writeObjectField("mandatoryOptions", mandNames);

        List<String> tariffs = new ArrayList<>(value.getPackages().size());
        for (PackageDto pack : value.getPackages()) {
            tariffs.add(pack.getTariff().getName() + "(" + pack.getTariff().getDescription() + ")");
        }
        gen.writeObjectField("packages", tariffs);

        List<String> incomNames = new ArrayList<>(value.getIncompatibleOptions().size());
        for (AdditionDto ad : value.getIncompatibleOptions()) {
            incomNames.add(ad.getName());
        }
        gen.writeObjectField("incompatibleOptions", incomNames);
        gen.writeEndObject();
    }

    @Override
    public String toString() {
        return "AdditionSerializer{}";
    }
}
