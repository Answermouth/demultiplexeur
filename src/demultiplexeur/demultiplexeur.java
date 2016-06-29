package demultiplexeur;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

public class demultiplexeur {

    public static ArrayList<String> getFileList(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        ArrayList<String> fileList = new ArrayList<String>();
        
        for (File f : listOfFiles) {
            fileList.add(f.getName());
        }
        
        return fileList;
    }
    
    public static ArrayList<String> getOutputs(ArrayList<String> inputs) {
        ArrayList<String> outputs = new ArrayList<String>();
        
        for (String s : inputs) {
            outputs.add(s.substring(0, s.length()-3) + "aac");
        }
        
        return outputs;
    }
    
    public static ArrayList<String> getCommands(String pathInputs, String pathOutputs, ArrayList<String> inputs, ArrayList<String> outputs) {
        ArrayList<String> commands = new ArrayList<String>();
        
        String start, params, end;
        start = "ffmpeg -i \"" + pathInputs + "/";
        params = "\" -acodec copy -vn \"" + pathOutputs+"/";
        end = "\"";
        
        for (int i = 0; i  < inputs.size(); i++) {
            commands.add(start + inputs.get(i) + params + outputs.get(i) + end);
        }
        
        return commands;
    }
    
    public static void exec(ArrayList<String> commands) {
        try {
            for (String cmd : commands) {
                Runtime.getRuntime().exec(cmd);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ArrayList<String> inputsList, outputsList, commands;
        if (args.length > 0 && args[0].equals("absolute")) {
            inputsList = getFileList("C:/Users/Ansel/Desktop/music/video");
            outputsList = getOutputs(inputsList);        
            commands = getCommands("C:/Users/Ansel/Desktop/music/video", "C:/Users/Ansel/Desktop/music/audio", inputsList, outputsList);
            exec(commands);
        } else {
            inputsList = getFileList("./video");
            outputsList = getOutputs(inputsList);        
            commands = getCommands("./video", "./audio", inputsList, outputsList);
            exec(commands);
        }
    }
}
