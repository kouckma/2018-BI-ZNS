package expertsystemfw.KnowledgeBase;

        import expertsystemfw.UncertaintyModule.UncertaintyModule;

        import java.io.File;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.concurrent.atomic.AtomicInteger;
        import java.util.logging.Level;
        import java.util.logging.Logger;

public class FuzzyDatabase {




    Map<String,Map<String, Trapezoid>> fuzzySets;
    Map<String,Map<String,String>> fuzzyRules;

    public static final String RESULT_SET = "result_set";


    public Map<String,Map<String, Trapezoid>> getFuzzySets(){
        return fuzzySets;
    }
    public Map<String,Map<String, String>> getFuzzyRules(){
        return fuzzyRules;
    }

    public FuzzyDatabase() {
        this.fuzzySets = new HashMap<>();
        fuzzySets.put(RESULT_SET,new HashMap<>());
        this.fuzzyRules = new HashMap<>();
        loadFile();
    }

    private final String filePath = "src" + File.separator + "expertsystemfw" + File.separator + "KnowledgeBase" + File.separator + "Files" + File.separator + "FuzzyRules";

    public void loadFile() {
        Path path = Paths.get(filePath);

        final AtomicInteger conditionedProbLoaded = new AtomicInteger(0);


        try {
            Files.lines(path).forEach(l -> {
                if (l.equals(";")){
                    conditionedProbLoaded.incrementAndGet();
                    return;
                }
                if (conditionedProbLoaded.get() == 0) {
                    parseFuzzySet(l);
                } else if (conditionedProbLoaded.get() == 1) {
                    parseFuzzyRule(l);
                } else if (conditionedProbLoaded.get() == 2) {
                    parseDefuzzySet(l);
                }


            });
        } catch (IOException ex) {
            Logger.getLogger(UncertaintyModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseDefuzzySet(String l) {
        String[] parts = l.trim().split("_");
        String trapezoidName = parts[0];
        fuzzySets.get(RESULT_SET).put(trapezoidName, new Trapezoid(Integer.valueOf(parts[1]),Integer.valueOf(parts[2]),Integer.valueOf(parts[3]),Integer.valueOf(parts[4]),false,false));
    }

    private void parseFuzzyRule(String l) {
        String[] first = l.trim().split("#");
        fuzzyRules.put(first[0],new HashMap<>());
        String[] pairs = first[1].split(":");
        for (String s : pairs) {
            fuzzyRules.get(first[0]).put(s.split("=")[0],s.split("=")[1]);
        }
    }

    private void parseFuzzySet(String l) {
        String[] parts = l.trim().split(":");
        String predicateName = parts[0];
        String[] sets = parts[1].trim().split(";");
        String[] trapezoid;
        for (int i = 0;i<sets.length;i++) {
            trapezoid = sets[i].trim().split("_");
            if (i == 0) {
                fuzzySets.put(predicateName,new HashMap<>());
                fuzzySets.get(predicateName).put(trapezoid[0],new Trapezoid(Integer.valueOf(trapezoid[1]),Integer.valueOf(trapezoid[2]),Integer.MAX_VALUE, Integer.MAX_VALUE,true,false));
                continue;
            }
            if (i==sets.length-1) {
                fuzzySets.get(predicateName).put(trapezoid[0],new Trapezoid(Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.valueOf(trapezoid[1]),Integer.valueOf(trapezoid[2]),false,true));
                continue;
            }
            fuzzySets.get(predicateName).put(trapezoid[0],new Trapezoid(Integer.valueOf(trapezoid[1]),Integer.valueOf(trapezoid[2]),Integer.valueOf(trapezoid[3]),Integer.valueOf(trapezoid[4]),false,false));
        }
    }


}
