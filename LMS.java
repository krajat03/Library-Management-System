import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    int book_id;
    String book_name;
    boolean issued = false;

    Book(int book_id, String book_name, boolean issued) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.issued = issued;
    }
}

class Book_entry {
    List<Book> books = new ArrayList<>();

    void addBook(Scanner scanner) {
        System.out.print("-> Enter book id: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();

        if (checkEntry(book_id)) {
            System.out.println("⚠️ Book with this ID already exists.");
            return;
        }

        System.out.print("-> Enter book name: ");
        String book_name = scanner.nextLine();

        Book book = new Book(book_id, book_name, false);
        books.add(book);
        System.out.println("✅ Book added sucessfully!");
    }

    void displayBooks() {
        System.out.println("\n+----------+-------------------+--------+");
        System.out.printf("| %-8s | %-17s | %-6s |\n", "Book ID", "Book Name", "Issued");
        System.out.println("+----------+-------------------+--------+");

        for (Book b : books) {
            System.out.printf("| %-8d | %-17s | %-6s |\n", b.book_id, b.book_name, b.issued ? "Yes" : "No");
        }

        System.out.println("+----------+-------------------+--------+");
    }

    boolean checkEntry(int book_id) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.book_id == book_id) {
                return true;
            }
        }
        return false;
    }

    boolean checkBookStatus(int book_id) {
        for (Book book : books) {
            if (book.book_id == book_id) {
                return book.issued;
            }
        }
        return false;
    }

    int length() {
        return books.size();
    }

}

class Entry {
    int id, book_id;
    String name, book_name, cls;

    Entry(int id, String cls, String name, int book_id, String book_name) {
        this.id = id;
        this.cls = cls;
        this.book_id = book_id;
        this.name = name;
        this.book_name = book_name;
    }
}

class Storage {
    List<Entry> entries = new ArrayList<>();

    void addEntry(Entry entry) {
        entries.add(entry);
    }

    void deleteEntry(int id, int book_id) {
        boolean found = false;
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            if (e.id == id && e.book_id == book_id) {
                entries.remove(i);
                System.out.println("✅ Entry with ID " + id + " and Book ID " + book_id + " returned sucessfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("❌ Entry not found with ID " + id + " and Book ID " + book_id);
        }
    }

    void display() {
        String line = "+------+-------+----------------+----------+-------------------+";
        String header = String.format("| %-4s | %-5s | %-14s | %-8s | %-17s |",
                "ID", "Class", "Name", "Book ID", "Book Name");

        System.out.println(line);
        System.out.println(header);
        System.out.println(line);

        for (Entry e : entries) {
            System.out.printf("| %-4d | %-5s | %-14s | %-8d | %-17s |\n",
                    e.id, e.cls, e.name, e.book_id, e.book_name);
        }

        System.out.println(line);
    }
}

class Library {
    Storage s = new Storage();
    Book_entry books = new Book_entry();

    void issueBook(Scanner scanner) {

        System.out.print("-> Enter id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("-> Enter class: ");
        String cls = scanner.nextLine();

        System.out.print("-> Enter name: ");
        String name = scanner.nextLine();

        System.out.print("-> Enter book id: ");
        int book_id = scanner.nextInt();
        while (!books.checkEntry(book_id)) {
            System.out.println("### Invalid book id, try again: ");
            System.out.print("-> Enter book id: ");
            book_id = scanner.nextInt();
        }

        if (books.checkBookStatus(book_id)) {
            System.out.println("⚠️ Book already issued.");
            return;
        }
        scanner.nextLine();

        String book_name = "";
        for (Book b : books.books) {
            if (b.book_id == book_id) {
                book_name = b.book_name;
                break;
            }
        }

        Entry entry = new Entry(id, cls, name, book_id, book_name);
        s.addEntry(entry);
        for (Book b : books.books) {
            if (b.book_id == book_id) {
                b.issued = true;
                break;
            }
        }
        System.out.println("✅ Book issued sucessfully");
    }

    void deleteEntry(Scanner scanner) {
        int id, book_id;
        System.out.print("-> Enter id: ");
        id = scanner.nextInt();

        System.out.print("-> Enter book id: ");
        book_id = scanner.nextInt();

        s.deleteEntry(id, book_id);
        for (Book book : books.books) {
            if (book.book_id == book_id) {
                book.issued = false;
                break;
            }
        }
    }

    void display() {
        s.display();
    }
}

public class LMS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n====================================");
            System.out.println("1) Add book");
            System.out.println("2) List all books");
            System.out.println("3) Issue book");
            System.out.println("4) Return book");
            System.out.println("5) View all issued books");
            System.out.println("6) Exit");
            System.out.print("-> Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.books.addBook(scanner);
                    break;
                case 2:
                    library.books.displayBooks();
                    break;
                case 3:
                    library.issueBook(scanner);
                    break;
                case 4:
                    library.deleteEntry(scanner);
                    break;
                case 5:
                    library.display();
                    break;
                case 6:
                    System.out.println("Thank you for using LMS!");
                    return;
                default:
                    System.out.println("### Invalid option!");
            }
        }
    }
}
