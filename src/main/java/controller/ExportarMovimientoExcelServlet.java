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
        Sheet sheet = workbook.createSheet("Kardex");

        // Estilo título
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte KARDEX");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

        sheet.createRow(1); // Fila vacía

        // Estilo encabezado
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        String[] columnas = {
            "Fecha", "Producto", "Detalle", "Entrada", "Salida",
            "Costo Unitario", "Total Movimiento", "Existencia"
        };

        Row headerRow = sheet.createRow(2);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        // Estilo para celdas de datos
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // Estilo para moneda (S/. 0.00)
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.cloneStyleFrom(dataStyle);
        DataFormat df = workbook.createDataFormat();
        currencyStyle.setDataFormat(df.getFormat("S/. #,##0.00"));

        int rowNum = 3;
        for (Movimiento mov : listaMovimientos) {
            Row row = sheet.createRow(rowNum++);

            Cell fechaCell = row.createCell(0);
            fechaCell.setCellValue(mov.getFecha().toString());
            fechaCell.setCellStyle(dataStyle);

            Cell productoCell = row.createCell(1);
            productoCell.setCellValue(mov.getNombreProducto());
            productoCell.setCellStyle(dataStyle);

            Cell detalleCell = row.createCell(2);
            detalleCell.setCellValue(mov.getDetalle());
            detalleCell.setCellStyle(dataStyle);

            Cell entradaCell = row.createCell(3);
            entradaCell.setCellValue(mov.getEntrada());
            entradaCell.setCellStyle(dataStyle);

            Cell salidaCell = row.createCell(4);
            salidaCell.setCellValue(mov.getSalida());
            salidaCell.setCellStyle(dataStyle);

            Cell costoCell = row.createCell(5);
            costoCell.setCellValue(mov.getCostoUnitario());
            costoCell.setCellStyle(currencyStyle);

            Cell totalCell = row.createCell(6);
            totalCell.setCellValue(mov.getTotalMovimiento());
            totalCell.setCellStyle(currencyStyle);

            Cell existenciaCell = row.createCell(7);
            existenciaCell.setCellValue(mov.getExistenciaActual());
            existenciaCell.setCellStyle(dataStyle);
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=KardexInventario.xlsx");

        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();

    } catch (Exception e) {
        throw new ServletException("Error al generar el Excel de Kardex: " + e.getMessage(), e);
    }
    }
}