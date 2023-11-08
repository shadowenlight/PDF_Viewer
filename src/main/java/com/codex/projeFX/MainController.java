package com.codex.projeFX;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.hibernate.Session;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.springframework.beans.factory.annotation.Autowired;

import com.codex.projeFX.customClasses.CustomSwingViewBuilder;
import com.codex.projeFX.entity.BookEntity;
import com.codex.projeFX.entity.CourseEntity;
import com.codex.projeFX.entity.UserEntity;
import com.codex.projeFX.repository.BookRepository;
import com.codex.projeFX.repository.CourseRepository;
import com.codex.projeFX.repository.UserRepository;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.scene.Group;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class MainController implements Initializable {
	@FXML
	private Button userButton = new Button();

	@FXML
	private Button deleteButton = new Button();

	@FXML
	private Button downloadsButton;

	@FXML
	private Pane heroBanner;

	@FXML
	private VBox guideBox;

	@FXML
	private Hyperlink guideLink;

	@FXML
	private Hyperlink publisherSiteLink;

	@FXML
	private VBox mobileBox;

	@FXML
	private VBox centerBox;

	@FXML
	private HBox mobileLink;

	@FXML
	private HBox webLink;

	@FXML
	private HBox guideLinkk;

	@FXML
	private HBox publisherLinkk;

	@FXML
	private Hyperlink mobileAddressLink;

	@FXML
	private Hyperlink websiteAppLink;

	@FXML
	private Button minimizeButton;

	@FXML
	private Button closeButton;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Accordion accordion;

	@FXML
	private StackPane stackPane;

	@FXML
	private Button openButton;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	private Session session;

	private Stage primaryStage;

//	private JPanel remoteMenuPanel;

	public MainController(CourseRepository courseRepository, BookRepository bookRepository,
			UserRepository userRepository, Session session, Stage primaryStage) {
		this.courseRepository = courseRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.session = session;
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userButton.setOnAction(e -> handleUserButton());
		deleteButton.setOnAction(e -> handleDeleteButton());
		downloadsButton.setOnAction(e -> handleDownloadsButton());
		guideLink.setOnAction(e -> handleGuideLink());
		publisherSiteLink.setOnAction(e -> handlePublisherSiteLink());
		mobileAddressLink.setOnAction(e -> handleMobileAddressLink());
		websiteAppLink.setOnAction(e -> handleWebsiteAppLink());
		minimizeButton.setOnAction(e -> handleMinimizeButton());
		closeButton.setOnAction(e -> handleCloseButton());

		setButtonIcons();

		heroBanner.prefWidthProperty().bind(primaryStage.widthProperty());

//		remoteMenuPanel = new JPanel();				

		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		System.setProperty("sun.java2d.xrender", "true");

		disableAppInteractions();
		populateAccordion();
	}

	private void setButtonIcons() {
		Image minimizeIcon = new Image(getClass().getResourceAsStream("/view/mainStageIcons/MinimizeIcon.png"));
		ImageView minimizeIconView = new ImageView(minimizeIcon);
		minimizeIconView.setFitWidth(25);
		minimizeIconView.setFitHeight(25);
		minimizeButton.setGraphic(minimizeIconView);

		Image closeIcon = new Image(getClass().getResourceAsStream("/view/mainStageIcons/CloseIcon.png"));
		ImageView closeIconView = new ImageView(closeIcon);
		closeIconView.setFitWidth(30);
		closeIconView.setFitHeight(30);
		closeButton.setGraphic(closeIconView);

		Image userIcon = new Image(getClass().getResourceAsStream("/view/mainStageIcons/UserIcon.png"));
		ImageView userIconView = new ImageView(userIcon);
		userIconView.setFitWidth(30);
		userIconView.setFitHeight(30);
		userButton.setGraphic(userIconView);

		Image downloadsIcon = new Image(getClass().getResourceAsStream("/view/mainStageIcons/DownloadsIcon.png"));
		ImageView downloadsIconView = new ImageView(downloadsIcon);
		downloadsIconView.setFitWidth(25);
		downloadsIconView.setFitHeight(25);
		downloadsButton.setGraphic(downloadsIconView);

		Image deleteIcon = new Image(getClass().getResourceAsStream("/view/mainStageIcons/DeleteIcon.png"));
		ImageView deleteIconView = new ImageView(deleteIcon);
		deleteIconView.setFitWidth(25);
		deleteIconView.setFitHeight(25);
		deleteButton.setGraphic(deleteIconView);
	}

	private void handleUserButton() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.initStyle(StageStyle.TRANSPARENT);
		dialog.initOwner(primaryStage);

		StackPane userLayout = new StackPane();

		ImageView userView = new ImageView(new Image(getClass().getResourceAsStream("/view/LoginIcon.png")));
		userView.setFitWidth(20);
		userView.setFitHeight(20);

		TextField usernameField = new TextField();
		usernameField.setPromptText("Kullanıcı adı");

		userLayout.getChildren().addAll(usernameField, userView);
		userLayout.setAlignment(Pos.CENTER_RIGHT);

		Label usernameLabel = new Label("Kullanıcı");

		StackPane passwordLayout = new StackPane();

		ImageView passwordView = new ImageView(new Image(getClass().getResourceAsStream("/view/PasswordIcon.png")));
		passwordView.setFitWidth(20);
		passwordView.setFitHeight(20);

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Şifre");

		passwordLayout.getChildren().addAll(passwordField, passwordView);
		passwordLayout.setAlignment(Pos.CENTER_RIGHT);

		Label passwordLabel = new Label("Şifre");

		Image image = new Image(getClass().getResourceAsStream("/view/LoginSideImage.png"));
		ImageView loginSide = new ImageView(image);

		HBox dialgBox = new HBox(50);

		Pane blank = new Pane();
		VBox contentBox = new VBox(10);
		contentBox.getChildren().addAll(usernameLabel, userLayout, blank, passwordLabel, passwordLayout);
		contentBox.setAlignment(Pos.CENTER_LEFT);

		contentBox.minWidth(350);

		dialgBox.getChildren().addAll(loginSide, contentBox);
		dialgBox.setAlignment(Pos.CENTER_LEFT);

		dialog.getDialogPane().setContent(dialgBox);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		dialog.getDialogPane().setMinWidth(550);

		Platform.runLater(usernameField::requestFocus);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				return new Pair<>(username, password);
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			String username = usernamePassword.getKey();
			String password = usernamePassword.getValue();

			boolean isAuthenticated = authenticateUser(username, password);

			if (isAuthenticated) {
				enableAppInteractions();
				dialog.close();
			} else {
				showAlertUser("Invalid username or password!");
			}
		});

		dialog.getDialogPane().addEventHandler(DialogEvent.DIALOG_SHOWN, event -> {

		});
		dialog.getDialogPane().addEventHandler(DialogEvent.DIALOG_HIDDEN, event -> {

		});
	}

	private boolean authenticateUser(String username, String password) {
		UserEntity user = userRepository.findUserByName(username);

		if (user != null && user.getPasssword().equals(password))
			return true;

		return false;
	}

	private void enableAppInteractions() {
		userButton.setDisable(false);
		deleteButton.setDisable(false);
		downloadsButton.setDisable(false);

		Node node = centerBox;
		node.setDisable(false);
	}

	private void disableAppInteractions() {
		userButton.setDisable(false);
		deleteButton.setDisable(true);
		downloadsButton.setDisable(true);

		Node node = centerBox;
		node.setDisable(true);
	}

	private void showAlertUser(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(primaryStage);

		alert.showAndWait();
	}

	private void handleDeleteButton() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Delete Library");
		alert.setHeaderText("Choose deletion option:");
		alert.setContentText("Select an option to delete the library.");

		ButtonType deleteExceptCoursesButton = new ButtonType("Delete Except Courses");
		ButtonType deleteEverythingButton = new ButtonType("Delete Everything");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(deleteExceptCoursesButton, deleteEverythingButton, cancelButton);

		alert.showAndWait().ifPresent(buttonType -> {
			if (buttonType == deleteExceptCoursesButton) {
				// Delete everything except courses
				deleteExceptCourses();
			} else if (buttonType == deleteEverythingButton) {
				// Delete everything
				deleteEverything();
			}
		});
		Platform.runLater(this::restartApplication);
	}

	private void restartApplication() {
		try {
			// Get the current Java command
			String javaCommand = System.getProperty("java.home") + "/bin/java";

			// Get the current class path
			String classPath = System.getProperty("java.class.path");

			// Get the current main class
			String mainClass = getClass().getName();

			// Create a process builder for restarting the application
			ProcessBuilder processBuilder = new ProcessBuilder(javaCommand, "-cp", classPath, mainClass);

			// Start the new process
			processBuilder.start();

			// Exit the current instance of the application
			Platform.exit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteExceptCourses() {
		if (bookRepository.getAllBooks().isEmpty())
			showAlert("Book Library already empty!");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Delete Library");
		alert.setHeaderText("Delete Everything Except Courses");
		alert.setContentText("Are you sure you want to delete all data except courses?");

		ButtonType deleteButton = new ButtonType("Delete");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(deleteButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == deleteButton) {
			session.beginTransaction();

			try {
				List<CourseEntity> courses = courseRepository.getAllCourse();

				for (CourseEntity course : courses) {
					course.getBooks().clear();
					bookRepository.getAllBooksByCourse(course).clear();
				}
				bookRepository.deleteAllBooks();
				session.getTransaction().commit();

				// Show a confirmation message
				Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
				confirmationAlert.initOwner(primaryStage);
				confirmationAlert.setTitle("Delete Library");
				confirmationAlert.setHeaderText(null);
				confirmationAlert.setContentText("Data has been deleted except courses.");
				confirmationAlert.showAndWait();
			} catch (Exception e) {
				session.getTransaction().rollback();
				System.out.print("Failed to delete data: " + e.getMessage());
				showAlert("Failed to delete data: " + e.getMessage());
			}
			if (bookRepository.getAllBooks().isEmpty())
				System.out.print("Operation was successful.");
			else
				System.out.print("Operation was not successful.");

		}
	}

	private void deleteEverything() {
		if (bookRepository.getAllBooks().isEmpty() && courseRepository.getAllCourse().isEmpty())
			showAlert("Library already empty!");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Delete Library");
		alert.setHeaderText("Delete Everything");
		alert.setContentText("Are you sure you want to delete all data, including courses?");

		ButtonType deleteButton = new ButtonType("Delete");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(deleteButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == deleteButton) {
			session.beginTransaction();
			try {
				bookRepository.deleteAllBooks();
				courseRepository.deleteAllCourses();
				session.getTransaction().commit();

				Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
				confirmationAlert.initOwner(primaryStage);
				confirmationAlert.setTitle("Delete Library");
				confirmationAlert.setHeaderText(null);
				confirmationAlert.setContentText("All data has been deleted.");
				confirmationAlert.showAndWait();
			} catch (Exception e) {
				session.getTransaction().rollback();
				showAlert("Failed to delete data: " + e.getMessage());
			}
			if (bookRepository.getAllBooks().isEmpty() && courseRepository.getAllCourse().isEmpty())
				System.out.print("Operation was successful.");
			else
				System.out.print("Operation was not successful.");

		}
	}

	private void handleDownloadsButton() {
		// TODO: Handle downloadsButton click event
	}

	private void handleGuideLink() {
		Stage guideStage = new Stage();
		guideStage.initModality(Modality.APPLICATION_MODAL);
		guideStage.setTitle("User Guide");

		WebView webView = new WebView();
		webView.getEngine()
				.load("file:///C:/Users/isiko/eclipse-workspace/projeFX/src/main/resources/links/UserGuide.html");

		Button okButton = new Button("OK");
		okButton.setOnAction(event -> guideStage.close());

		VBox layout = new VBox(webView, okButton);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(10);

		Scene scene = new Scene(layout);

		guideStage.initStyle(StageStyle.TRANSPARENT);
		guideStage.setScene(scene);
		guideStage.showAndWait();

	}

	private void handlePublisherSiteLink() {
		String publisherSiteLink = "https://www.semihguleser.com/en";

		try {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URI(publisherSiteLink));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleMobileAddressLink() {
		String mobileAddressLink = "https://www.worldapefund.org/";

		try {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URI(mobileAddressLink));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleWebsiteAppLink() {
		String websiteAppLink = "https://m.facebook.com/nihatcftc/posts/1391791367536905/?locale=es_LA\r\n";

		try {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URI(websiteAppLink));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleMinimizeButton() {
		Stage stage = (Stage) minimizeButton.getScene().getWindow();
		stage.setIconified(true);
	}

	private void handleCloseButton() {
		Platform.exit();
	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void populateAccordion() {

		List<CourseEntity> courses = fetchCoursesFromDatabase();

		for (CourseEntity course : courses) {
			TitledPane coursePane = new TitledPane();
			coursePane.setText(course.getCourseName());
			coursePane.setAlignment(Pos.BASELINE_CENTER);

			FlowPane courseFlowPane = new FlowPane();
			courseFlowPane.setMinHeight(270.0);
			courseFlowPane.setHgap(20); // Adjust the spacing between book type items

			List<String> bookTypes = fetchBookTypesFromDatabase();

			for (String bookType : bookTypes) {
				VBox bookTypeVBox = new VBox();
				bookTypeVBox.setSpacing(10);

				URL coverURL = getClass().getResource("/view/CoverFrame.png");
				Image coverImage = new Image(coverURL.toExternalForm());
				ImageView coverFrame = new ImageView(coverImage);

				coverFrame.setFitHeight(190.0);
				coverFrame.setFitWidth(210.0);

				URL imageURL = getClass().getResource("/view/No-Image-Placeholder.svg.png");
				Image image = new Image(imageURL.toExternalForm());
				ImageView bookTypeCover = new ImageView(image);

				bookTypeCover.setFitHeight(124.0);
				bookTypeCover.setFitWidth(93.0);

				bookTypeCover.setTranslateX(15);
				bookTypeCover.setTranslateY(-15);

				Label bookTypeLabel = new Label(bookType);
				bookTypeLabel.setAlignment(Pos.TOP_CENTER);

				bookTypeLabel.setTranslateY(68);
				bookTypeLabel.getStyleClass().add("book-type-label");

				StackPane bookTypeCoverPane = new StackPane(coverFrame, bookTypeCover, bookTypeLabel);
				bookTypeCover.getStyleClass().add("book-type-cover");

				Button expandButton = new Button("Expand");
				expandButton.setOnAction(event -> handleExpandButton(bookType));

				bookTypeVBox.setAlignment(Pos.CENTER);

				bookTypeVBox.getChildren().addAll(bookTypeCoverPane, expandButton);
				courseFlowPane.getChildren().add(bookTypeVBox);
			}

			coursePane.setContent(courseFlowPane);
			accordion.getPanes().add(coursePane);
		}
	}

	private List<CourseEntity> fetchCoursesFromDatabase() {
		List<CourseEntity> courses = null;
		session.beginTransaction();
		try {
			courses = courseRepository.getAllCourse();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.print("Failed to get all courses: " + e.getMessage());
			showAlert("Failed to get all courses: " + e.getMessage());
		}
		return courses;
	}

	private List<String> fetchBookTypesFromDatabase() {
		List<String> bookTypes = null;
		session.beginTransaction();
		try {
			bookTypes = bookRepository.getAllBookTypes();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.print("Failed to get all types: " + e.getMessage());
			showAlert("Failed to get all types: " + e.getMessage());
		}
		return bookTypes;
	}

	public void handleExpandButton(String bookType) {
		List<BookEntity> books = bookRepository.getAllBooksByType(bookType);

		Stage bookListStage = new Stage();
		// bookListStage.initModality(Modality.WINDOW_MODAL);
		bookListStage.initOwner(primaryStage);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		for (int i = 0; i < books.size(); i++) {
			VBox bookListVBox = new VBox();
			bookListVBox.setId("bookListVBox");
			bookListVBox.setSpacing(10);

			BookEntity book = books.get(i);

			ImageView bookCover = new ImageView(new Image(book.getImgPath().toExternalForm()));
			bookCover.setId("bookCover");
			bookCover.setFitWidth(150);
			bookCover.setPreserveRatio(true);

			Button openButton = new Button("Open");
			openButton.setId("openButtonBook");
			int bookIndex = i;

			openButton.setOnAction(event -> {
				BookEntity selectedBook = books.get(bookIndex);
				handleOpenButton(selectedBook);
				bookListStage.close();
			});

			bookListVBox.setAlignment(Pos.CENTER);
			bookListVBox.getChildren().addAll(bookCover, openButton);
			bookListVBox.setId("bookListVBox");
			gridPane.add(bookListVBox, i % 2, i / 2);
		}

		ScrollPane scrollTypes = new ScrollPane(gridPane);
		scrollTypes.setFitToWidth(true);
		scrollTypes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollTypes.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollTypes.setId("scrollPaneBook");

		VBox vbox = new VBox(scrollTypes);
		vbox.setPadding(new Insets(10));

		Scene scene = new Scene(vbox);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		bookListStage.setScene(scene);
		bookListStage.initStyle(StageStyle.TRANSPARENT);

		bookListStage.sizeToScene();

		double mainframeMinX = primaryStage.getMinWidth();
		double mainframeMaxX = primaryStage.getMaxWidth();
		double mainframeMinY = primaryStage.getMinHeight();
		double mainframeMaxY = primaryStage.getMaxHeight();

		bookListStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				// Get the current position of the stage
				double stageX = bookListStage.getX();
				double stageY = bookListStage.getY();

				// Check if the interaction occurred outside of the mainframe area
				if (stageX < mainframeMinX || stageX > mainframeMaxX || stageY < mainframeMinY
						|| stageY > mainframeMaxY) {
					bookListStage.close();
				}
			}
		});
		bookListStage.show();
	}

	public void handleOpenButton(BookEntity selectedBook) {
		try {
			File originalFile = Paths.get(selectedBook.getPdfPath().substring(1)).toFile();

			PDDocument document = PDDocument.load(originalFile);

			PDFOverlayUtility overlayUtil = new PDFOverlayUtility();

			if (overlayUtil.hasAnnotations(document) == false) {
				overlayUtil.addTransparentLayerToAllPages(document);

				if (overlayUtil.isAnnotationLayerApplied(document)) {
					System.out.println("Annotation layer applied successfully.");
				} else {
					System.out.println("Failed to apply annotation layer.");
				}
			}

			if (overlayUtil.hasAnnotations(document) == true)
				System.out.println("Annotation layer already applied by any capable application.");

			File tempFile = File.createTempFile(originalFile.getName(), ".pdf");
			document.save(tempFile);
			document.close();

			Files.move(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			displayPdf(selectedBook.getPdfPath().substring(1));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void icePdfConf() {
		System.getProperties().put("org.icepdf.core.awtFontLoading", "true");
		System.getProperties().put("org.icepdf.core.views.page.annotation.ink.line.color", "#000000");
		System.getProperties().put("org.icepdf.core.ccittfax.jai", "True");
		System.getProperties().put("org.icepdf.core.imageReference", "scaled");
		System.getProperties().put("org.icepdf.core.target.alphaInterpolation", "VALUE_INTERPOLATION_QUALITY");
		System.getProperties().put("org.icepdf.core.target.antiAliasing", "VALUE_ANTIALIAS_ON");
		System.getProperties().put("org.icepdf.core.target.colorRender	", "VALUE_COLOR_RENDER_QUALITY");
		System.getProperties().put("org.icepdf.core.target.dither", "VALUE_DITHER_ENABLE");
		System.getProperties().put("org.icepdf.core.target.fractionalmetrics", "VALUE_FRACTIONALMETRICS_ON");
		System.getProperties().put("org.icepdf.core.target.interpolation", "VALUE_INTERPOLATION_BICUBIC");
		System.getProperties().put("org.icepdf.core.target.render", "VALUE_RENDER_QUALITY");
		System.getProperties().put("org.icepdf.core.target.textAntiAliasing", "True");
		System.getProperties().put("org.icepdf.core.target.stroke", "VALUE_STROKE_NORMALIZE");
		System.getProperties().put("org.icepdf.core.imageMaskScale.quality", "True");
		System.getProperties().put("org.icepdf.core.imageReference", "smoothScaled");

		System.out.println("org.icepdf.core.awtFontLoading: " + System.getProperty("org.icepdf.core.awtFontLoading"));
		System.out.println("org.icepdf.core.views.page.annotation.ink.line.color: "
				+ System.getProperty("org.icepdf.core.views.page.annotation.ink.line.color"));
		System.out.println("org.icepdf.core.ccittfax.jai: " + System.getProperty("org.icepdf.core.ccittfax.jai"));
		System.out.println("org.icepdf.core.imageReference: " + System.getProperty("org.icepdf.core.imageReference"));
		System.out.println("org.icepdf.core.target.alphaInterpolation: "
				+ System.getProperty("org.icepdf.core.target.alphaInterpolation"));
		System.out.println(
				"org.icepdf.core.target.antiAliasing: " + System.getProperty("org.icepdf.core.target.antiAliasing"));
		System.out.println(
				"org.icepdf.core.target.colorRender: " + System.getProperty("org.icepdf.core.target.colorRender"));
		System.out.println("org.icepdf.core.target.dither: " + System.getProperty("org.icepdf.core.target.dither"));
		System.out.println("org.icepdf.core.target.fractionalmetrics: "
				+ System.getProperty("org.icepdf.core.target.fractionalmetrics"));
		System.out.println(
				"org.icepdf.core.target.interpolation: " + System.getProperty("org.icepdf.core.target.interpolation"));
		System.out.println("org.icepdf.core.target.render: " + System.getProperty("org.icepdf.core.target.render"));
		System.out.println("org.icepdf.core.target.textAntiAliasing: "
				+ System.getProperty("org.icepdf.core.target.textAntiAliasing"));
		System.out.println("org.icepdf.core.target.stroke: " + System.getProperty("org.icepdf.core.target.stroke"));
	}

	public void displayPdf(String filePath) {

		SwingController controller = new SwingController();
		CustomSwingViewBuilder factory = new CustomSwingViewBuilder(controller);

		icePdfConf();

		MyAnnotationCallback myAnnotationCallback = new MyAnnotationCallback(controller.getDocumentViewController());
		controller.getDocumentViewController().setAnnotationCallback(myAnnotationCallback);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel viewerComponentPanel = factory.buildViewerPanel();

		JFrame childFrame = factory.childFrame(controller);

		frame.getContentPane().add(viewerComponentPanel, BorderLayout.CENTER);

		controller.openDocument(filePath);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				childFrame.dispose();
			}
		});

		childFrame.setVisible(true);

		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}