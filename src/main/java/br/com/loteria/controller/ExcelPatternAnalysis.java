package br.com.loteria.controller;

import br.com.loteria.model.Concurso;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author leand
 */
public class ExcelPatternAnalysis {

    private String filePath;
    private Set<Byte> numerosRepetidos = new HashSet<>();
    public ExcelPatternAnalysis() {
    }

    public ExcelPatternAnalysis(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void startAnalisys(List<Concurso> concursos) {
        try {
            for (int i = 0; i < concursos.size(); i++) {
                for (int j = i + 1; j < concursos.get(i).getDezenas().size(); j++) {
                    Set<Byte> group1 = concursos.get(i).getDezenas();
                    Set<Byte> group2 = concursos.get(j).getDezenas();
                    boolean hasRepetition = hasRepetition(group1, group2);
                    System.out.println("Repetition between Group " + (i + 1) + " and Group " + (j + 1) + ": " + hasRepetition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Concurso> readExcelFile(String filePath) throws IOException {
        List<Concurso> concursos = new ArrayList<>();

        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // Assume que a planilha desejada é a primeira (índice 0)

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Pula a primeira linha de cabeçalho
            }
            int numero = (int) row.getCell(0).getNumericCellValue();
            Date dataConcurso = row.getCell(1).getDateCellValue();
            Concurso concurso = new Concurso(numero, dataConcurso);
            for (int i = 2; i <= 6; i++) { // Leitura das colunas C até G
                Cell cell = row.getCell(i);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    concurso.getDezenas().add((byte) cell.getNumericCellValue());
                }
            }
            concursos.add(concurso);
        }

        workbook.close();
        file.close();

        return concursos;
    }

    private boolean hasRepetition(Set<Byte> group1, Set<Byte> group2) {
        for (Byte number : group1) {
            if (group2.contains(number)) {
                numerosRepetidos.add(number);
                return true;
            }
        }
        return false;
    }
    
    private Date parseStringToDate(String strData) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(strData);
        } catch (ParseException ex) {
            Logger.getLogger(ExcelPatternAnalysis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
