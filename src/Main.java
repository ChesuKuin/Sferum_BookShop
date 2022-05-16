import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static List<Book> books = new ArrayList<>();
    private static List<Saved> saves = new ArrayList<>();
    public record Book(String name, int count, int cost){
    }
    public record Saved(String names){
    }
    public static void main(String[] args)
    {

        Scanner scanner = new Scanner(System.in);
        String input;
        String start_input;
        String balance_text;
        int i = 0;
        int y = 0;
        String exit = "exit";
        String current_balance = "print balance";
        String stock = "show books in stock";
        String bought = "show bought books";
        String buy_book = "buy book";
        String help = "help";
        String save = "save book";
        String show_saved = "show saved books";
        String change_balance = "change balance";
        String add_balance = "add balance";
        start_input = scanner.nextLine();
        balance_text = start_input;
        int balance = Integer.parseInt(balance_text.split(",")[0].replace("balance: ", ""));
        start_input = start_input.replace("[", "")
                .replace("]", "")
                .replace("(", "")
                .replaceAll("(balance: [0-9]+, books:)", "")
                .trim();

        for(String book : start_input.split("(\\), )"))
        {
            String book_name = book.split("\",")[0]
                    .replaceAll("\"", "")
                    .trim();
            int count = Integer.parseInt(book.split("\",")[1]
                    .split(",")[0]
                    .trim());
            int cost = Integer.parseInt(book.split("\",")[1]
                    .split((","))[1]
                    .replaceAll("\\)", "")
                    .trim());// ...

            books.add(new Book(book_name, count, cost));
        }
        try {

            while (true) {
                System.out.println();
                input = scanner.nextLine();
                if (input.length() >= 9) {
                    String buy_cmd = input.substring(0, 9);
                    if (buy_cmd.equals(buy_book)) {
                        String[] parts = input.split("\"");
                        String BK_Name = parts[1];
                        String Buy_Count = parts[2].trim();

                        int BuyU_Count = Integer.parseInt(Buy_Count);
                        Book a = books.stream()
                                .filter(book -> BK_Name.equals(book.name()))
                                .findAny()
                                .orElse(null);

                        if (a != null) {
                            books.remove(a);
                            Book book = new Book(a.name(), a.count() - BuyU_Count, a.cost());
                            if (book.count() <= 0)
                                books.remove(book);
                            Book bought_books = new Book(a.name(), a.count(), 0);
                            System.out.println("deal");
                        } else {
                            System.out.println("no deal");
                        }
                    } else if (buy_cmd.equals(save)) {
                        String[] savep = input.split("\"");
                        String BK_Names = savep[1];
                        Book a = books.stream()
                                .filter(book -> BK_Names.equals(book.name()))
                                .findAny()
                                .orElse(null);

                        if (a != null) {
                            Saved saved = new Saved(a.name());
                            System.out.println("Saved");
                        } else {
                            System.out.println("Not saved");
                        }
                    }
                }

                if (input.equals(exit)) {
                    break;
                } else if (input.equals(current_balance)) {
                    System.out.println("balance: " + balance + " руб");

                } else if (input.equals(stock)) {
                    for (Book book : books) {
                        System.out.printf("%s, %d шт, %d руб %n", book.name(), book.count(), book.cost());
                    }
                } else if (input.equals(bought)) {
                    for (Book bought_books : books) {
                        System.out.printf("%s, %d шт", bought_books.name(), bought_books.count());
                    }
                } else if (input.equals(help)) {
                    System.out.println(current_balance);
                    System.out.println(stock);
                    System.out.println(bought);
                    System.out.println(buy_book + "\"Название книги\" " + "количество");
                    System.out.println(save + "\"Название книги\"");
                    System.out.println(show_saved);
                    System.out.println(change_balance);
                    System.out.println(add_balance);
                    System.out.println(exit);
                } else if (input.equals(show_saved)) {
                    for (Saved saved : saves) {
                        System.out.printf("%s", saved.names());
                    }


                }
                else if(input.equals(change_balance))
                {
                    System.out.println("Хорошо. На сколько?");
                    balance = scanner.nextInt();
                    System.out.println("Успешно");
                }
                else if(input.equals(add_balance))
                {
                    System.out.println("Хорошо. Сколько добавим?");
                    int add_to_balance = scanner.nextInt();
                    balance = balance + add_to_balance;
                    System.out.println("Успешно");
                }
                else
                {
                    System.out.println("I don't understand");
                }
            }


        }
        catch(Exception e){
            System.out.println("------------------[Error]-----------------\n");
            System.out.println(e.getMessage());
            System.out.println("\n---[Exception catched. Continue]---");
        }
    }
}


