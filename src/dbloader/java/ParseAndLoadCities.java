import com.google.common.base.Splitter;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ParseAndLoadCities {
    public static void main(String[] args) throws FileNotFoundException {
        Set<String> cities = new HashSet<>();
        try (Scanner scanner = new Scanner(ParseAndLoadCities.class.getResourceAsStream("/telepulesek.txt"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String city = Splitter.on("\t").splitToList(line).get(1);
                cities.add(city);
            }
        }

        System.out.println(cities.size());
        cities.forEach(System.out::println);
    }
}
