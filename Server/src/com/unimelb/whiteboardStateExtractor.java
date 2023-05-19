/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import com.unimelb.renderElements.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class whiteboardStateExtractor {

    public static void extractWhiteboardCSV(ArrayList<IRenderable> elements){

        StringBuilder dataForCSV = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {

            IRenderable tempRenderable = elements.get(i);
            dataForCSV.append(tempRenderable.getType()).append(",");

            Color tempCol = tempRenderable.getColor();
            dataForCSV.append(String.format("#%02x%02x%02x", tempCol.getRed(), tempCol.getGreen(), tempCol.getBlue())).append(",");

            dataForCSV.append(tempRenderable.getStrokeWidth()).append(",");
            ArrayList<Point> tempPointsArray = tempRenderable.getPoints();

            if (tempRenderable.getType() == renderTypes.TEXT) {
                dataForCSV.append(((textRender) tempRenderable).getTextString()).append(",");
            }
            for (int j = 0; j < tempPointsArray.size(); j++) {
                Point tempPoint = tempPointsArray.get(j);
                dataForCSV.append(tempPoint.x).append(",");
                if (j == tempPointsArray.size() - 1) {
                    dataForCSV.append(tempPoint.y).append("\n");
                } else {
                    dataForCSV.append(tempPoint.y).append(",");
                }
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Output CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));


        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String outputPath = fileChooser.getSelectedFile().getPath();

            if (!outputPath.toLowerCase().endsWith(".csv")) {
                outputPath += ".csv";
            }

            try (FileWriter writer = new FileWriter(outputPath)) {
                writer.write(dataForCSV.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to write to file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
        }
    }

    public static ArrayList<IRenderable> loadWhiteboardRenderables() {
        ArrayList<IRenderable> finalArrayList = new ArrayList<>();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    finalArrayList.add(generateRenderFromLine(row));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to read file, ensure valid format", "Error", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        } else {
        }

        return finalArrayList;
    }

    private static IRenderable generateRenderFromLine(String[] line) {
        renderTypes type = renderTypes.valueOf(line[0]);
        Color color = Color.decode(line[1]);
        int strokeWidth = Integer.parseInt(line[2]);
        ArrayList<Point> points;
        if (type.equals(renderTypes.TEXT)) {
            String text = line[3];
            points = generatePointList(Arrays.copyOfRange(line, 4, line.length));
            return new textRender(points, color, strokeWidth, type, text);
        } else {
            points = generatePointList(Arrays.copyOfRange(line, 3, line.length));
            switch (type) {
                case LINE:
                    return new line(points, color, strokeWidth, type);
                case CIRCLE:
                    return new circle(points, color, strokeWidth, type);
                case ELLIPSE:
                    return new ellipse(points, color, strokeWidth, type);
                case RECTANGLE:
                    return new rectangle(points, color, strokeWidth, type);
                case SQUARE:
                    return new square(points, color, strokeWidth, type);
                default:
                    return new freehandLine(points, color, strokeWidth, type);
            }
        }

    }

    private static ArrayList<Point> generatePointList(String[] pointArray) {
        ArrayList<Point> points= new ArrayList<>();
        for (int i = 0; i < pointArray.length; i = i + 2) {
            Point tempPoint = new Point(Integer.parseInt(pointArray[i]), Integer.parseInt(pointArray[i+1]));
            points.add(tempPoint);
        }
        return points;
    }
}
