import java.util.*;
import java.util.regex.Pattern;

public class BehavioralSecurityScanner {

    private final Map<String, List<Pattern>> behaviorPatterns =
            new LinkedHashMap<>();

    public BehavioralSecurityScanner() {

        // ---------------------------------------------------------
        // FILE SYSTEM ACCESS
        // ---------------------------------------------------------

        behaviorPatterns.put("FILE_SYSTEM_ACCESS", List.of(

                Pattern.compile(
                        "\\bfilesystemobject\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bcreatefolder\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bdeletefile\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bcopyfile\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bmovefile\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bopentextfile\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bcreatetextfile\\b",
                        Pattern.CASE_INSENSITIVE
                )
        ));


        // ---------------------------------------------------------
        // REGISTRY MODIFICATION
        // ---------------------------------------------------------

        behaviorPatterns.put("REGISTRY_MODIFICATION", List.of(

                Pattern.compile(
                        "\\bregwrite\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bregdelete\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "hkey_local_machine",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "hkey_current_user",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "hkcu\\\\\\\\",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "hklm\\\\\\\\",
                        Pattern.CASE_INSENSITIVE
                )
        ));


        // ---------------------------------------------------------
        // PROCESS / COMMAND EXECUTION
        // ---------------------------------------------------------

        behaviorPatterns.put("PROCESS_EXECUTION", List.of(

                Pattern.compile(
                        "\\bpowershell(?:\\.exe)?\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bcmd\\.exe\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bshell\\.application\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bwscript\\.shell\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bcscript(?:\\.exe)?\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bwscript(?:\\.exe)?\\b",
                        Pattern.CASE_INSENSITIVE
                )
        ));


        // ---------------------------------------------------------
        // EMAIL AUTOMATION
        // ---------------------------------------------------------

        /*
         * Notice that plain "mapi" is deliberately NOT here.
         *
         * "69.mapi" should not be interpreted as email behavior.
         */

        behaviorPatterns.put("EMAIL_AUTOMATION", List.of(

                Pattern.compile(
                        "outlook\\.application",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "getnamespace\\s*\\(\\s*[\"']mapi[\"']\\s*\\)",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "createobject\\s*\\(\\s*[\"']outlook\\.application[\"']\\s*\\)",
                        Pattern.CASE_INSENSITIVE
                )
        ));


        // ---------------------------------------------------------
        // NETWORK ACCESS
        // ---------------------------------------------------------

        behaviorPatterns.put("NETWORK_ACCESS", List.of(

                Pattern.compile(
                        "xmlhttp",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "winhttp",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bfetch\\s*\\(",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "websocket",
                        Pattern.CASE_INSENSITIVE
                )
        ));


        // ---------------------------------------------------------
        // SCRIPT EXECUTION
        // ---------------------------------------------------------

        behaviorPatterns.put("SCRIPT_EXECUTION", List.of(

                Pattern.compile(
                        "<script\\b",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "vbscript",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "javascript:",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\beval\\s*\\(",
                        Pattern.CASE_INSENSITIVE
                )
        ));


        // ---------------------------------------------------------
        // OBFUSCATION INDICATORS
        // ---------------------------------------------------------

        /*
         * These are weak indicators.
         * Their presence does NOT mean that content is malicious.
         */

        behaviorPatterns.put("OBFUSCATION", List.of(

                Pattern.compile(
                        "\\bfromcharcode\\s*\\(",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\batob\\s*\\(",
                        Pattern.CASE_INSENSITIVE
                ),

                Pattern.compile(
                        "\\bunescape\\s*\\(",
                        Pattern.CASE_INSENSITIVE
                )
        ));
    }


    public Map<String, List<String>> scan(String content) {

        Map<String, List<String>> matches =
                new LinkedHashMap<>();

        if (content == null || content.isBlank()) {
            return matches;
        }

        for (Map.Entry<String, List<Pattern>> behavior :
                behaviorPatterns.entrySet()) {

            String behaviorName = behavior.getKey();

            for (Pattern pattern : behavior.getValue()) {

                if (pattern.matcher(content).find()) {

                    matches
                            .computeIfAbsent(
                                    behaviorName,
                                    key -> new ArrayList<>()
                            )
                            .add(pattern.pattern());
                }
            }
        }

        return matches;
    }
}