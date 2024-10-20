import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { BookResponse } from '../../../../services/models/book-response';
import { Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {
  private _book: BookResponse = {};
  @Input() set book(value: BookResponse) {
    this._book = value;
  }
  get book(): BookResponse {
    return this._book;
  }


  private _manage: boolean = false;
  get manage(): boolean {
    return this._manage;
  }
  @Input() set manage(value: boolean) {
    this._manage = value;
  }


  private _bookCover: string | undefined;

  get bookCover(): string | undefined {
    if (this.book.cover) {
      return 'data:image/jpeg;base64,' + this._book.cover;
    }
    return 'https://media.newyorker.com/photos/59ee325f1685003c9c28c4ad/4:3/w_4992,h_3744,c_limit/Heller-Kirkus-Reviews.jpg';
  }

  @Output() private share: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private borrow: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private addToWaitingList: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private edit: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private archive: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private showDetails: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();


  onShowDetails(): void {
    this.showDetails.emit(this.book);
  }

  onBorrow(): void {
    this.borrow.emit(this.book);
  }

  onAddToWaitingList(): void {
    this.addToWaitingList.emit(this.book);
  }

  onEdit(): void {
    this.edit.emit(this.book);
  }

  onShare(): void {
    this.share.emit(this.book);
  }

  onArchive(): void {
    this.archive.emit(this.book);
  }

}
