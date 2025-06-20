package controller;

import dao.MovimientoDAO;
import model.Movimiento;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/reporte-movimientos")
public class ExportarMovimientoExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MovimientoDAO movimientoDAO = new MovimientoDAO();

        try {
            List<Movimiento> listaMovimientos = movimientoDAO.obtenerTodosLosMovimientos();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Movimientos");

            // Estilo título
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 16);
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Reporte de Movimientos de Inventario");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            sheet.createRow(1); // Fila vacía

            // Encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            String[] columnas = { "ID Movimiento", "ID Inventario", "Fecha", "Detalle", "Entrada", "Salida" };
            Row headerRow = sheet.createRow(2);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // Estilo de celdas de datos
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            int rowNum = 3;
            for (Movimiento mov : listaMovimientos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(mov.getIdMovimiento());
                row.createCell(1).setCellValue(mov.getIdInventario());
                row.createCell(2).setCellValue(mov.getFecha().toString());
                row.createCell(3).setCellValue(mov.getDetalle());
                row.createCell(4).setCellValue(mov.getEntrada());
                row.createCell(5).setCellValue(mov.getSalida());

                for (int i = 0; i < columnas.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=ReporteMovimientos.xlsx");

            OutputStream out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
            out.close();

        } catch (Exception e) {
            throw new ServletException("Error al generar el Excel de movimientos: " + e.getMessage(), e);
        }
    }
}
