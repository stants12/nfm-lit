package nfm.lit;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GarageManager {
    private static final String GARAGE_FILE = "data/user/garage.json";

    public static void saveOwnedCarIds(List<Integer> ownedCarIds) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(GARAGE_FILE), ownedCarIds);
    }

    public static void loadOwnedCarIds(List<Integer> ownedCarIds) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> loaded = mapper.readValue(new File(GARAGE_FILE), List.class);
        ownedCarIds.clear();
        ownedCarIds.addAll(loaded);
    }
}