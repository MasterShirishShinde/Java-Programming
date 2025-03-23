import javax.speech.*;
import javax.speech.recognition.*;
import java.util.Locale;
import java.io.FileReader;
import java.util.Scanner;

public class VoiceAssistant extends ResultAdapter {
    static Recognizer recognizer;
    
    public static void main(String[] args) {
        try {
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ENGLISH));
            recognizer.allocate();
            
            FileReader grammar = new FileReader("commands.grammar");
            RuleGrammar ruleGrammar = recognizer.loadJSGF(grammar);
            ruleGrammar.setEnabled(true);
            
            recognizer.addResultListener(new VoiceAssistant());
            recognizer.commitChanges();
            recognizer.requestFocus();
            recognizer.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void resultAccepted(ResultEvent e) {
        try {
            Result result = (Result) e.getSource();
            ResultToken[] tokens = result.getBestTokens();
            
            String command = "";
            for (ResultToken token : tokens) {
                command += token.getSpokenText() + " ";
            }
            
            command = command.trim().toLowerCase();
            System.out.println("You said: " + command);
            executeCommand(command);
            recognizer.resume();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void executeCommand(String command) {
        try {
            switch (command) {
                case "open notepad":
                    Runtime.getRuntime().exec("notepad.exe");
                    break;
                case "open browser":
                    Runtime.getRuntime().exec("cmd /c start chrome");
                    break;
                case "tell me a joke":
                    System.out.println("Why don't programmers like nature? It has too many bugs!");
                    break;
                default:
                    System.out.println("Command not recognized!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}