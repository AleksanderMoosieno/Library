
data class Reader(val name: String, val readerId: Int) {

    val borrowedBooks: MutableList<Book>

    init {
        borrowedBooks = mutableListOf()
    }

    fun borrowBook(book: Book): Unit{
        if (book is Ebook){
            book.borrowBook()
        }else {
            book.borrowBook()
            this.borrowedBooks.add(book)
        }
    }

    fun returnBook(book: Book): Unit{
        book.returnBook()
        this.borrowedBooks.remove(book)
        println("${this.name} returned the book")
    }
}