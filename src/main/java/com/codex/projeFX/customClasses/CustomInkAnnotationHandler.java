package com.codex.projeFX.customClasses;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

import org.icepdf.core.pobjects.annotations.Annotation;
import org.icepdf.core.pobjects.annotations.AnnotationFactory;
import org.icepdf.core.pobjects.annotations.InkAnnotation;
import org.icepdf.ri.common.tools.InkAnnotationHandler;
import org.icepdf.ri.common.views.AbstractPageViewComponent;
import org.icepdf.ri.common.views.AnnotationCallback;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.DocumentViewModel;
import org.icepdf.ri.common.views.annotations.AbstractAnnotationComponent;
import org.icepdf.ri.common.views.annotations.AnnotationComponentFactory;

public class CustomInkAnnotationHandler extends InkAnnotationHandler {

	private Color userDefinedLineColor = lineColor;
	private float userDefinedLineThickness = stroke.getLineWidth();

	public CustomInkAnnotationHandler(DocumentViewController documentViewController,
			AbstractPageViewComponent pageViewComponent, DocumentViewModel documentViewModel) {
		super(documentViewController, pageViewComponent, documentViewModel);
	}

	public void setUserDefinedLineColor(Color color) {
		this.userDefinedLineColor = color;
	}

	public void setUserDefinedLineThickness(float thickness) {
		this.userDefinedLineThickness = thickness;
	}

	@Override
	public void paintTool(Graphics g) {
		if (inkPath != null) {
			Graphics2D gg = (Graphics2D) g;
			Color oldColor = gg.getColor();
			Stroke oldStroke = gg.getStroke();
			gg.setColor(userDefinedLineColor);
			gg.setStroke(new BasicStroke(userDefinedLineThickness));
			gg.draw(inkPath);
			gg.setColor(oldColor);
			gg.setStroke(oldStroke);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		inkPath = new GeneralPath();
		inkPath.moveTo(e.getX(), e.getY());
		pageViewComponent.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		inkPath.moveTo(e.getX(), e.getY());

		Rectangle bBox = inkPath.getBounds();
		bBox.setRect(bBox.getX() - 5, bBox.getY() - 5, bBox.getWidth() + 10, bBox.getHeight() + 10);
		Rectangle tBbox = convertToPageSpace(bBox).getBounds();
		Shape tInkPath = convertToPageSpace(inkPath);

		InkAnnotation annotation = (InkAnnotation) AnnotationFactory.buildAnnotation(
				documentViewModel.getDocument().getPageTree().getLibrary(), Annotation.SUBTYPE_INK, tBbox);

		annotation.setColor(userDefinedLineColor);
		annotation.setBorderStyle(borderStyle);
		annotation.setInkPath(tInkPath);

		annotation.setBBox(tBbox);
		annotation.resetAppearanceStream(getPageTransform());

		AbstractAnnotationComponent comp = AnnotationComponentFactory.buildAnnotationComponent(annotation,
				documentViewController, pageViewComponent, documentViewModel);
		comp.setBounds(bBox);

		if (documentViewController.getAnnotationCallback() != null) {
			AnnotationCallback annotationCallback = documentViewController.getAnnotationCallback();
			annotationCallback.newAnnotation(pageViewComponent, comp);
		}

		documentViewController.getParentController().setDocumentToolMode(DocumentViewModel.DISPLAY_TOOL_SELECTION);

		inkPath = null;
	}
}
