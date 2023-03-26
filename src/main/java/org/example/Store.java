package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Picker> pickers;
    private LocalTime pickingStartTime;
    private LocalTime pickingEndTime;

    public Store(List<Picker> pickers, LocalTime pickingStartTime, LocalTime pickingEndTime) {
        this.pickers = pickers;
        this.pickingStartTime = pickingStartTime;
        this.pickingEndTime = pickingEndTime;
    }

    public Store() {
    }

    public List<Picker> getPickers() {
        return pickers;
    }

    public LocalTime getPickingStartTime() {
        return pickingStartTime;
    }

    public LocalTime getPickingEndTime() {
        return pickingEndTime;
    }

    public void setPickers(List<Picker> pickers) {
        this.pickers = pickers;
    }

    public void setPickingStartTime(LocalTime pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    public void setPickingEndTime(LocalTime pickingEndTime) {
        this.pickingEndTime = pickingEndTime;
    }

    public Duration getWorkingHours(){
        return Duration.between(getPickingStartTime(), getPickingEndTime());
    }

    @Override
    public String toString() {
        return "Store{" +
                "pickers=" + pickers +
                ", pickingStartTime=" + pickingStartTime +
                ", pickingEndTime=" + pickingEndTime +
                '}';
    }

    // Loads the store data from the given input stream.
    public void loadStore(InputStream is) throws Exception {
        JSONObject jsonObj;
        try {
            // Read the data from the input stream and parse it as a JSON object.
            BufferedReader bR = new BufferedReader(new InputStreamReader(is));
            String line = "";

            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                responseStrBuilder.append(line);
            }
            jsonObj = new JSONObject(responseStrBuilder.toString());

            // Parse the data for pickers and store it in a list.
            List<Picker> pickers = new ArrayList<>();
            LocalTime pickingStartTime = LocalTime.parse(jsonObj.getString("pickingStartTime"));
            LocalTime pickingEndTime = LocalTime.parse(jsonObj.getString("pickingEndTime"));
            setPickingStartTime(pickingStartTime);
            setPickingEndTime(pickingEndTime);

            JSONArray pickersJSON = jsonObj.getJSONArray("pickers");
            for (int j = 0; j < pickersJSON.length(); j++) {
                pickers.add(new Picker(pickersJSON.get(j).toString(), getWorkingHours(), pickingStartTime));

            }

            // Set the list of pickers for the store.
            setPickers(pickers);
        } catch (IOException e) {
            // Print the stack trace if there is an error reading the data.
            e.printStackTrace();
        }
    }
}
