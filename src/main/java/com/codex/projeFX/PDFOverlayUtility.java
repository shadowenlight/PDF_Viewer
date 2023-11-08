package com.codex.projeFX;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

public class PDFOverlayUtility {
	
	public void addTransparentLayerToAllPages(PDDocument pdfDocument) throws IOException {
	    for (PDPage page : pdfDocument.getPages()) {
	        addTransparentLayerToPage(pdfDocument, page);
	    }
	}

	public boolean isAnnotationLayerApplied(PDDocument pdfDocument) throws IOException {
	    for (PDPage page : pdfDocument.getPages()) {
	        if (!isAnnotationLayerCreated(page)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public void removeTransparentLayerFromAllPages(PDDocument pdfDocument) {
	    for (PDPage page : pdfDocument.getPages()) {
	        removeTransparentLayerFromPage(page);
	    }
	}
	
	public boolean hasAnnotations(PDDocument pdfDocument) throws IOException {
        for (PDPage page : pdfDocument.getPages()) {
            List<PDAnnotation> annotations = page.getAnnotations();
            if (!annotations.isEmpty()) {
                return true;
            }
        }
        return false;
    }
	
	private void addTransparentLayerToPage(PDDocument pdfDocument, PDPage page) throws IOException {
	    COSDictionary pageDictionary = page.getCOSObject();
	    COSDictionary groupDictionary = new COSDictionary();
	    groupDictionary.setItem(COSName.TYPE, COSName.GROUP);
	    groupDictionary.setItem(COSName.S, COSName.TRANSPARENCY);
	    groupDictionary.setItem(COSName.CS, COSName.DEVICERGB);

	    pageDictionary.setItem(COSName.GROUP, groupDictionary);
	}

	private boolean isAnnotationLayerCreated(PDPage page) {
	    COSDictionary pageDictionary = page.getCOSObject();
	    COSDictionary groupDictionary = pageDictionary.getCOSDictionary(COSName.GROUP);

	    if (groupDictionary != null) {
	        COSName groupType = groupDictionary.getCOSName(COSName.TYPE);
	        COSName groupSubType = groupDictionary.getCOSName(COSName.S);
	        COSName groupColorSpace = groupDictionary.getCOSName(COSName.CS);

	        return groupType == COSName.GROUP && groupSubType == COSName.TRANSPARENCY
	                && groupColorSpace == COSName.DEVICERGB;
	    }

	    return false;
	}
	
	private void removeTransparentLayerFromPage(PDPage page) {
	    COSDictionary pageDictionary = page.getCOSObject();
	    pageDictionary.removeItem(COSName.GROUP);
	}
}
