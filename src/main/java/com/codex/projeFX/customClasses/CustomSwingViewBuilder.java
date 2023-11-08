package com.codex.projeFX.customClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.icepdf.ri.common.KeyEventConstants;
import org.icepdf.ri.common.PageNumberTextFieldInputVerifier;
import org.icepdf.ri.common.PageNumberTextFieldKeyListener;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.utility.annotation.CircleAnnotationPanel;
import org.icepdf.ri.common.utility.annotation.FreeTextAnnotationPanel;
import org.icepdf.ri.common.utility.annotation.LineAnnotationPanel;
import org.icepdf.ri.common.utility.annotation.LinkAnnotationPanel;
import org.icepdf.ri.common.utility.annotation.SquareAnnotationPanel;
import org.icepdf.ri.common.utility.annotation.TextAnnotationPanel;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.images.Images;
import org.icepdf.ri.util.PropertiesManager;

public class CustomSwingViewBuilder extends SwingViewBuilder {

	public CustomSwingViewBuilder(SwingController controller) {
		super(controller);
	}

	@Override
	public JSplitPane buildUtilityAndDocumentSplitPane(boolean embeddableComponent) {
		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitpane.setOneTouchExpandable(false);
		splitpane.setDividerSize(8);
		splitpane.setContinuousLayout(true);
		// set the utility pane the left of the split pane

		buildUtilityTabbedPane();

		// set the viewController embeddable flag.
		DocumentViewController viewController = viewerController.getDocumentViewController();
		// will add key event listeners
		viewerController.setIsEmbeddedComponent(embeddableComponent);

		// remove F6 focus management key from the splitpane
		splitpane.getActionMap().getParent().remove("toggleFocus");

		// add the viewControllers doc view container to the split pain
		splitpane.setRightComponent(viewController.getViewContainer());
		
		// apply previously set divider location, default is -1
		int dividerLocation = PropertiesManager.checkAndStoreIntegerProperty(propertiesManager,
				PropertiesManager.PROPERTY_DIVIDER_LOCATION, 260);
		splitpane.setDividerLocation(dividerLocation);

		// Add the split pan component to the view controller so that it can
		// manipulate the divider via the controller, hide, show, etc. for
		// utility pane.
		if (viewerController != null)
			viewerController.setUtilityAndDocumentSplitPane(splitpane);
				
		return splitpane;
	}

