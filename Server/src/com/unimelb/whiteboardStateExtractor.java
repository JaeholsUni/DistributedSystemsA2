package com.unimelb;

import com.unimelb.renderElements.renderTypes;
import com.unimelb.renderElements.textRender;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class whiteboardStateExtractor {

    public static void extractWhiteboardCSV(ArrayList<IRenderable> elements, String whiteboardTitle){

        StringBuilder dataForCSV = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            IRenderable tempRenderable = elements.get(i);
            dataForCSV.append(tempRenderable.getType()).append(",");
            dataForCSV.append(tempRenderable.getColor()).append(",");
            dataForCSV.append(tempRenderable.getStrokeWidth()).append(",");
            ArrayList<Point> tempPointsArray = tempRenderable.getPoints();
            for (int j = 0; j < tempPointsArray.size(); j++) {
                Point tempPoint = tempPointsArray.get(j);
                dataForCSV.append(tempPoint.x).append(",");
                dataForCSV.append(tempPoint.y).append(",");
            }
            if (tempRenderable.getType() == renderTypes.TEXT) {
                dataForCSV.append(((textRender) tempRenderable).getTextString()).append(",");
            }
            dataForCSV.append("\n");
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Output CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));


        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String outputPath = fileChooser.getSelectedFile().getPath();

            try (FileWriter writer = new FileWriter(outputPath)) {
                writer.write(dataForCSV.toString());
                System.out.println("CSV data has been successfully extracted to " + outputPath);
            } catch (IOException e) {
                System.err.println("Failed to write CSV data to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File selection canceled by the user.");
        }
    }
}
