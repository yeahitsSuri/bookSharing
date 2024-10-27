import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../../../services/services/book.service';
import { Router } from '@angular/router';
import { PageResponseBookResponse } from '../../../../services/models/page-response-book-response';
import { BookCardComponent } from "../../components/book-card/book-card.component";
import { BookResponse } from '../../../../services/models/book-response';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule, BookCardComponent, RouterModule],
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss'
})

export class MyBooksComponent implements OnInit{
  bookResponse: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 5;
  pages: any = [];

  constructor(
    private bookService: BookService,
    private router: Router
  ) {

  }
  ngOnInit() : void{
    this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooksByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (books) => {
          this.bookResponse = books;
          this.pages = Array(this.bookResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
        }
      });
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1;
    this.findAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBooks();
  }

  get isLastPage() {
    return this.page === this.bookResponse.totalPages as number - 1;
  }

  archiveBook(book: BookResponse) {
    this.bookService.updArchivedStatus({
      'book_id': book.id as number
    }).subscribe({
      next: () => {
        book.archived = !book.archived;
      }
    });
  }

  shareBook(book: BookResponse) {
    this.bookService.updShareableStatus({
      'book_id': book.id as number
    }).subscribe({
      next: () => {
        book.shareable = !book.shareable;
      }
    });
  }

  editBook(book: BookResponse) {
    this.router.navigate(['books', 'manage', book.id]);
    // navigate to the manage book page with the book id
  }
}