	@Override
	public JToolBar buildUtilityToolBar(boolean embeddableComponent, PropertiesManager propertiesManager) {
		JToolBar toolbar = new JToolBar();
		commonToolBarSetup(toolbar, false);

		if (!PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
				PropertiesManager.PROPERTY_SHOW_UTILITY_PRINT)) {
			return toolbar;
		}

//		addToToolBar(toolbar, buildSaveAsFileButton());
//		addToToolBar(toolbar, buildShowHideUtilityPaneButton());
		return toolbar;
	}

	@Override
	public JTextField buildCurrentPageNumberTextField() {
		JTextField pageNumberTextField = new JTextField("", 3);
		pageNumberTextField.setToolTipText(messageBundle.getString("viewer.toolbar.navigation.current.tooltip"));
		pageNumberTextField.setInputVerifier(new PageNumberTextFieldInputVerifier());

		/**
		 * Add a key listener and check to make sure the character intered is a digit,
		 * period, the back_space or delete keys. If not the invalid character is
		 * ignored and a system beep is triggered.
		 */
		pageNumberTextField.addKeyListener(new PageNumberTextFieldKeyListener());

		pageNumberTextField.setHorizontalAlignment(SwingConstants.CENTER);

		if (viewerController != null)
			viewerController.setCurrentPageNumberTextField(pageNumberTextField);
		return pageNumberTextField;
	}

	@Override
	public JButton buildFirstPageButton() {

		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.navigation.firstPage.label"),
				messageBundle.getString("viewer.toolbar.navigation.firstPage.tooltip"), null, null, null);

		if (viewerController != null && btn != null) {
			ImageIcon firstIcon = new ImageIcon(getClass().getResource("/view/FirstPageIcon.png"));
			btn.setIcon(firstIcon);
			btn.setText("");

			viewerController.setFirstPageButton(btn);
		}

		return btn;
	}

	@Override
	public JButton buildPreviousPageButton() {

		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.navigation.previousPage.label"),
				messageBundle.getString("viewer.toolbar.navigation.previousPage.tooltip"), null, null, null);

		if (viewerController != null && btn != null) {
			ImageIcon prevIcon = new ImageIcon(getClass().getResource("/view/PreviousPageIcon.png"));
			btn.setIcon(prevIcon);
			btn.setText("");

			viewerController.setPreviousPageButton(btn);
		}

		return btn;
	}

	@Override
	public JButton buildNextPageButton() {
		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.navigation.nextPage.label"),
				messageBundle.getString("viewer.toolbar.navigation.nextPage.tooltip"), null, null, null);

		if (viewerController != null && btn != null) {
			ImageIcon nextIcon = new ImageIcon(getClass().getResource("/view/NextPageIcon.png"));
			btn.setIcon(nextIcon);
			btn.setText("");

			viewerController.setNextPageButton(btn);
		}
		return btn;
	}

	@Override
	public JButton buildLastPageButton() {
		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.navigation.lastPage.label"),
				messageBundle.getString("viewer.toolbar.navigation.lastPage.tooltip"), null, null, null);
		if (viewerController != null && btn != null) {
			ImageIcon lastIcon = new ImageIcon(getClass().getResource("/view/LastPageIcon.png"));
			btn.setIcon(lastIcon);
			btn.setText("");

			viewerController.setLastPageButton(btn);
		}
		return btn;
	}

	@Override
	public JMenuItem buildGoToPageMenuItem() {
		JMenuItem mi = makeMenuItem(messageBundle.getString("viewer.menu.document.gotToPage.label"),
				buildKeyStroke(KeyEventConstants.KEY_CODE_GOTO, KeyEventConstants.MODIFIER_GOTO));
		if (viewerController != null && mi != null) {
			ImageIcon goToIcon = new ImageIcon(getClass().getResource("/view/GoToPageIcon.png"));
			mi.setIcon(goToIcon);
			mi.setText("");

			viewerController.setGoToPageMenuItem(mi);
		}
		return mi;
	}

	@Override
	public JLabel buildNumberOfPagesLabel() {
		JLabel lbl = new JLabel();
		lbl.setToolTipText(messageBundle.getString("viewer.toolbar.navigation.pages.tooltip"));
		if (viewerController != null)
			viewerController.setNumberOfPagesLabel(lbl);
		return lbl;
	}

	@Override
	public JToolBar buildPageNavigationToolBar() {
		JToolBar toolbar = new JToolBar();
		commonToolBarSetup(toolbar, false);

		toolbar.setOpaque(false);
		toolbar.setBackground(new Color(0, 0, 0, 0));

		Border border = BorderFactory.createLineBorder(Color.decode("#3a7ad9"));

		JButton firstPage = buildFirstPageButton();

		JButton previousPage = buildPreviousPageButton();

		JTextField currentPage = buildCurrentPageNumberTextField();

		currentPage.setBorder(border);

		JMenuItem goToPage = buildGoToPageMenuItem();
		goToPage.setBackground(Color.white);
		goToPage.setBorder(border);

		JButton nextPage = buildNextPageButton();

		JButton lastPage = buildLastPageButton();

		addToToolBar(toolbar, firstPage);
		addToToolBar(toolbar, previousPage);
		addToToolBar(toolbar, currentPage);
		addToToolBar(toolbar, goToPage);
		addToToolBar(toolbar, nextPage);
		addToToolBar(toolbar, lastPage);
		
		toolbar.setPreferredSize(new Dimension(270, 27));
		return toolbar;
	}

