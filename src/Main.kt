

fun main() {
    val book: Book = Book("Мертвые души", "Гоголь Н.В.", "1234_3")
    val book1: Book = Book("Тарас Бульба", "Гоголь Н.В.", "1234_1")
    val book2: Book = Book("Сказки", "Пушкин А.С.", "1234_2")
    val elBook: Ebook = Ebook("Научная статья", "Ученные", "2223454_4",75)
    val books: MutableList<Book> = mutableListOf(book, book1, book2, elBook)
    val readers: MutableList<Reader> = mutableListOf(Reader("Петр",5))
    val mainLibrary:Library = Library(books, readers)
    println("Привет Вы пришли в Библиотеку!")
    while(true) {
        println("Выберите действие:")
        println("1 - Зарегестрироваться в библиотеке")
        println("2 - Предъявить читательский билет ")
        println("3 - Войти, как библиотекарь")
        println("0 - Уйти из библиотеки ")
        val command: Int = readln().toInt()
        when(command){
            Command.librarian.number -> {
                println("Выбрать команду:")
                println("4 - Массовое добавление Книг")
                println("5 - Массовое добавление Пользователей")
                println("6 - Удаление Книги")
                println("7 - Добавление Книги")
                println("0 - Уйти из библиотеки ")
                val commandLibrarian: Int = readln().toInt()
                if (commandLibrarian == Command.addAllBook.number){
                    mainLibrary.addAllBookFromFile()
                    println("В библиотеке ${mainLibrary.books.size} книг.")
                }
                if (commandLibrarian == Command.addAllReads.number){
                    mainLibrary.addAllReadsFromFile()
                    println("В библиотеке зарегестрированно ${mainLibrary.readers.size} читателей.")
                }
                if (commandLibrarian == Command.dellBook.number){
                    var numBook:Int = 1
                    println("Выберите Книгу которую хотите удалить из библиотеки.")
                    mainLibrary.books.forEach{println("${numBook++} - ${it.title} ${it.author} ${it.available}")}
                    val dellBook: Int = readln().toInt()
                    if (mainLibrary.books[dellBook-1].available == false){
                        println("Книги нет на месте, ее взяли почитать!")
                    }
                    if (mainLibrary.books[dellBook-1].available == true){
                        mainLibrary.removeBook(mainLibrary.books[dellBook-1])
                        println("Книга удалена!")
                    }
                }
                if(commandLibrarian == Command.addBook.number){
                    println("Какую книгу Вы хотите добавить?")
                    println("8 - Классическую")
                    println("9 - Электронную")
                    if (readln().toInt() == Command.addBookClassik.number){
                        mainLibrary.addBook(Book(readln(), readln(),readln()))
                    }
                    if (readln().toInt() == Command.addEbook.number){
                        mainLibrary.addBook(Ebook(readln(), readln(),readln(),readln().toInt()))
                    }

                    println("Книга добавлена!")
                }
                if (commandLibrarian == Command.endLibrary.number){println("До скорой встречи!");break}
            }
            Command.reg.number -> {
                println("Ведите Ваше Имя.")
                val name: String = readln()
                val readerId: Int = (0..100).random()
                val reader: Reader = Reader(name, readerId)
                val findReader: Boolean = mainLibrary.findReaderById(reader)
                if (findReader) {
                    println("Пользователь уже зарегистрирован!")
                    continue
                }
                if (!findReader) {
                      println("Поздравляем, читательский билет создан!")
                      mainLibrary.registerReader(reader)
                      println(reader)
                    continue
                }

            }
            Command.getCard.number -> {
                println("Введите имя")
                val name: String = readln()
                println("Введите номер читательского билета!")
                val readerId: Int = readln().toInt()
                val reader: Reader = Reader(name,readerId)
                val findReader: Boolean = mainLibrary.findReaderById(reader)
                if (findReader) {
                    println("Привет $name!")
                    println("Достпные дествия, чтобы выбрать введите цифру ")
                    println("11 - взять книгу")
                    println("12 - вернуть книгу")
                    println("13 - Вернуть читательский билет!")
                    val command2: Int = readln().toInt()
                    if(command2 == Command.setBook.number){
                        mainLibrary.books.forEach{it ->println(it.available)}
                        mainLibrary.readers.forEach{it ->println(it.borrowedBooks)}
                        println("Доступные книги:")
                        var numBook: Int = 1
                        mainLibrary.books.forEach{it -> println("${numBook++} -  ${it.title} ${it.author} ${it.available}")}
                        println("Чтобы взять книгу напишите ее номер ")
                        val readBook: Int = readln().toInt()
                        println(mainLibrary.books[readBook-1])
                        val getBook: Book = mainLibrary.books[readBook-1]
                        if (getBook.available == false){
                            println("Книгу ${getBook.title} нельзя взять.")
                        }
                        if (getBook.available == true){
                            val indexReader:Int = mainLibrary.readers.indexOf(reader)
                            mainLibrary.readers[indexReader].borrowBook(mainLibrary.books[readBook-1])
                            println("Вы взяли книгу ${getBook.title}")
                            continue
                        }

                    }
                    if(command2 == Command.getBook.number){
                        val indexReader:Int = mainLibrary.readers.indexOf(reader)
                        val flagReader: Boolean = mainLibrary.readers[indexReader].borrowedBooks.isEmpty()
                        if (flagReader){
                            println(reader.borrowedBooks)
                            println("У вас нет книг!")
                            continue
                        }

                        if (!flagReader){
                            var numBook:Int = 1
                            println("Выберите книгу, которую вы хотите сдать:")
                            mainLibrary.readers[indexReader].borrowedBooks.forEach{ it ->println("${numBook++} - ${it.title} ${it.author}") }
                            val returnBook: Int = readln().toInt()
                            println("Книгу сдаю ${mainLibrary.readers[indexReader].borrowedBooks[returnBook-1].title}")
                            mainLibrary.readers[indexReader].borrowedBooks[returnBook-1].returnBook()
                            mainLibrary.readers[indexReader].returnBook(mainLibrary.readers[indexReader].borrowedBooks[returnBook-1])
                            continue

                        }

                    }
                    if(command2 == Command.dellReadTicket.number){
                        val readerLib:Int = mainLibrary.readers.indexOf(reader)
                        mainLibrary.unregisterReader(mainLibrary.readers[readerLib])

                        continue
                    }

                }

               if(!findReader){println("Читательский билет не найден!"); continue}
            }
            Command.endLibrary.number -> {println("До скорой встречи!");break}
        }

    }
}

enum class Command(val number: Int) {
    reg(1),
    getCard(2),
    librarian(3),
    addAllBook(4),
    addAllReads(5),
    dellBook(6),
    addBook(7),
    addBookClassik(8),
    addEbook(9),
    endLibrary(0),
    setBook(11),
    getBook(12),
    dellReadTicket(13)

}
