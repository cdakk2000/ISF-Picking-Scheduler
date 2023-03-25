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

    public void loadStore(InputStream is) throws Exception {
//        Scanner s = new Scanner(is).useDelimiter("\\A");
//        String result = s.hasNext() ? s.next() : "";
        JSONObject jsonObj;
        try {
            BufferedReader bR = new BufferedReader(new InputStreamReader(is));
            String line = "";

            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                responseStrBuilder.append(line);
            }
            jsonObj = new JSONObject(responseStrBuilder.toString());
            List<Picker> pickers = new ArrayList<>();
            //for (int i = 0; i < jsonObj.length(); i++) {
            // JSONObject jsonObj = result.getJSONObject(i);
            LocalTime pickingStartTime = LocalTime.parse(jsonObj.getString("pickingStartTime"));
            LocalTime pickingEndTime = LocalTime.parse(jsonObj.getString("pickingEndTime"));
            setPickingStartTime(pickingStartTime);
            setPickingEndTime(pickingEndTime);

            JSONArray pickersJSON = jsonObj.getJSONArray("pickers");
            for (int j = 0; j < pickersJSON.length(); j++) {
                pickers.add(new Picker(pickersJSON.get(j).toString(), getWorkingHours(), pickingStartTime));

            }

            //}
            setPickers(pickers);

            //}
//            is.close();
//            bR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
