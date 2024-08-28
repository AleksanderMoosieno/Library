import java.io.BufferedReader
import java.io.File
import java.io.FileReader


class Library(val books: MutableList<Book>, val readers:MutableList<Reader>){

    fun addBook(book: Book): Unit {
        this.books.add(book)
        println("The book has been added")
    }

    fun removeBook(book: Book): Unit {
        this.books.remove(book)
        println("the book has been deleted")
    }

    fun registerReader(reader: Reader):Unit{
        readers.add(reader)
        println("The reader is registered")
    }

    fun unregisterReader(reader: Reader):Unit{
        if (reader.borrowedBooks.isEmpty()) {
            readers.remove(reader)
            println("The reader is not registered")
        }else{println("Просьба Вернуть книгу!")}
    }

    private fun findBook(book: Book): Boolean {
        val bookFlag: Boolean = books.any {book.title == it.title}
        return bookFlag
    }

    fun findReaderById(reader: Reader): Boolean {
        val findReaderId:Boolean = readers.any{(it.readerId == reader.readerId) && (it.name == reader.name)}
        return findReaderId
    }

    fun addAllBookFromFile():Unit{
        val fileBook = File("C:\\Users\\Александр\\Desktop\\Book.txt")
        val newBook = BufferedReader(FileReader(fileBook, Charsets.UTF_8))
        newBook.lines().forEach{
            val book:Book = Book(it.split(",")[0],it.split(",")[1].toString(),it.split(",")[2])
            if(!this.findBook(book)){
                this.books.add(book)
            }else{println("${book.title} уже усть в библиотеке!!!!")}

        }
        println("Массовое добавление книг выполнено!")
        println(this.books)
    }

    fun addAllReadsFromFile(): Unit{
        val fileRead = File("C:\\Users\\Александр\\Desktop\\Read.txt")
        val newReaders = BufferedReader(FileReader(fileRead, Charsets.UTF_8))
        newReaders.lines().forEach{
            val filterReader: Reader = Reader(it.split(",")[0],it.split(",")[1].toInt())
            if(!this.findReaderById(filterReader)){
                this.readers.add(filterReader)
            }else{println("${filterReader.name} уже зарегестрирован!!!!")}
        }
        println("Массовое добавление читателей выполнено!")
        println(this.readers)
    }
}