package bookeditor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private String author;

    private String isbn;

    private int printYear;

    private boolean readAlready;

    protected Book(){
    }

    public Book(String title, String description, String author, String isbn, int printYear, boolean readAlready){
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
        this.printYear = printYear;
        this.readAlready = readAlready;
    }

    public Long getId() {
        return id;
    }

    //вообще setId нигде не используется, но нужен в binder для grid, в которой мы хотим указать, что поле id не доступно при редактировании
    //.setEditable(false)
    public void setId() {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrintYear() {
        return printYear;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public void setReadAlready(boolean readAlready) {
        this.readAlready = readAlready;
    }

    public boolean getReadAlready() {
         return readAlready;
    }

    @Override
    public String toString() {
        return String.format("Book[id=%d, title='%s', description='%s', author='%s', isbn='%s', printYear='%d', readAlready='b']",
                id, title, description, author, isbn, printYear,readAlready);
    }

}
