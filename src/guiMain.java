package src;

import src.Student_util.FileUtil;
import src.gui.LoginFrame;
import src.service.AuthService;

import javax.swing.*;
import java.io.File;

public class guiMain {
    public static void main(String[] args) {
        String baseDir = System.getProperty("user.dir") + File.separator + "Student_Data";
        String studentPath = baseDir + File.separator + "Students.csv";
        String userPath = baseDir + File.separator + "Users.csv";

        // Initialize data files
        FileUtil.initializeDataFile(studentPath);
        FileUtil.initializeUserFile(userPath);
        FileUtil.loadFromFile(studentPath);
        AuthService.ensureDefaultAdmin();

        // Launch login GUI
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
