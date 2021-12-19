package info.behzadian;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            String sourceFilePath = "C:\\Work\\Workspace\\Mehan\\Java-distance-calculator\\src\\info\\behzadian\\interview-java-staff.json";
            String outputFilePath = "C:\\Work\\Workspace\\Mehan\\Java-distance-calculator\\src\\info\\behzadian\\selected-staff.json";

            //
            // Read all staff data from file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Reader reader = Files.newBufferedReader(Paths.get(sourceFilePath));
            StaffList staffList = gson.fromJson(reader,StaffList.class);

            //
            // Calculate distance for each staff
            for(Staff staff: staffList.getStaff()) {
                staff.setDistance(distance(staff.getLat(), staff.getLng()));
            }

            //
            // find staff with the distance less than or equal to 20,000 meters
            List<Staff> staffInDistance = staffList.getStaff().stream().filter(s-> s.getDistance() <= 20 * 1000).collect(Collectors.toList());

            //
            // print all staff with the distance less than or equal to 20,000 meters to a file
            StaffList selectedStaffList = new StaffList();
            selectedStaffList.setStaff(staffInDistance);
            try (Writer writer = new FileWriter(outputFilePath)) {
                gson.toJson(selectedStaffList, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate distance between two points in latitude and longitude.
     *
     * @returns Distance in Meters
     */
    public static double distance(double staffLat, double staffLng) {
        final int R = 6371; // Radius of the earth

        double officeLat = 35.776346;
        double officeLng = 51.413482;

        double latDistance = Math.toRadians(staffLat - officeLat);
        double lonDistance = Math.toRadians(staffLng - officeLng);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(officeLat)) * Math.cos(Math.toRadians(staffLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;
        return distance;
    }

}
