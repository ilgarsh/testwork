import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A {@code Finder} that find all files
 * with some extension in directory
 * that contain some text.
 */
class Finder {

    private Path directory;
    private String searchText;
    private String fileExtension;

    private ArrayList<Path> foundFiles;

    /**
     * @param directory where we search text.
     * @param searchText that we want to find.
     * @param fileExtension like 'txt', 'class', etc (default 'log').
     */
    Finder(Path directory, String searchText, String fileExtension) {
        this.directory = directory;
        this.searchText = searchText;
        this.fileExtension = fileExtension;

        find();
    }

    /**
     * The method {@code find()} that find files
     * containing {@code this.searchText} in {@code this.directory}
     * and save results in {@code ArrayList<Path> this.foundFiles}.
     */
    private void find() {
        FinderFileVisitor finderFileVisitor = new FinderFileVisitor(searchText, fileExtension);
        try {
            Files.walkFileTree(directory, finderFileVisitor);
        } catch (IOException e) {
            System.out.println("Some problems with " + directory);
        }
        foundFiles = finderFileVisitor.foundFiles;
    }

    ArrayList<Path> getFoundFiles() {
        return foundFiles;
    }

    /**
     * A {@code FinderFileVisitor} that finds
     * all files that contain some text.
     */
    private static class FinderFileVisitor extends SimpleFileVisitor<Path> {

        private ArrayList<Path> foundFiles;
        private String searchText;
        private String fileExtension;

        FinderFileVisitor(String searchText, String fileExtension) {
            this.searchText = searchText;
            this.fileExtension = fileExtension;
            foundFiles = new ArrayList<>();
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            String fileName = file.getFileName().toString();
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex != -1 && fileName.substring(dotIndex + 1).equals(fileExtension)) {

                try {
                    Stream<String> stream = Files.lines(file);
                    stream.parallel().forEach(
                            s -> {
                                if (s.toLowerCase().contains(searchText.toLowerCase())) {
                                    foundFiles.add(file);
                                }
                            }

                    );
                    return super.visitFile(file, attrs);

                } catch (Exception e) {
                    System.out.println("Some problems with " + file.getFileName());
                }
            }
            return FileVisitResult.CONTINUE;
        }
    }

}