//	BOTTOM BAR
	@Override
	public JPanel buildStatusPanel() {
		// check to see if the status bars should be built.
		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
				PropertiesManager.PROPERTY_SHOW_STATUSBAR)) {
			JPanel statusPanel = new JPanel(new BorderLayout());

			JPanel viewPanel = new JPanel();
			// Only add actual buttons to the view panel if requested by the properties file
			// Regardless we'll add the parent JPanel, to preserve the same layout behavior
			if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
					PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE)) {
//				if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//						PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE_SINGLE))
//					viewPanel.add(buildPageViewSinglePageNonConToggleButton());
//				if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//						PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE_SINGLE_CONTINUOUS))
//					viewPanel.add(buildPageViewSinglePageConToggleButton());
//				if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//						PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE_DOUBLE))
//					viewPanel.add(buildPageViewFacingPageNonConToggleButton());
//				if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//						PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE_DOUBLE_CONTINUOUS))
//					viewPanel.add(buildPageViewFacingPageConToggleButton());
			}

			viewPanel.add(buildPageNavigationToolBar());
			viewPanel.setBackground(Color.white);
			statusPanel.add(viewPanel, BorderLayout.CENTER);

			JLabel lbl2 = new JLabel(" ");
			lbl2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // So text isn't at the very edge
			statusPanel.add(lbl2, BorderLayout.EAST);
			
			statusPanel.setBackground(Color.white);

			return statusPanel;
		}
		return null;
	}

	@Override
	public JToggleButton buildFitActualSizeButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.pageFit.actualsize.label"),
				messageBundle.getString("viewer.toolbar.pageFit.actualsize.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon actualSizeIcon = new ImageIcon(getClass().getResource("/view/OriginalSizeIcon.png"));
			btn.setIcon(actualSizeIcon);
			btn.setText("");

			viewerController.setFitActualSizeButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildFitPageButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.pageFit.fitWindow.label"),
				messageBundle.getString("viewer.toolbar.pageFit.fitWindow.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon fitWindowIcon = new ImageIcon(getClass().getResource("/view/FitInWindowIcon.png"));
			btn.setIcon(fitWindowIcon);
			btn.setText("");

			viewerController.setFitHeightButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildFitWidthButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.pageFit.fitWidth.label"),
				messageBundle.getString("viewer.toolbar.pageFit.fitWidth.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon fitWidthIcon = new ImageIcon(getClass().getResource("/view/FitToWidthIcon.png"));
			btn.setIcon(fitWidthIcon);
			btn.setText("");

			viewerController.setFitWidthButton(btn);
		}
		return btn;
	}

	@Override
	public JToolBar buildFitToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setBackground(null);
		commonToolBarSetup(toolbar, false);
		addToToolBar(toolbar, buildFitActualSizeButton());
		toolbar.addSeparator();
		addToToolBar(toolbar, buildFitPageButton());
		toolbar.addSeparator();
		addToToolBar(toolbar, buildFitWidthButton());

		return toolbar;
	}

	@Override
	public JButton buildZoomOutButton() {
		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.zoom.out.label"),
				messageBundle.getString("viewer.toolbar.zoom.out.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon zoomOutIcon = new ImageIcon(getClass().getResource("/view/ZoomOutIcon.png"));
			btn.setIcon(zoomOutIcon);
			btn.setText("");

			viewerController.setZoomOutButton(btn);
		}
		return btn;
	}

	@Override
	public JComboBox buildZoomCombBox() {
		// Get the properties manager in preparation for trying to get the zoom levels
		doubleCheckPropertiesManager();

		// Assign any different zoom ranges from the properties file if possible
		zoomLevels = PropertiesManager.checkAndStoreFloatArrayProperty(propertiesManager,
				PropertiesManager.PROPERTY_ZOOM_RANGES, zoomLevels);				

		JComboBox tmp = new JComboBox();
		tmp.setToolTipText(messageBundle.getString("viewer.toolbar.zoom.tooltip"));
		tmp.setPreferredSize(new Dimension(35, tmp.getHeight()));
		tmp.setForeground(Color.decode("#3a7ad9"));
		tmp.setBackground(Color.WHITE);
		for (float zoomLevel : zoomLevels)
			tmp.addItem(NumberFormat.getPercentInstance().format(zoomLevel));
		tmp.setEditable(true);
		if (viewerController != null) 
			viewerController.setZoomComboBox(tmp, zoomLevels);						
			
		return tmp;
	}

	@Override
	public JButton buildZoomInButton() {
		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.zoom.in.label"),
				messageBundle.getString("viewer.toolbar.zoom.in.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon zoomInIcon = new ImageIcon(getClass().getResource("/view/ZoomInIcon.png"));			
			btn.setIcon(zoomInIcon);
			btn.setText("");

			viewerController.setZoomInButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildPanToolButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.tool.pan.label"),
				messageBundle.getString("viewer.toolbar.tool.pan.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon handIcon = new ImageIcon(getClass().getResource("/view/HandIcon.png"));
			btn.setIcon(handIcon);
			btn.setText("");

			viewerController.setPanToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildTextSelectToolButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.tool.text.label"),
				messageBundle.getString("viewer.toolbar.tool.text.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon selectionIcon = new ImageIcon(getClass().getResource("/view/SelectionIcon.png"));
			btn.setIcon(selectionIcon);
			btn.setText("");

			viewerController.setTextSelectToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildZoomInToolButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.tool.zoomMarquis.label"),
				messageBundle.getString("viewer.toolbar.tool.zoomMarquis.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon selectionZoomIcon = new ImageIcon(getClass().getResource("/view/SelectionZoomIcon.png"));
			btn.setIcon(selectionZoomIcon);
			btn.setText("");

			viewerController.setZoomInToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildZoomOutToolButton() {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.tool.zoomDynamic.label"),
				messageBundle.getString("viewer.toolbar.tool.zoomDynamic.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon dynamicZoomIcon = new ImageIcon(getClass().getResource("/view/DynamicZoomIcon.png"));
			btn.setIcon(dynamicZoomIcon);
			btn.setText("");

			viewerController.setZoomDynamicToolButton(btn);
		}
		return btn;
	}
	
	@Override
    public JToolBar buildZoomToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setBackground(null);
        commonToolBarSetup(toolbar, false);
        
        addToToolBar(toolbar, buildZoomOutButton());
        addToToolBar(toolbar, buildZoomCombBox());
        addToToolBar(toolbar, buildZoomInButton());
        return toolbar;
    }
	
	@Override
    public JToolBar buildToolToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setBackground(null);
        commonToolBarSetup(toolbar, false);
        addToToolBar(toolbar, buildPanToolButton());
        addToToolBar(toolbar, buildTextSelectToolButton());
        addToToolBar(toolbar, buildZoomInToolButton());
        addToToolBar(toolbar, buildZoomOutToolButton());
        return toolbar;
    }

	@Override
	public JToolBar buildCompleteToolBar(boolean embeddableComponent) {
		JToolBar toolbar = new JToolBar();
//		toolbar.setLayout(new ToolbarLayout(ToolbarLayout.LEFT, 0, 0));
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.LINE_AXIS));
		commonToolBarSetup(toolbar, true);

		// Attempt to get the properties manager so we can configure which toolbars are
		// visible
		doubleCheckPropertiesManager();

//		toolbar.add(Box.createHorizontalGlue());
//		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//				PropertiesManager.PROPERTY_SHOW_TOOLBAR_UTILITY))
//			addToToolBar(toolbar, buildUtilityToolBar(embeddableComponent, propertiesManager));
//		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//				PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV))
//			addToToolBar(toolbar, buildPageNavigationToolBar());
		toolbar.add(Box.createHorizontalGlue());
		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
				PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT))
			addToToolBar(toolbar, buildFitToolBar());
		toolbar.add(Box.createHorizontalGlue());
