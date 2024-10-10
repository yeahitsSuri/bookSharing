import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../../../services/services/book.service';
import { Router } from '@angular/router';
import { PageResponseBookResponse } from '../../../../services/models/page-response-book-response';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [ CommonModule, HttpClientModule, FormsModule],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{
  bookResponse: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 5;

  constructor(
    private bookService: BookService,
    private router: Router
  ) {

  }
  ngOnInit() : void{
    this.findAllBooks();
  }

  private findAllBooks(): void {
    this.bookService.findAllBooks({
      page: this.page,
      size: this.size
    }).subscribe(
      {next: (books) => {
      this.bookResponse = books;
    }});
  }

}
