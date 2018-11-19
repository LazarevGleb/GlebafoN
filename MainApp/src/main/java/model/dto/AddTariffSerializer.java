package model.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddTariffSerializer extends JsonSerializer<AdditionWithTariffsDto> {
    private static Logger logger = LoggerFactory.getLogger(AddTariffSerializer.class);

    @Override
    public void serialize(AdditionWithTariffsDto value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        logger.debug("serialize() : {}, {}, {}", value, gen, serializers);
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("parameter", value.getParameter().toString());
        gen.writeNumberField("value", value.getValue());
        gen.writeNumberField("price", value.getPrice());
        gen.writeNumberField("additionActivationCost", value.getAdditionActivationCost());

        List<String> tariffs = new ArrayList<>(value.getPackages().size());
        for (PackageDto pack : value.getPackages()) {
            tariffs.add(pack.getTariff().getName() + "(" + pack.getTariff().getDescription() + ")");
        }
        gen.writeObjectField("packages", tariffs);

        List<String> remainedTariffs = new ArrayList<>(value.getRemainedTariffs().size());
        for (TariffDto tar : value.getRemainedTariffs()) {
            remainedTariffs.add(tar.getName() + "(" + tar.getDescription() + ")");
        }
        gen.writeObjectField("remainedTariffs", remainedTariffs);
        gen.writeEndObject();
    }

    @Override
    public String toString() {
        return "AddTariffSerializer{}";
    }
}
