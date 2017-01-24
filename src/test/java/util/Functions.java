package util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;


/**
 * Created by philipsushkov on 2016-12-08.
 */

public class Functions {
    private static Properties propUI;
    private static String currentDir;

    public static Properties ConnectToPropUI(String sPathSharedUIMap) throws IOException {
        propUI = new Properties();
        currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
        propUI.load(new FileInputStream(currentDir + sPathSharedUIMap));
        return propUI;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    //#############################################################################
    //2. Write Excel Sheet for any page
    public static void WriteExcelSheet(String sSheetName, String[][] sValues, String sPathToFile) {
        HSSFWorkbook workbook = null;
        try {
            FileInputStream inpFile = new FileInputStream(new File(sPathToFile));
            workbook = new HSSFWorkbook(inpFile);

            HSSFSheet sheet = null;

            try {
                sheet = workbook.createSheet(sSheetName);
            } catch (IllegalArgumentException e) {
                sheet = workbook.getSheet(sSheetName);
                for (int index = sheet.getLastRowNum(); index >= sheet.getFirstRowNum(); index--) {
                    sheet.removeRow(sheet.getRow(index));
                }
            }

            Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();

            int count = 0;
            while (count < sValues.length) {
                data.put(Integer.valueOf(count+1), sValues[count]);
                count++;
            }

            Set<Integer> keyset = data.keySet();
            int rownum = 0;
            for (Integer key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if(obj instanceof Date)
                        cell.setCellValue((Date)obj);
                    else if(obj instanceof Boolean)
                        cell.setCellValue((Boolean)obj);
                    else if(obj instanceof String)
                        cell.setCellValue((String)obj);
                    else if(obj instanceof Double)
                        cell.setCellValue((Double)obj);
                }
            }

            FileOutputStream outFile =
                    new FileOutputStream(new File(sPathToFile));
            workbook.write(outFile);
            outFile.close();
            System.out.println("Excel was written successfully");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //#############################################################################
    //3. Read Excel File for completing any form
    public static String[][] ReadExcelSheet(String sSheetName, int columnsTotal, String sExcept, String sPathToFile) throws InterruptedException {
        try {

            FileInputStream file = new FileInputStream(new File(sPathToFile));

            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheet(sSheetName);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            //String str[][] = new String[sheet.getLastRowNum()+1][columnsTotal];
            ArrayList<String[]> zoom = new ArrayList();

            //System.out.print(sheet.getLastRowNum() + "\n");

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String temp[] = new String[row.getLastCellNum()];

                //System.out.print(row.getCell(0).getStringCellValue() + "\n");
                if (!String.valueOf(row.getCell(0)).equals(sExcept)) {
                    //zoom.add(new String[]{row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue()});
                    for (int i=0; i<row.getLastCellNum(); i++) {
                        if (row.getCell(i).getCellType() == 0)
                            temp[i] = Integer.toString(((int)row.getCell(i).getNumericCellValue()));
                        if (row.getCell(i).getCellType() == 1)
                            temp[i] = row.getCell(i).getStringCellValue();
                        //System.out.print(temp[i] + "\n");
                    }
                    zoom.add(temp);
                }
            }
            file.close();

            String str[][] = zoom.toArray(new String[zoom.size()][columnsTotal]);

            return str;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void WriteArrayToJSON(String[] args, String sPathToFile, String sArrayName) {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();

        for (int i=0; i<args.length; i++) {
            list.add(args[i]);
        }

        obj.put(sArrayName, list);

        try {
            FileWriter file = new FileWriter(sPathToFile);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.print(obj);

    }

    public static String[] ReadArrayFromJSON(String sPathToFile, String sArrayName) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(sPathToFile));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray items = (JSONArray) jsonObject.get(sArrayName);
            String temp[] = new String[items.size()];

            int i = 0;
            for (Iterator<String> iterator = items.iterator(); iterator.hasNext(); i++) {
                temp[i] = iterator.next();
                //System.out.println(temp[i]);
            }

            return temp;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Clear JSON file
    public static void ClearJSONfile(String sPathToFile) {
        try {
            FileWriter fileClear = new FileWriter(sPathToFile);
            fileClear.write("");
            fileClear.flush();
            fileClear.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
