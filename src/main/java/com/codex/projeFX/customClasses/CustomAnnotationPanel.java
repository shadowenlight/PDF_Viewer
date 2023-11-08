package com.codex.projeFX.customClasses;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.utility.annotation.AnnotationPanel;
import org.icepdf.ri.common.utility.annotation.AnnotationPanelAdapter;

@SuppressWarnings("serial")
public class CustomAnnotationPanel extends AnnotationPanel {

	public CustomAnnotationPanel(SwingController controller) {
		super(controller);
	}

	// Method to build isolated panels for different annotation types
	public JPanel buildCustomAnnotationPanel(AnnotationPanelAdapter annotationPanel) {
		JPanel panel = new JPanel(new BorderLayout());
		if (annotationPanel != null) {
			annotationPanel.setEnabled(false);
			panel.add(annotationPanel, BorderLayout.CENTER);
		}
		return panel;
	}
}