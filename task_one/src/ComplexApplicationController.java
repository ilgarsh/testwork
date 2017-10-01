import com.sun.istack.internal.NotNull;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 *The {@code ComplexApplicationController} is Controller for {@code ComplexApplication.fxml}.
 */
public class ComplexApplicationController implements Initializable{

    @FXML TextField textFieldSearchText;
    @FXML TextField textFieldExtensionFile;
    @FXML TextField textFieldDirectory;
    @FXML ProgressBar progressBar;

    @FXML private TabPane tabPane;

    @FXML private TreeView treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * The {@code handleToolsQuit()} is method that terminates the application.
     */
    @FXML
    private void handleToolsQuit() {
        System.exit(0);
    }

    /**
     * The {@code handleButtonFind()} is method that handle button Find
     * and if is clicked build tree of files.
     */
    @FXML private void handleButtonFind() {
        Task<Void> taskFindAndSet = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String searchText = textFieldSearchText.getText();
                String extension = textFieldExtensionFile.getText().equals("") ? "log" : textFieldExtensionFile.getText();
                Path directory = Paths.get(textFieldDirectory.getText());
                try {
                    Finder finder = new Finder(directory, searchText, extension);
                    setTreeView(finder.getFoundFiles(), directory);
                } catch (NullPointerException e) {
                    System.out.println("No such file: " + directory);
                }
                return null;
            }
        };
        Thread thFind = new Thread(taskFindAndSet);
        thFind.setDaemon(true);
        thFind.start();


        Task<Void> taskUpdateProgressBar = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                double d = 0;
                while (!taskFindAndSet.isDone()) {
                    progressBar.setProgress(d);
                    if (d <= 1) {
                        d += 0.01;
                    } else {
                        d = 0;
                    }
                    Thread.sleep(100);
                }
                progressBar.setProgress(1);
                return null;
            }
        };
        Thread thProgress = new Thread(taskUpdateProgressBar);
        thProgress.setDaemon(true);
        thProgress.start();
    }

    /**
     * The {@code setTreeView} is method that build tree of files.
     * @param paths files containing search text.
     * @param root directory from which files are searched for
     */
    private void setTreeView(ArrayList<Path> paths, Path root) {
        TreeItem<String> treeItemRoot = new TreeItem<>(root.getRoot().toString());
        paths.forEach(p -> {
            p = p.toAbsolutePath();
            int depth = p.getNameCount();
            TreeItem<String> treeItemCurrent = treeItemRoot;
            for (int i = 0; i < depth; i++) {
                TreeItem<String> temp = new TreeItem<>(p.getName(i).toString());
                Optional<TreeItem<String>> treeItem = treeItemCurrent.getChildren()
                        .parallelStream()
                        .filter(stringTreeItem -> stringTreeItem.toString().equals(temp.toString())).findFirst();

                if (treeItem.isPresent()) {
                    treeItemCurrent = treeItem.get();
                } else {
                    treeItemCurrent.getChildren().add(temp);
                    treeItemCurrent = temp;
                }
            }
        });

        //treeView always not null
        treeView.setRoot(treeItemRoot);

        //create handler for mouse click
        EventHandler<MouseEvent> mouseEventHandle = this::handleMouseClicked;

        //set mouse click handler on treeView
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

    }

    /**
     * The {@code handleMouseClicked} is method that catches
     * mouse click.
     * If clicked node is Text or TreeCell we find full path of item
     * and open it in new {@code Tab}.
     * @param event {@code MouseEvent}
     */
    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            TreeItem item = (TreeItem) treeView.getSelectionModel().getSelectedItem();
            if (getFullPath(item) != null) {
                Path path = Paths.get(getFullPath(item));
                Tab tab = new Tab();
                try {
                    StringBuilder sb = new StringBuilder();
                    if (!Files.isDirectory(path)) {
                        Files.lines(path).forEachOrdered(sb::append);
                        tab.setText(item.getValue().toString());
                        TextArea textArea = new TextArea(sb.toString());
                        textArea.setEditable(false);
                        textArea.prefHeight(533.0);
                        textArea.prefWidth(445.0);
                        textArea.setWrapText(true);
                        tab.setContent(textArea);
                        tabPane.getTabs().add(tab);
                        tabPane.getSelectionModel().select(tab);
                    }
                } catch (IOException e) {
                    System.out.println("Some problems with: " + path);
                }
            }
        }
    }

    /**
     * The {@code getFullPath(Object o)} is method that return full path of file.
     *
     * @param o {@code Object} that cast in {@code TreeItem}
     * @return {@code String}
     */
    private String getFullPath(@NotNull Object o) {
        StringBuilder sb = new StringBuilder();
        TreeItem item = (TreeItem) o;
        while (item != null) {
            sb.insert(0, item.getValue().toString());
            sb.insert(0, File.separator);
            item = item.getParent();
        }

        //check for exception
        if (sb.length() > 0) {
            //delete duplicate
            return sb.deleteCharAt(0).toString();
        } else {
            return null;
        }
    }
}
