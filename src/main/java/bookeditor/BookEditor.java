package bookeditor;

import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import java.util.List;
import java.util.ListIterator;

@SpringComponent
@UIScope
public class BookEditor {

    private final BookRepository repository;
    private Book book;

    /* Поля редактируют параметры Book сущности */
    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    TextField author = new TextField("Author");
    TextField isbn = new TextField("ISBN");
    TextField printYear = new TextField("Print year");
    TextField readAlready = new TextField("Read already");

    //https://vaadin.com/docs/v8/framework/datamodel/datamodel-forms.html
    Binder<Book> binder = new Binder<>(Book.class);



    /* Автоматическое связывание */
    @Autowired
    public BookEditor(BookRepository repository) {
        this.repository = repository;
        //в binder еще валидатор можно воткнуть
        binder.forField(printYear)
                .withConverter(new StringToIntegerConverter("Что-то пошло не так"))
                .bind("printYear");
        binder.forField(readAlready)
                .withConverter(new StringToBooleanConverter("Что-то пошло не так"))
                .bind("readAlready");
        // Связывание, использующее конвенцию именования
        binder.bindInstanceFields(this);

    }



    //два метода осталось доделать
    public void addbook(Book b){


    }

    public void updatebook(Book b) {
            b.setReadAlready(false);
            //что тут надо написать?

            //Если bean установлен для binder, book в repo автоматически обновляется, когда пользователь выполняет изменения в пользовательском интерфейсе
            //binder.writeBeanIfValid(b);
    }


    public void deletebook(List<Book> checkbooklist) {
        for (ListIterator<Book> iter = checkbooklist.listIterator(); iter.hasNext(); ) {
            book = iter.next();
            repository.delete(book);
        }
    }

    public void readbook(List<Book> checkbooklist) {
        for (ListIterator<Book> iter = checkbooklist.listIterator(); iter.hasNext(); ) {
            book = iter.next();
            book.setReadAlready(true);
            repository.save(book);
        }
    }

}