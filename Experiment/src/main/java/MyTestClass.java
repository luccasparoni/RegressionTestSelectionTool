import com.RegressionTestSelectionTool.TestSelector;
import com.RegressionTestSelectionTool.utils.SelectionTechniqueEnum;
import jdk.jshell.spi.ExecutionControl;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MyTestClass {
    public static void main(String[] args) {

        var selectedCodeViolations = new ArrayList<String>();
        selectedCodeViolations.add("GodClass");
        selectedCodeViolations.add("CommentSize");
        //selectedCodeViolations.add("CognitiveComplexity");
        selectedCodeViolations.add("ExcessiveMethodLength"); // long method
        //selectedCodeViolations.add("ExcessiveClassLength"); //long class

        ArrayList<ArrayList<String>> projectsNames = new ArrayList<>(Arrays.asList(
                //new ArrayList<>(Arrays.asList("Chart", "Chart_v1b", "Chart_v26b")),
//                new ArrayList<>(Arrays.asList("Cli", "Cli_v1b", "Cli_v40b")),
                new ArrayList<>(Arrays.asList("Closure", "Closure_v106b", "Closure_v107b")),
//                new ArrayList<>(Arrays.asList("Codec", "Codec_v1b", "Codec_v18b "))
//                new ArrayList<>(Arrays.asList("Collections", "Collections_v25b", "Collections_v28b")),
//                new ArrayList<>(Arrays.asList("Compress", "Compress_v1b", "Compress_v47b")),
                //new ArrayList<>(Arrays.asList("Csv", "Csv_v1b", "Csv_v16b"))
//                new ArrayList<>(Arrays.asList("Gson", "Gson_v1b", "Gson_v18b")),
                //new ArrayList<>(Arrays.asList("JacksonCore", "JacksonCore_v1b", "JacksonCore_v26b"))
                //new ArrayList<>(Arrays.asList("JacksonDatabind", "JacksonDatabind_v1b", "JacksonDatabind_v112b"))
                  //new ArrayList<>(Arrays.asList("JacksonXml", "JacksonXml_v1b", "JacksonXml_v6b"))
//                new ArrayList<>(Arrays.asList("Jsoup", "Jsoup_v1b", "Jsoup_v93b")),
//                new ArrayList<>(Arrays.asList("JxPath", "JxPath_v1b", "JxPath_v22b")),
                new ArrayList<>(Arrays.asList("Lang", "Lang_v65b", "Lang_v1b")),
                new ArrayList<>(Arrays.asList("Math", "Math_v106b", "Math_v1b")),
                new ArrayList<>(Arrays.asList("Mockito", "Mockito_v38b", "Mockito_v1b")),
                new ArrayList<>(Arrays.asList("Time", "Time_v27b", "Time_v1b"))
        ));

        String projectPathBase = "/home/luccasparoni/Documents/TCC/final-compilation/";


        String resultsFolderPath = "/home/luccasparoni/Documents/TCC/testes-finais/";

        for (ArrayList<String> project : projectsNames) {
            var initialVersionProjectPath = projectPathBase + project.get(1);
            var finalVersionProjectPath = projectPathBase + project.get(2);


            try {
                var firewallSelector = new TestSelector(
                        initialVersionProjectPath,
                        finalVersionProjectPath,
                        SelectionTechniqueEnum.CLASS_FIREWALL_ONLY_SMELLS,
                        "/home/luccasparoni/Documents/TCC/DependencyFinder-1.2.1-beta5",
                        selectedCodeViolations);

                var selectedTestCases = firewallSelector.getSelectedClasses();
                var totalTestClasses = firewallSelector.numberOfPossibleSelectedTestClasses();
                System.out.println("Cabou: >>>>>>>>>" + selectedTestCases.size());

                final Set<String> sanitizedSelectedTestClasses = new HashSet<String>();

                selectedTestCases.forEach(testCase -> {
                    var shouldCountAsTestCase = false;
                    if (project.get(0).contains("Xml")) {
                        shouldCountAsTestCase = testCase.contains("Test");
                    }else if (project.get(0).contains("Time") || project.get(0).contains("Jackson")) {
                        shouldCountAsTestCase = testCase.contains(".Test");
                    } else {
                        shouldCountAsTestCase = testCase.contains("Test\\$") || testCase.endsWith("Test");
                    }
                    if (shouldCountAsTestCase) {
                        testCase = testCase.split("\\$")[0];
                        sanitizedSelectedTestClasses.add(testCase);
                    }
                });

                String selectedTestCasesString = sanitizedSelectedTestClasses.toString();

                FileOutputStream fout = new FileOutputStream(
                        resultsFolderPath + "/" + project.get(0) + "_" + selectedCodeViolations + "_OnlySmells_" +
                                sanitizedSelectedTestClasses.size() + "_of_" + totalTestClasses.size() + ".txt");

//                FileOutputStream fout = new FileOutputStream(
//                        resultsFolderPath + "/" + project.get(0) + "_" + "_OnlyModified_" +
//                                sanitizedSelectedTestClasses.size() + "_of_" + totalTestClasses.size() + ".txt");
//                FileOutputStream fout = new FileOutputStream(
//                         resultsFolderPath + "/possible_selected_" + project.get(0) + "_" +
//                                sanitizedSelectedTestClasses.size() + "_of_" + totalTestClasses.size() + ".txt");

                byte b[] = selectedTestCasesString.getBytes(StandardCharsets.UTF_8);
                //byte b[] = totalTestClasses.toString().getBytes(StandardCharsets.UTF_8);

                fout.write(b);
                fout.close();
            } catch (Exception e) {
                System.out.println("Project Failed:" + project.get(0));
                e.printStackTrace();
            }
        }
    }
}
