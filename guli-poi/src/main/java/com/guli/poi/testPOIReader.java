package com.guli.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Tang on 2020/10/12
 */
public class testPOIReader {

    @Test
    public void testRead03() throws Exception{

        InputStream is = new FileInputStream("/Users/Tang/test-write03.xls");

        Workbook workbook = new HSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取第一行第一列
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        Cell cell1 = row.getCell(1);

        // 输出单元内容
        System.out.println(cell.getStringCellValue());
        System.out.println(cell1.getNumericCellValue());
        // 操作结束，关闭文件
        is.close();
    }

}
