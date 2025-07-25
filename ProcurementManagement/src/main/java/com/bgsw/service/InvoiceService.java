package com.bgsw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.bgsw.entity.PurchaseOrder;

import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PurchaseOrderService purchaseOrderService; // Your existing service

    public byte[] generateInvoicePdf(Long orderId) {
        PurchaseOrder order = purchaseOrderService.getOrderById(orderId)
                                                  .orElseThrow(() -> new RuntimeException("Order not found"));

        Context context = new Context();
        context.setVariable("order", order);

        String htmlContent = templateEngine.process("invoice.html", context);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            return os.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

