import org.example.Picker;
import org.example.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class StoreTest {
    private Store store;

    @BeforeEach
    public void setup() {
        store = new Store();
    }

    @Test
    public void testLoadStore() throws Exception {
        String json = "{\n" +
                "  \"pickingStartTime\": \"08:00\",\n" +
                "  \"pickingEndTime\": \"18:00\",\n" +
                "  \"pickers\": [\n" +
                "    \"picker1\",\n" +
                "    \"picker2\",\n" +
                "    \"picker3\"\n" +
                "  ]\n" +
                "}";

        InputStream stream = new ByteArrayInputStream(json.getBytes());
        store.loadStore(stream);

        LocalTime expectedPickingStartTime = LocalTime.parse("08:00");
        LocalTime expectedPickingEndTime = LocalTime.parse("18:00");
        List<Picker> expectedPickers = Arrays.asList(
                new Picker("picker1", Duration.ofHours(10), LocalTime.of(8, 0)),
                new Picker("picker2", Duration.ofHours(10), LocalTime.of(8, 0)),
                new Picker("picker3", Duration.ofHours(10), LocalTime.of(8, 0))
        );
        List<Picker> actual = Collections.unmodifiableList(store.getPickers());

        Assertions.assertEquals(expectedPickingStartTime, store.getPickingStartTime());
        Assertions.assertEquals(expectedPickingEndTime, store.getPickingEndTime());
        Assertions.assertEquals(expectedPickers, actual);
    }

    @Test
    public void testLoadStoreEmptyPickers() throws Exception {
        String json = "{\n" +
                "  \"pickingStartTime\": \"08:00\",\n" +
                "  \"pickingEndTime\": \"18:00\",\n" +
                "  \"pickers\": []\n" +
                "}";

        InputStream stream = new ByteArrayInputStream(json.getBytes());
        store.loadStore(stream);

        LocalTime expectedPickingStartTime = LocalTime.parse("08:00");
        LocalTime expectedPickingEndTime = LocalTime.parse("18:00");
        List<Picker> expectedPickers = List.of();

        Assertions.assertEquals(expectedPickingStartTime, store.getPickingStartTime());
        Assertions.assertEquals(expectedPickingEndTime, store.getPickingEndTime());
        Assertions.assertEquals(expectedPickers, store.getPickers());
    }

    @Test
    public void testLoadStoreInvalidJson() {
        String invalidJson = "not a valid json";

        InputStream stream = new ByteArrayInputStream(invalidJson.getBytes());

        Assertions.assertThrows(Exception.class, () -> store.loadStore(stream));
    }

}
