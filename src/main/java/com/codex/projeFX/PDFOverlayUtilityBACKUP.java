//package com.codex.projeFX;
//
//import org.apache.pdfbox.cos.COSArray;
//import org.apache.pdfbox.cos.COSDictionary;
//import org.apache.pdfbox.cos.COSFloat;
//import org.apache.pdfbox.cos.COSName;
//import org.apache.pdfbox.cos.COSStream;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
//
//import java.io.IOException;
//
//public class PDFOverlayUtility {
//
//	public void addTransparentLayerToAllPages(PDDocument pdfDocument) throws IOException {
//		for (PDPage page : pdfDocument.getPages()) {
//			addTransparentLayerToPage(pdfDocument, page);
//		}
//	}
//
//	private void addTransparentLayerToPage(PDDocument pdfDocument, PDPage page) throws IOException {
//		PDRectangle mediaBox = page.getMediaBox();
//		float width = mediaBox.getWidth();
//		float height = mediaBox.getHeight();
//
//		COSDictionary groupDictionary = new COSDictionary();
//		groupDictionary.setItem(COSName.W, new COSFloat(width));
//		groupDictionary.setItem(COSName.H, new COSFloat(height));
//		groupDictionary.setItem(COSName.S, COSName.TRANSPARENCY);
//		groupDictionary.setItem(COSName.TYPE, COSName.GROUP);
//		groupDictionary.setItem(COSName.CS, COSName.DEVICERGB);
//
//		COSStream properties = null;
//		try {
//			properties = new COSStream();
//			properties.setItem(COSName.CA, new COSArray());
//			properties.setItem(COSName.AIS, COSName.OFF);
//			properties.setItem(COSName.BM, COSName.NORMAL);
//			properties.setItem(COSName.SMASK, COSName.NONE);
//			properties.setItem(COSName.TYPE, COSName.PROPERTIES);
//
//			COSDictionary propsDictionary = new COSDictionary();
//			propsDictionary.setItem(COSName.TYPE, COSName.PROPERTIES);
//			propsDictionary.setItem(COSName.CA, new COSArray());
//
//			groupDictionary.setItem(COSName.P, propsDictionary);
//
//			COSStream groupStream = null;
//			try {
//				groupStream = new COSStream();
//				groupStream.setItem(COSName.TYPE, COSName.GROUP);
//				groupStream.setItem(COSName.S, COSName.TRANSPARENCY);
//				groupStream.setItem(COSName.CS, COSName.DEVICERGB);
//				groupStream.setItem(COSName.P, propsDictionary);
//				groupStream.setItem(COSName.CA, new COSArray());
//
//				PDExtendedGraphicsState extendedGraphicsState = new PDExtendedGraphicsState();
//				extendedGraphicsState.setNonStrokingAlphaConstant(1f);
//
//				PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page,
//						PDPageContentStream.AppendMode.APPEND, false, true);
//				contentStream.setGraphicsStateParameters(extendedGraphicsState);
//				contentStream.close();
//			} finally {
//				if (groupStream != null) {
//					groupStream.close();
//				}
//			}
//		} finally {
//			if (properties != null) {
//				properties.close();
//			}
//		}
//	}
//
//}
