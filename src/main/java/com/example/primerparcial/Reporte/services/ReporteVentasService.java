package com.example.primerparcial.Reporte.services;

import com.example.primerparcial.Reporte.models.Reporte;
import com.example.primerparcial.Reporte.repositories.ReporteRepository;
import com.example.primerparcial.config.cloudinary.CloudinaryService;
import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.transacciones.models.CarritoProductoDetalle;
import com.example.primerparcial.transacciones.models.Transaccion;
import com.example.primerparcial.transacciones.repositories.TransaccionRepository;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.UnitValue;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteVentasService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private ReporteRepository reporteRepository;

    // Obtener todos los reportes
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }

    // Generar y guardar el reporte en Excel
    public Reporte generarYGuardarReporteExcel(String fechaInicio, String fechaFin, Long idUsuario, Long idProducto, Long idTipoPago) throws IOException {
        List<Transaccion> transacciones = obtenerTransaccionesFiltradas(fechaInicio, fechaFin, idUsuario, idProducto, idTipoPago);
        ByteArrayInputStream excelStream = generarReporteVentasExcel(transacciones);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = excelStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] excelBytes = buffer.toByteArray();

        // Subir a Cloudinary
        String fileName = "reporte_ventas_" + LocalDate.now() + ".xlsx";
        Map uploadResult = cloudinaryService.uploadArchivosExcel(excelBytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileName);
        String excelUrl = uploadResult.get("url").toString();

        // Crear y guardar el reporte en la base de datos
        Reporte reporte = new Reporte(LocalDateTime.now(), null, excelUrl);
        return reporteRepository.save(reporte);
    }

    // Generar y guardar el reporte en PDF
    public Reporte generarYGuardarReportePdf(String fechaInicio, String fechaFin, Long idUsuario, Long idProducto, Long idTipoPago) throws IOException {
        List<Transaccion> transacciones = obtenerTransaccionesFiltradas(fechaInicio, fechaFin, idUsuario, idProducto, idTipoPago);
        ByteArrayInputStream pdfStream = generarReporteVentasPdf(transacciones);

        // Subir a Cloudinary
        String fileName = "reporte_ventas_" + LocalDate.now() + ".pdf";
        Map uploadResult = cloudinaryService.uploadArchivos(pdfStream, "application/pdf");
        String pdfUrl = uploadResult.get("url").toString();

        // Crear y guardar el reporte en la base de datos
        Reporte reporte = new Reporte(LocalDateTime.now(), pdfUrl, null);
        return reporteRepository.save(reporte);
    }
    private List<Transaccion> obtenerTransaccionesFiltradas(String fechaInicio, String fechaFin, Long idUsuario, Long idProducto, Long idTipoPago) {
        // Print the parameters
        System.out.println("Filtrando transacciones con los siguientes parámetros:");
        System.out.println("fechaInicio: " + fechaInicio);
        System.out.println("fechaFin: " + fechaFin);
        System.out.println("idUsuario: " + idUsuario);
        System.out.println("idProducto: " + idProducto);
        System.out.println("idTipoPago: " + idTipoPago);

        List<Transaccion> todasLasTransacciones = transaccionRepository.findAll();

        LocalDateTime inicio = (fechaInicio != null && !fechaInicio.isEmpty()) ? LocalDate.parse(fechaInicio).atStartOfDay() : null;
        LocalDateTime fin = (fechaFin != null && !fechaFin.isEmpty()) ? LocalDate.parse(fechaFin).atTime(LocalTime.MAX) : null;

        // Print the converted dates
        System.out.println("Fecha de inicio convertida: " + inicio);
        System.out.println("Fecha de fin convertida: " + fin);

        return todasLasTransacciones.stream()
                .filter(transaccion -> (inicio == null || !transaccion.getFecha().isBefore(inicio)))
                .filter(transaccion -> (fin == null || !transaccion.getFecha().isAfter(fin)))
                .filter(transaccion -> (idUsuario == null || transaccion.getUsuario().getId().equals(idUsuario)))
                .filter(transaccion -> {
                    System.out.println("Transacción ID: " + transaccion.getId());
                    System.out.println("Carrito asociado:");

                    // Imprimir todos los detalles del carrito (ProductoDetalle)
                    transaccion.getCarrito().getProductosDetalle().forEach(productoDetalle -> {
                        Producto producto = productoDetalle.getProductoDetalle().getProducto();
                        System.out.println("ProductoDetalle ID: " + productoDetalle.getId());
                        System.out.println("Producto ID: " + producto.getId() + " - Nombre: " + producto.getNombre()
                                + " (IDProducto recibido: " + idProducto + ")");
                    });

                    // Si idProducto es distinto de null, realizar la comparación
                    if (idProducto != null) {
                        return transaccion.getCarrito().getProductosDetalle().stream()
                                .anyMatch(productoDetalle -> {
                                    Producto producto = productoDetalle.getProductoDetalle().getProducto();
                                    System.out.println("Comparando Producto ID: " + producto.getId() + " con IDProducto recibido: " + idProducto);
                                    return producto.getId().longValue() == idProducto.longValue();
                                });
                    }

                    return true; // Si idProducto es null, no filtramos por producto
                })
                .filter(transaccion -> (idTipoPago == null || transaccion.getTipoPago().getId().equals(idTipoPago)))
                .collect(Collectors.toList());
    }



    private ByteArrayInputStream generarReporteVentasExcel(List<Transaccion> transacciones) {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("Reporte de Ventas");

        // Estilos
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle rightAlignCellStyle = workbook.createCellStyle();
        rightAlignCellStyle.setAlignment(HorizontalAlignment.RIGHT);

        int rowIdx = 0;
        for (Transaccion transaccion : transacciones) {
            // Crear encabezado de la transacción (Venta Nro, Fecha, Cliente, Tipo de Pago)
            Row transaccionTitleRow = sheet.createRow(rowIdx++);
            transaccionTitleRow.createCell(0).setCellValue("Venta Nro: " + transaccion.getId());

            Row fechaRow = sheet.createRow(rowIdx++);
            fechaRow.createCell(0).setCellValue("Fecha:");
            fechaRow.createCell(1).setCellValue(transaccion.getFecha().toString());

            Row clienteRow = sheet.createRow(rowIdx++);
            clienteRow.createCell(0).setCellValue("Cliente:");
            clienteRow.createCell(1).setCellValue(transaccion.getUsuario().getNombre());

            Row tipoPagoRow = sheet.createRow(rowIdx++);
            tipoPagoRow.createCell(0).setCellValue("Tipo de Pago:");
            tipoPagoRow.createCell(1).setCellValue(transaccion.getTipoPago().getNombre());

            rowIdx++; // Espacio entre encabezado y tabla de productos

            // Crear encabezado de productos
            Row headerRow = sheet.createRow(rowIdx++);
            String[] headers = {"Producto", "Precio Unitario", "Cantidad", "Monto", "Imagen"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            BigDecimal total = BigDecimal.ZERO;
            for (CarritoProductoDetalle detalle : transaccion.getCarrito().getProductosDetalle()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(detalle.getProductoDetalle().getProducto().getNombre());

                // Precio unitario del producto
                BigDecimal precio = detalle.getProductoDetalle().getProducto().getPrecio();
                row.createCell(1).setCellValue(precio.doubleValue());

                // Cantidad
                row.createCell(2).setCellValue(detalle.getCantidad());

                // Calcular el monto (cantidad * precio)
                BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
                BigDecimal totalPrecio = cantidad.multiply(precio);
                total = total.add(totalPrecio);  // Sumar al total
                row.createCell(3).setCellValue(totalPrecio.doubleValue());

                row.createCell(4).setCellValue(detalle.getProductoDetalle().getImagen2D()); // Imagen URL
            }

            // Agregar el total al final, alineado a la derecha
            Row totalRow = sheet.createRow(rowIdx++);
            Cell totalLabelCell = totalRow.createCell(3); // Total alineado a la derecha
            totalLabelCell.setCellValue("Total:");
            totalLabelCell.setCellStyle(rightAlignCellStyle);

            Cell totalValueCell = totalRow.createCell(4); // Valor total
            totalValueCell.setCellValue(total.doubleValue());
            totalValueCell.setCellStyle(rightAlignCellStyle);

            rowIdx++; // Espacio entre transacciones
        }

        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }




    private ByteArrayInputStream generarReporteVentasPdf(List<Transaccion> transacciones) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        // Título del reporte
        document.add(new Paragraph("REPORTE DE VENTAS")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER)
                .setFontColor(ColorConstants.RED));

        for (Transaccion transaccion : transacciones) {
            // Crear tabla para los detalles de la transacción (Venta Nro, Fecha, Cliente, Tipo de Pago)
            com.itextpdf.layout.element.Table transactionTable = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(new float[]{2, 4}))
                    .useAllAvailableWidth();

            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Venta Nro:")).setBold());
            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(transaccion.getId()))));

            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Fecha:")).setBold());
            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(transaccion.getFecha().toString())));

            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Cliente:")).setBold());
            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(transaccion.getUsuario().getNombre())));

            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Tipo de Pago:")).setBold());
            transactionTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(transaccion.getTipoPago().getNombre())));

            document.add(transactionTable);
            document.add(new Paragraph("\n")); // Espacio antes de la tabla de productos

            // Crear tabla para productos
            com.itextpdf.layout.element.Table productTable = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 2, 2}))
                    .useAllAvailableWidth();

            // Encabezados de la tabla de productos
            String[] headers = {"Producto", "Precio Unitario", "Cantidad", "Monto", "Imagen"};
            for (String header : headers) {
                productTable.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(header))
                        .setBackgroundColor(ColorConstants.GRAY)
                        .setFontColor(ColorConstants.WHITE));
            }

            BigDecimal total = BigDecimal.ZERO;
            for (CarritoProductoDetalle detalle : transaccion.getCarrito().getProductosDetalle()) {
                productTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(detalle.getProductoDetalle().getProducto().getNombre())));

                // Precio unitario del producto
                BigDecimal precio = detalle.getProductoDetalle().getProducto().getPrecio();
                productTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(precio.toString())));

                // Cantidad
                productTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));

                // Calcular el monto (cantidad * precio)
                BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
                BigDecimal totalPrecio = cantidad.multiply(precio);
                total = total.add(totalPrecio);  // Sumar al total
                productTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(totalPrecio.toString())));

                // Agregar imagen si existe
                String imageUrl = detalle.getProductoDetalle().getImagen2D();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    try {
                        ImageData imageData = ImageDataFactory.create(imageUrl);
                        Image img = new Image(imageData).scaleToFit(50, 50);
                        productTable.addCell(new com.itextpdf.layout.element.Cell().add(img));
                    } catch (Exception e) {
                        productTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Imagen no disponible")));
                    }
                } else {
                    productTable.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Sin imagen")));
                }
            }

            // Añadir la tabla de productos al documento
            document.add(productTable);

            // Añadir total y alinearlo a la derecha
            document.add(new Paragraph("Total: " + total.toString())
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.RIGHT));

            document.add(new Paragraph("\n")); // Espacio entre transacciones
        }

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    // Método para eliminar reporte por ID
    public void eliminarReporte(Long id) {
        reporteRepository.deleteById(id);
    }

}
