package com.codex.projeFX.customClasses;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.utility.annotation.InkAnnotationPanel;
import org.icepdf.ri.common.views.AnnotationComponent;

@SuppressWarnings("serial")
public class CustomInkAnnotationPanel extends InkAnnotationPanel{

	public CustomInkAnnotationPanel(SwingController controller) {
        super(controller);
        
        setEnabled(true);
    }

    /**
     * Get the annotation component associated with this panel.
     *
     * @return the annotation component
     */
    public AnnotationComponent getAnnotationComponent() {
        return currentAnnotationComponent;
    }
}
