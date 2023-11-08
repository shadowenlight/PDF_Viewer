package com.codex.projeFX.customClasses;

import org.icepdf.core.pobjects.Name;
import org.icepdf.core.pobjects.annotations.BorderStyle;
import org.icepdf.core.pobjects.annotations.InkAnnotation;
import org.icepdf.core.util.Library;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomInkAnnotation extends InkAnnotation {

	public CustomInkAnnotation(Library l, HashMap h) {
		super(l, h);
	}

	public float getCustomLineThickness() {
		return super.borderStyle.getStrokeWidth();
	}

	public void setCustomLineThickness(float customLineThickness) {
		super.borderStyle.setStrokeWidth(customLineThickness);
	}

	public BorderStyle getCustomLineStyle() {
		return super.getBorderStyle();
	}

	public void setCustomLineStyle(BorderStyle style) {
		super.borderStyle = style;
	}

	public Color getCustomColor() {
		return super.getColor();
	}

	public void setCustomColor(Color customColor) {
		super.setColor(customColor);
	}
}