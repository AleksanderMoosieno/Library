import java.util.concurrent.TimeUnit

open class Book(val title:String, val author: String, val isbn: String) {

   open var available: Boolean = true


    open fun borrowBook(): Unit {
        this.available = false
    }

    open fun returnBook(): Unit {
        this.available = true
    }

}

class Ebook(title: String, author: String, isbn: String,val fileSize: Int): Book( title, author, isbn){

    override var available: Boolean = true
        get() = super.available
        set(value) {field = value}

    override fun returnBook() {
        this.available = true
    }
    override fun borrowBook() {
        super.borrowBook()
        println("Это электронная книга!")
        println("Книга вам доступна на прочтение в течении 30 секунд.")
        TimeUnit.SECONDS.sleep(5)
        this.returnBook()
    }

}