//		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//				PropertiesManager.PROPERTY_SHOW_TOOLBAR_ZOOM))
//			addToToolBar(toolbar, buildZoomToolBar());
		toolbar.add(Box.createHorizontalGlue());
//		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//				PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE))
//			addToToolBar(toolbar, buildRotateToolBar());
		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
				PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL))
			addToToolBar(toolbar, buildToolToolBar());
		toolbar.add(Box.createHorizontalGlue());
//		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//				PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION))
//			addToToolBar(toolbar, buildAnnotationlToolBar());
//		if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager,
//				PropertiesManager.PROPERTY_SHOW_TOOLBAR_FORMS))
//			addToToolBar(toolbar, buildFormsToolBar());			    

		// we only add the configurable font engin in the demo version
//		addToToolBar(toolbar, buildDemoToolBar());

		// Set the toolbar back to null if no components were added
		// The result of this will properly disable the necessary menu items for
		// controlling the toolbar
		if (toolbar.getComponentCount() == 0) {
			toolbar = null;
		}

		if ((viewerController != null) && (toolbar != null))
			viewerController.setCompleteToolBar(toolbar);

		return toolbar;
	}
	
	public JToggleButton buildMenuButton() {
		JToggleButton menuButton = new JToggleButton("Menu");
		
		if(viewerController != null && menuButton != null) {
			ImageIcon menuIcon = new ImageIcon(getClass().getResource("/view/MenuIcon.png"));
			menuButton.setIcon(menuIcon);
			menuButton.setText("");
		}
		menuButton.setPreferredSize(new Dimension(37, 27));
		menuButton.setBackground(Color.white);
		menuButton.setBorder(null);
		
		return menuButton;
	}
	
	JToggleButton menuButton = buildMenuButton();
	
	public JPanel buildSideMenuPanel() {
		JPanel menuPanel = new JPanel(new GridBagLayout());
		JMenuBar menuBar = new JMenuBar();
		JMenuBar menuBar2 = new JMenuBar();
		
		JToolBar pageViewBar = buildFitToolBar();
		
		JToolBar zoomToolBar = buildToolToolBar();
		
		menuBar.add(pageViewBar);
		menuBar2.add(zoomToolBar);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new java.awt.Insets(0, 0, 40, 0);

		constraints.gridx = 0;
		constraints.gridy = 0;
		menuPanel.add(menuBar, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		menuPanel.add(menuBar2, constraints);
		
		return menuPanel;
	}

	@Override
	public void buildContents(Container cp, boolean embeddableComponent) {
		cp.setLayout(new BorderLayout()); // Use a different layout manager for the main container.

		// Builds the utility pane as well as the main document View, important
		// code entry point.
		JSplitPane utilAndDocSplit = buildUtilityAndDocumentSplitPane(embeddableComponent);
		if (utilAndDocSplit != null)
			cp.add(utilAndDocSplit, BorderLayout.CENTER);

		JToolBar toolBar = buildCompleteToolBar(embeddableComponent);
//		if (toolBar != null)
//			cp.add(toolBar, BorderLayout.NORTH);					
		
		JPanel statusPanel = buildStatusPanel();
		menuButton.setBackground(Color.white);
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(Color.white);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));

		if(menuPanel != null && menuButton != null) {
			menuPanel.add(menuButton);
			containerPanel.add(menuPanel);			
		}
		
		JPanel sideMenu = buildSideMenuPanel();
		if(sideMenu != null) {
			cp.add(sideMenu, BorderLayout.WEST);
			
			sideMenu.setVisible(false);
			
			menuButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Get the current visibility of the sideMenu panel
					boolean isVisible = sideMenu.isVisible();
					
					// Toggle the visibility of the sideMenu panel
					sideMenu.setVisible(!isVisible);
				}
			});
		}
		
		
		if (statusPanel != null) {
			containerPanel.add(statusPanel);
			cp.add(containerPanel, BorderLayout.SOUTH);
		}
				
		utilAndDocSplit.setBackground(Color.WHITE);
		
		toolBar.setBackground(Color.WHITE);
					
		cp.setBackground(Color.white);
		containerPanel.setBackground(Color.white);
		statusPanel.setBackground(Color.white);

	}

	CustomInkAnnotationPanel inkTools = new CustomInkAnnotationPanel(viewerController);
	CircleAnnotationPanel circleTools = new CircleAnnotationPanel(viewerController);
	SquareAnnotationPanel squareTools = new SquareAnnotationPanel(viewerController);
	LineAnnotationPanel lineTools = new LineAnnotationPanel(viewerController);
	LinkAnnotationPanel linkTools = new LinkAnnotationPanel(viewerController);
	FreeTextAnnotationPanel freeTextTools = new FreeTextAnnotationPanel(viewerController);
	TextAnnotationPanel textTools = new TextAnnotationPanel(viewerController);

	public JFrame utilityTools() {
		CustomAnnotationPanel customPanel = new CustomAnnotationPanel(viewerController);
		customPanel.buildCustomAnnotationPanel(inkTools);
		customPanel.buildCustomAnnotationPanel(circleTools);
		customPanel.buildCustomAnnotationPanel(squareTools);
		customPanel.buildCustomAnnotationPanel(lineTools);
		customPanel.buildCustomAnnotationPanel(linkTools);
		customPanel.buildCustomAnnotationPanel(freeTextTools);
		customPanel.buildCustomAnnotationPanel(textTools);

		viewerController.setAnnotationPanel(customPanel);

		JFrame inkToolFrame = new JFrame("Utility Tools");

		inkToolFrame.add(customPanel);
		inkToolFrame.pack();
		inkToolFrame.setVisible(false);
		inkToolFrame.setAlwaysOnTop(true);

		customPanel.setBackground(Color.white);

		return inkToolFrame;
	}

	private long pressStartTime;

	private JPanel remoteMenuPanel = new JPanel();

	@Override
	public JToggleButton buildInkAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.ink.label"),
				messageBundle.getString("viewer.toolbar.tool.ink.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon inkIcon = new ImageIcon(getClass().getResource("/view/InkIcon.png"));
			btn.setIcon(inkIcon);
			btn.setText("");

			viewerController.setInkAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildCircleAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.circle.label"),
				messageBundle.getString("viewer.toolbar.tool.circle.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon circleIcon = new ImageIcon(getClass().getResource("/view/CircleIcon.png"));
			btn.setIcon(circleIcon);
			btn.setText("");

			viewerController.setCircleAnnotationToolButton(btn);
		}

		return btn;
	}

	@Override
	public JToggleButton buildSquareAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.rectangle.label"),
				messageBundle.getString("viewer.toolbar.tool.rectangle.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon squareIcon = new ImageIcon(getClass().getResource("/view/SquareIcon.png"));
			btn.setIcon(squareIcon);
			btn.setText("");

			viewerController.setSquareAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildLinkAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.link.label"),
				messageBundle.getString("viewer.toolbar.tool.link.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon linkIcon = new ImageIcon(getClass().getResource("/view/LinkIcon.png"));
			btn.setIcon(linkIcon);
			btn.setText("");

			viewerController.setLinkAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildUnderlineAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.underline.label"),
				messageBundle.getString("viewer.toolbar.tool.underline.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon underlineIcon = new ImageIcon(getClass().getResource("/view/UnderlineIcon.png"));
			btn.setIcon(underlineIcon);
			btn.setText("");

			viewerController.setUnderlineAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildHighlightAnnotationUtilityToolButton(final String imageSize) {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.tool.highlight.label"),
				messageBundle.getString("viewer.toolbar.tool.highlight.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon highlightIcon = new ImageIcon(getClass().getResource("/view/HighlightIcon.png"));
			btn.setIcon(highlightIcon);
			btn.setText("");

			viewerController.setHighlightAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildStrikeOutAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.strikeOut.label"),
				messageBundle.getString("viewer.toolbar.tool.strikeOut.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon strikeThroughIcon = new ImageIcon(getClass().getResource("/view/StrikeThroughIcon.png"));
			btn.setIcon(strikeThroughIcon);
			btn.setText("");

			viewerController.setStrikeOutAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildTextAnnotationToolButton(final String imageSize) {
		JToggleButton btn = makeToolbarToggleButton(messageBundle.getString("viewer.toolbar.tool.textAnno.label"),
				messageBundle.getString("viewer.toolbar.tool.textAnno.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon textAnnotationIcon = new ImageIcon(getClass().getResource("/view/TextAnnotationIcon.png"));
			btn.setIcon(textAnnotationIcon);
			btn.setText("");

			viewerController.setTextAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildFreeTextAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.freeText.label"),
				messageBundle.getString("viewer.toolbar.tool.freeText.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon freeTextIcon = new ImageIcon(getClass().getResource("/view/FreeTextIcon.png"));
			btn.setIcon(freeTextIcon);
			btn.setText("");

			viewerController.setFreeTextAnnotationToolButton(btn);
		}
		return btn;
	}

	@Override
	public JButton buildRotateLeftButton() {
		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.rotation.left.label"),
				messageBundle.getString("viewer.toolbar.rotation.left.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon rotateLeftIcon = new ImageIcon(getClass().getResource("/view/RotateLeftIcon.png"));
			btn.setIcon(rotateLeftIcon);
			btn.setText("");

			viewerController.setRotateLeftButton(btn);
		}
		return btn;
	}

	@Override
	public JButton buildRotateRightButton() {
		JButton btn = makeToolbarButton(messageBundle.getString("viewer.toolbar.rotation.right.label"),
				messageBundle.getString("viewer.toolbar.rotation.right.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon rotateRightIcon = new ImageIcon(getClass().getResource("/view/RotateRightIcon.png"));
			btn.setIcon(rotateRightIcon);
			btn.setText("");

			viewerController.setRotateRightButton(btn);
		}
		return btn;
	}

	@Override
	public JToggleButton buildLineAnnotationToolButton() {
		JToggleButton btn = makeToolbarToggleButtonSmall(messageBundle.getString("viewer.toolbar.tool.line.label"),
				messageBundle.getString("viewer.toolbar.tool.line.tooltip"), null, null, buttonFont);
		if (viewerController != null && btn != null) {
			ImageIcon lineIcon = new ImageIcon(getClass().getResource("/view/LineIcon.png"));
			btn.setIcon(lineIcon);
			btn.setText("");

			viewerController.setLineAnnotationToolButton(btn);
		}
		return btn;
	}

	public void remoteMenu() {
		JToggleButton InkButton = buildInkAnnotationToolButton();
		JFrame ToolFrame = utilityTools();

		

		InkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {
					ToolFrame.setVisible(true);
//					CustomInkAnnotationHandler inkHandle = new CustomInkAnnotationHandler(null, null, null);
//					inkHandle.setUserDefinedLineColor(Color.green);
//					inkHandle.installTool();
				}
			}
		});

		JToggleButton CircleButton = buildCircleAnnotationToolButton();

		CircleButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {

					ToolFrame.setVisible(true);
				}
			}
		});

		JToggleButton SquareButton = buildSquareAnnotationToolButton();

		SquareButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {
					ToolFrame.setVisible(true);
				}
			}
		});

		JToggleButton LineButton = buildLineAnnotationToolButton();

		LineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {
					ToolFrame.setVisible(true);
				}
			}
		});

		JToggleButton LinkButton = buildLinkAnnotationToolButton();

		LinkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {
					ToolFrame.setVisible(true);
				}
			}
		});

		JToggleButton freeTextButton = buildFreeTextAnnotationToolButton();

		freeTextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {
					ToolFrame.setVisible(true);
				}
			}
		});

		JToggleButton textButton = buildTextAnnotationToolButton(Images.SIZE_MEDIUM);

		textButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressStartTime = System.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				long pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration > 300) {
					ToolFrame.setVisible(true);
				}
			}
		});

		JToggleButton highlightButton = buildHighlightAnnotationUtilityToolButton(Images.SIZE_MEDIUM);
		JToggleButton strikeOutButton = buildStrikeOutAnnotationToolButton();
		JToggleButton underlineButton = buildUnderlineAnnotationToolButton();

		remoteMenuPanel.setLayout(new BoxLayout(remoteMenuPanel, BoxLayout.PAGE_AXIS));		

		Border border = BorderFactory.createLineBorder(Color.decode("#3a7ad9"), 2);

		remoteMenuPanel.setBorder(border);
		remoteMenuPanel.setBackground(Color.white);

		// Add the buttons to the grid
		JPanel gridBagContainer = new JPanel(new GridBagLayout());
		gridBagContainer.setBackground(Color.white);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new java.awt.Insets(10, 25, 10, 25);

		constraints.gridx = 0;
		constraints.gridy = 0;
		gridBagContainer.add(InkButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		gridBagContainer.add(CircleButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		gridBagContainer.add(SquareButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		gridBagContainer.add(LineButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		gridBagContainer.add(LinkButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		gridBagContainer.add(freeTextButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		gridBagContainer.add(textButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		gridBagContainer.add(highlightButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		gridBagContainer.add(strikeOutButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 4;
		gridBagContainer.add(underlineButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		gridBagContainer.add(buildRotateLeftButton(), constraints);

		constraints.gridx = 1;
		constraints.gridy = 5;
		gridBagContainer.add(buildRotateRightButton(), constraints);

		remoteMenuPanel.add(gridBagContainer);

		remoteMenuPanel.add(buildZoomToolBar());

		remoteMenuPanel.setBorder(border);

		ToolFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}



	private Point initialMousePress;

	public JFrame childFrame(SwingController controller) {

		JFrame childFrame = new JFrame();
		childFrame.getContentPane().add(remoteMenuPanel);

		childFrame.setUndecorated(true);
		childFrame.setAlwaysOnTop(true);

		remoteMenu();

		childFrame.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				initialMousePress = e.getPoint();
			}
		});

		childFrame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currentLocation = childFrame.getLocation();
				int deltaX = e.getX() - initialMousePress.x;
				int deltaY = e.getY() - initialMousePress.y;
				childFrame.setLocation(currentLocation.x + deltaX, currentLocation.y + deltaY);
			}
		});

		Dimension minimumSize = remoteMenuPanel.getPreferredSize();
		minimumSize.width += 20;
		minimumSize.height += 20;
		childFrame.setMinimumSize(minimumSize);

		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		return childFrame;
	}
}