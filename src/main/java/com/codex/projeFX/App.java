package com.codex.projeFX;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codex.projeFX.dao.*;
import com.codex.projeFX.entity.*;
import com.codex.projeFX.repository.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@EnableTransactionManagement
public class App extends Application {
	private SessionFactory sessionFactory;
	private Session session;

	CourseRepository courseRepository;
	BookRepository bookRepository;
	UserRepository userRepository;

	public void sessionInit() {
		Configuration configuration = new Configuration();
		configuration.configure(App.class.getResource("/com/codex/projeFX/hibernate.cfg.xml"));

		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
	}

	public void db_init() {
		sessionInit();

		try {
			Transaction tx = session.beginTransaction();

			CourseDAO courseDAO = new CourseDAOImpl(session);
			courseRepository = new CourseRepositoryImpl(courseDAO);

			BookDAO bookDAO = new BookDAOImpl(session);
			bookRepository = new BookRepositoryImpl(bookDAO);
			
			UserDAO userDAO = new UserDAOImpl(session);
			userRepository = new UserRepositoryImpl(userDAO);

			bookRepository.deleteAllBooks();
			courseRepository.deleteAllCourses();
			userRepository.deleteAllUsers();
			
			UserEntity userDummy = new UserEntity();
			
			userDummy.setUsername("Dummy Name");
			userDummy.setPassword("1111");
			userDummy.setRole("Dummy Role");

			CourseEntity courseDummy = new CourseEntity();
			courseDummy.setCourseName("Dummy Course");

			BookEntity bookDummy1 = new BookEntity();
			bookDummy1.setTitle("Dummy Book 1");
			bookDummy1.setBookType("Type1");
			bookDummy1.setImgPath(getClass().getResource("/view/No-Image-Placeholder.svg.png"));
			bookDummy1.setPdfPath(getClass()
					.getResource(
							"/books/ConferencePaper_AnalyzingtheMentalFatigueFindingsviaPhysiologicalSignals_YA.pdf")
					.getPath());

			BookEntity bookDummy2 = new BookEntity();
			bookDummy2.setTitle("Dummy Book 2");
			bookDummy2.setBookType("Type2");
			bookDummy2.setImgPath(getClass().getResource("/view/No-Image-Placeholder.svg.png"));
			bookDummy2.setPdfPath(getClass()
					.getResource(
							"/books/soru.pdf")
					.getPath());

			BookEntity bookDummy3 = new BookEntity();
			bookDummy3.setTitle("Dummy Book 3");
			bookDummy3.setBookType("Type3");
			bookDummy3.setImgPath(getClass().getResource("/view/No-Image-Placeholder.svg.png"));
			bookDummy3.setPdfPath(getClass()
					.getResource(
							"/books/ConferencePaper_AnalyzingtheMentalFatigueFindingsviaPhysiologicalSignals_YA.pdf")
					.getPath());

			BookEntity bookDummy4 = new BookEntity();
			bookDummy4.setTitle("Dummy Book 4");
			bookDummy4.setBookType("Type4");
			bookDummy4.setImgPath(getClass().getResource("/view/No-Image-Placeholder.svg.png"));
			bookDummy4.setPdfPath(getClass()
					.getResource(
							"/books/ConferencePaper_AnalyzingtheMentalFatigueFindingsviaPhysiologicalSignals_YA.pdf")
					.getPath());

			courseDummy.addBook(bookDummy1);
			courseDummy.addBook(bookDummy2);
			courseDummy.addBook(bookDummy3);
			courseDummy.addBook(bookDummy4);

			userRepository.createUser(userDummy);
			
			bookRepository.createBook(bookDummy1);
			bookRepository.createBook(bookDummy2);
			bookRepository.createBook(bookDummy3);
			bookRepository.createBook(bookDummy4);

			courseRepository.createCourse(courseDummy);

			tx.commit();
			System.out.println("Transaction status after commit: " + tx.getStatus());

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		db_init();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/main.fxml"));

		MainController controller = new MainController(courseRepository, bookRepository,  userRepository,session, primaryStage);
		fxmlLoader.setController(controller);
		Parent root = fxmlLoader.load();

		Scene scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX App");
		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		session.close();
		sessionFactory.close();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
