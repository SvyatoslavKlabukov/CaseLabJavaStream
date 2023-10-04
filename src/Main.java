import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Developer dev1 = new Developer("Наташа", Arrays.asList("Java", "C++"));
        Developer dev2 = new Developer("Эрнест", Arrays.asList("Java", "Python"));
        Developer dev3 = new Developer("Элла", Arrays.asList("С#", "Python", "JavaScript"));
        Developer dev4 = new Developer("Иван", Arrays.asList("Java", "Python", "Fortran"));
        Stream<Developer> developerStream = Stream.of(dev1, dev2, dev3, dev4);


        developerStream
                .flatMap(d -> d.getLanguages().stream().map(l -> new DeveloperWithLanguage(d.getName(), l)))
                .reduce(
                        new HashMap<String, ArrayList<String>>(), // language -> список имён
                        (res, devWithLang) -> {
                            ArrayList<String> devNames = res.get(devWithLang.language);
                            if (devNames == null || devNames.size() == 0) {
                                ArrayList<String> nameArr = new ArrayList<String>();
                                nameArr.add(devWithLang.name);
                                res.put(devWithLang.language, nameArr);
                            } else {
                                devNames.add(devWithLang.name);
                            }
                            return res;
                        },
                        (res1, res2) -> res1
                )
                .values()
                .stream()
                .filter(names -> names.size() == 1)
                .map(names -> names.get(0))
                .distinct()
                .forEach(System.out::println);
    }
}