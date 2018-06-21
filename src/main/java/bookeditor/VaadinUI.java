package bookeditor;

import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.data.domain.Pageable;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@SpringUI
public class VaadinUI extends UI {
    private BookRepository repo;
    private final BookEditor editor;
    private final TextField filterByAuthor;
    private final TextField filterByTitle;
    private final TextField filterByPrintYear;
    private final ComboBox filterByreadAlready;
    private AtomicReference<String> contentfilter = new AtomicReference<>();
    private final List<Book> checkbooklist = new LinkedList<>();
    private Book currenteditedbookformgrid = new Book();
    private Grid grid = new Grid();
    private final Button add;
    private final Button read;
    private final Button delete;
    private static final Logger log = LoggerFactory.getLogger(VaadinUI.class);
    @Autowired
    public VaadinUI(BookRepository repo, BookEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.filterByAuthor = new TextField();
        this.filterByTitle = new TextField();
        this.filterByPrintYear = new TextField();
        this.filterByreadAlready = new ComboBox();
        this.add = new Button("Добавить", FontAwesome.PLUS);
        this.read = new Button("Прочитать", FontAwesome.EYE);
        this.delete = new Button("Удалить", FontAwesome.TRASH);
}
    @Override
    protected void init(VaadinRequest request) {
        VerticalSplitPanel main = new VerticalSplitPanel();
        VerticalSplitPanel actions = new VerticalSplitPanel();
        main.setSizeFull();
        actions.setFirstComponent(filterAndbuttons());
        actions.setSplitPosition(100, Sizeable.UNITS_PERCENTAGE);//
        actions.setLocked(true);
        main.setFirstComponent(actions);
        main.setSecondComponent(jpaPagable());
        main.setSplitPosition(15, Sizeable.UNITS_PERCENTAGE);
        setContent(main);
        filterByAuthor.addValueChangeListener(event -> {
            if(!event.getValue().isEmpty()) {
                filterByTitle.setEnabled(false);
                filterByPrintYear.setEnabled(false);
                filterByreadAlready.setEnabled(false);
                contentfilter.set(event.getValue());
                main.setSecondComponent(jpaPagable());
            }
            else {
                filterByTitle.setEnabled(true);
                filterByPrintYear.setEnabled(true);
                filterByreadAlready.setEnabled(true);
                contentfilter.set(null);
                main.setSecondComponent(jpaPagable());
            }
            grid.addItemClickListener(gridevent -> {
                currenteditedbookformgrid = (Book) gridevent.getItem();
                Notification.show(String.valueOf(currenteditedbookformgrid));
            });
        });
        filterByTitle.addValueChangeListener(event -> {
            if(!event.getValue().isEmpty()) {
                filterByAuthor.setEnabled(false);
                filterByPrintYear.setEnabled(false);
                filterByreadAlready.setEnabled(false);
                contentfilter.set(event.getValue());
                main.setSecondComponent(jpaPagable());
            }
            else {
                filterByAuthor.setEnabled(true);
                filterByPrintYear.setEnabled(true);
                filterByreadAlready.setEnabled(true);
                contentfilter.set(null);
                main.setSecondComponent(jpaPagable());
            }
            grid.addItemClickListener(gridevent -> {
                currenteditedbookformgrid = (Book) gridevent.getItem();
                Notification.show(String.valueOf(currenteditedbookformgrid));
            });
        });
        filterByPrintYear.addValueChangeListener(event -> {
            try {
                if (!event.getValue().isEmpty()) {
                    filterByAuthor.setEnabled(false);
                    filterByTitle.setEnabled(false);
                    filterByreadAlready.setEnabled(false);
                    contentfilter.set(event.getValue());
                    main.setSecondComponent(jpaPagable());
                }
                else {
                    filterByAuthor.setEnabled(true);
                    filterByTitle.setEnabled(true);
                    filterByreadAlready.setEnabled(true);
                    contentfilter.set(null);
                    main.setSecondComponent(jpaPagable());
                }
            }
            catch (NumberFormatException e){
                Notification.show("Год издания введен некорректно. Пример: 2017. Повторите ввод.");
                filterByPrintYear.setValue("");
            }
            grid.addItemClickListener(gridevent -> {
                currenteditedbookformgrid = (Book) gridevent.getItem();
                Notification.show(String.valueOf(currenteditedbookformgrid));
            });
        });
        filterByreadAlready.addValueChangeListener(event -> {
            if(event.getValue() != null) {
                if (event.getValue().toString().equalsIgnoreCase("прочитанные")) {
                    filterByAuthor.setEnabled(false);
                    filterByTitle.setEnabled(false);
                    filterByPrintYear.setEnabled(false);
                    contentfilter.set("true");
                    main.setSecondComponent(jpaPagable());
                }
                if (event.getValue().toString().equalsIgnoreCase("непрочитанные")) {
                    filterByAuthor.setEnabled(false);
                    filterByTitle.setEnabled(false);
                    filterByPrintYear.setEnabled(false);
                    contentfilter.set("false");
                    main.setSecondComponent(jpaPagable());
                }
            }
            else {
                filterByAuthor.setEnabled(true);
                filterByTitle.setEnabled(true);
                filterByPrintYear.setEnabled(true);
                contentfilter.set(null);
                add.setEnabled(true);
                main.setSecondComponent(jpaPagable());
            }
            grid.addItemClickListener(gridevent -> {
                currenteditedbookformgrid = (Book) gridevent.getItem();
                Notification.show(String.valueOf(currenteditedbookformgrid));
            });
        });
        read.addClickListener(clickEvent-> {
            if(!checkbooklist.isEmpty()) {
                editor.readbook(checkbooklist);
                initialstate();
                main.setSecondComponent(jpaPagable());
                Notification.show(String.valueOf(checkbooklist.size()) + " books read");
            }
        });
        delete.addClickListener(clickEvent-> {
            if(!checkbooklist.isEmpty()) {
                editor.deletebook(checkbooklist);
                initialstate();
                main.setSecondComponent(jpaPagable());
                Notification.show(String.valueOf(checkbooklist.size()) + " books delete");
            }
        });
        add.addClickListener(clickEvent -> {
            blockfilters();
            actions.setSecondComponent(workingspace());
            actions.setSplitPosition(15, Sizeable.UNITS_PERCENTAGE);//
            main.setSplitPosition(100, Sizeable.UNITS_PERCENTAGE);
            main.getSecondComponent().setVisible(false);

            //код добавления книги через редактор сетки с пустой книгой или посмотреть вариант добавления в основноую сетку toprow или upperrow


            //возврат интерфейса в исходное состояние

                });

        grid.addItemClickListener(gridevent -> {
            currenteditedbookformgrid = (Book) gridevent.getItem();
            Notification.show(String.valueOf(currenteditedbookformgrid));
        });
    }
    //workingspace может не понадобиться, сейчас он ничего особо не делает
    private HorizontalLayout workingspace() {
        HorizontalLayout wspace = new HorizontalLayout();
        wspace.setSizeFull();
        checkbooklist.clear();
        checkbooklist.add(new Book("","","","",10,false));
        final Grid gridworkingspace = createGrid(checkbooklist);
        gridworkingspace.getEditor().setEnabled(true);
        //чето делаем тут
        wspace.addComponent(gridworkingspace);
        wspace.setMargin(true);
        return wspace;
    }
    private HorizontalLayout filterAndbuttons() {
        HorizontalLayout allactions = new HorizontalLayout(filterByTitle, filterByAuthor, filterByPrintYear, filterByreadAlready,
                read, add, delete);
        filterByAuthor.setPlaceholder("Поиск по автору");
        filterByAuthor.setDescription("Поиск книг по автору");
        filterByAuthor.setValueChangeMode(ValueChangeMode.LAZY);
        filterByTitle.setPlaceholder("Поиск по названию");
        filterByTitle.setDescription("Поиск книг по названию");
        filterByTitle.setValueChangeMode(ValueChangeMode.LAZY);
        filterByPrintYear.setPlaceholder("Поиск по году издания");
        filterByPrintYear.setDescription("Поиск книг по году издания");
        filterByPrintYear.setValueChangeMode(ValueChangeMode.LAZY);
        filterByreadAlready.setPlaceholder("Поиск по статусу");
        filterByreadAlready.setDescription("Поиск книг по типу - прочитанные ранее или непрочитанные");
        filterByreadAlready.setItems("Прочитанные", "Непрочитанные");
        add.setDisableOnClick(true);
        add.setDescription("Добавление новой книги.");
        read.setEnabled(false);
        read.setDescription("Чтение книги. Если статус книги был <непрочитанная>, статус изменяется на <прочитанная>. " +
                "Чтение доступно при выборе книги <флажок в чекбоксе> из списка книг. Читать можно сразу по несколько книг.");
        delete.setEnabled(false);
        delete.setDescription("Удаление книги. Удаление доступно при выборе книги <флажок в чекбоксе> из списка книг. " +
                "Удалять можно сразу по несколько книг.");
        allactions.setMargin(true);
        return allactions;
    }
    private Page<Book> pageBooksAuthor(String filterText, Pageable pageable) {return repo.findByAuthorStartsWithIgnoreCase(filterText,pageable);}
    private Page<Book> pageBooksTitle(String filterText, Pageable pageable) {return repo.findByTitleStartsWithIgnoreCase(filterText,pageable);}
    private Page<Book> pageBooksPrintYear(int filter, Pageable pageable) {return repo.findByPrintYear(filter,pageable);}
    private Page<Book> pageBooksreadAlready(boolean filter, Pageable pageable) {return repo.findByreadAlready(filter,pageable);}
    private VerticalLayout jpaPagable() {
        final int page = 1;
        final int limit = 10;
        final Page<Book> books = findAllwithfilter(0, limit);
        final long total = books.getTotalElements();
        grid = createGrid(books.getContent());
        multiselect(grid);
        final Pagination pagination = createPagination(total, page, limit);
        pagination.addPageChangeListener((PaginationChangeListener) event -> {
            log.debug("jpaPagable : {}", event.toString());
            Page<Book> books1 = findAllwithfilter(event.pageIndex(), event.limit());
            pagination.setTotalCount(books1.getTotalElements());
            grid.setItems(books1.getContent());
            grid.scrollToStart();
        });
        final VerticalLayout layout = createContent(grid, pagination);
        layout.setMargin(true);
        return layout;
    }
    private Page<Book> findAllwithfilter(int page, int size) {
            Pageable pageable = new PageRequest(page, size);
            if(!filterByAuthor.getValue().isEmpty()) {return pageBooksAuthor(String.valueOf(contentfilter),pageable);}
            if(!filterByTitle.getValue().isEmpty()) {return pageBooksTitle(String.valueOf(contentfilter),pageable);}
            if(!filterByPrintYear.getValue().isEmpty()) {return pageBooksPrintYear(Integer.parseInt(String.valueOf(contentfilter)),pageable);}
            if(filterByreadAlready.getValue() != null) {return pageBooksreadAlready(Boolean.parseBoolean(String.valueOf(contentfilter)),pageable);}
            else {return repo.findAll(pageable);}
    }
    private Grid createGrid(List<Book> books) {
        final Grid<Book> grid = new Grid<>();
        grid.setItems(books);
        grid.setSizeFull();
        grid.getEditor().setEnabled(true);
        Binder<Book> binder = grid.getEditor().getBinder();
        grid.addColumn(Book::getId).setCaption("Id").setMaximumWidth(60);
        grid.addColumn(Book::getTitle).setCaption("Название книги").setEditorComponent(new TextField(), Book::setTitle).setEditable(true).setMaximumWidth(400);
        grid.addColumn(Book::getAuthor).setCaption("Автор").setEditorComponent(new TextField(), Book::setAuthor).setEditable(false).setMaximumWidth(200);
        grid.addColumn(Book::getDescription).setCaption("Краткое содержание").setEditorComponent(new TextField(), Book::setDescription).setEditable(true).setMaximumWidth(360);
        grid.addColumn(Book::getIsbn).setCaption("ISBN номер").setEditorComponent(new TextField(), Book::setIsbn).setEditable(true).setMaximumWidth(180);
        grid.addColumn(Book::getPrintYear).setCaption("Год издания").setEditorBinding(binder
                .forField(new TextField())
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("Год издания книги должен быть целым числом"))
                .withValidator(new BeanValidator(Book.class, "PrintYear"))
                .bind(Book::getPrintYear, Book::setPrintYear)).setEditable(true);
        grid.addColumn(Book::getReadAlready).setCaption("Статус").setEditorBinding(binder
                .forField(new TextField())
                .withNullRepresentation("")
                .withConverter(new StringToBooleanConverter("Статус прочтения книги является логическим значением - true или false"))
                .withValidator(new BeanValidator(Book.class, "ReadAlready"))
                .bind(Book::getReadAlready, Book::setReadAlready)).setEditable(false);
        return grid;
    }
    private Pagination createPagination(long total, int page, int limit) {
            final PaginationResource paginationResource = PaginationResource.newBuilder().setTotal(total).setPage(page).setLimit(limit).build();
            final Pagination pagination = new Pagination(paginationResource);
            pagination.setItemsPerPage(10, 20, 50, 100);
            return pagination;
        }
    private VerticalLayout createContent(Grid grid, Pagination pagination) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.addComponents(grid, pagination);
        layout.setExpandRatio(grid, 1f);
        return layout;
    }
    private void multiselect(Grid grid){
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(event -> {
            Notification.show(event.getAllSelectedItems().size() + " books selected");
            checkbooklist.clear();
            checkbooklist.addAll(event.getAllSelectedItems());
            buttonsavailable();
        });
    }
    private void buttonsavailable(){
        if(checkbooklist.size() > 0){
            add.setEnabled(false);
            read.setEnabled(true);
            delete.setEnabled(true);
        }
        else {
            add.setEnabled(true);
            read.setEnabled(false);
            delete.setEnabled(false);
            checkbooklist.clear();
        }
    }
    private void blockfilters(){
        filterByAuthor.setEnabled(false);
        filterByTitle.setEnabled(false);
        filterByPrintYear.setEnabled(false);
        filterByreadAlready.setEnabled(false);
    }
    private void initialstate(){
        filterByAuthor.clear();
        filterByTitle.clear();
        filterByPrintYear.clear();
        filterByreadAlready.clear();
        read.setEnabled(false);
        delete.setEnabled(false);
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}